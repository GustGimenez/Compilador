/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import lexico.Token;

/**
 *
 * @author Gustavo Gimenez
 */
public class ControladorSemantico {

    /**
     * Guarda as tabelas de cada escopo do programa
     */
    private HashMap<String, Tabela> tabelas;

    /**
     * Guarda os erros semânticos
     */
    private ArrayList<String> erros;

    /**
     * Guarda os parametros formais durante a analise dos mesmos
     */
    private ArrayList<Simbolo> parametrosFormais;

    public ControladorSemantico() {
        this.tabelas = new HashMap();
        this.erros = new ArrayList();
    }

    /**
     * Recupera os erros semânticos
     *
     * @return erros ArrayList<String>
     */
    public ArrayList<String> getErros() {
        return this.erros;
    }

    /**
     * Cria uma nova instancia de tabela nos escopos
     *
     * @param nome String nome do escopo
     */
    public void colocarEscopo(String nome) {
        this.tabelas.put(nome, new Tabela());
    }

    /**
     * Adiciona uma variável no escopo desejado
     *
     * @param escopo String
     * @param variavel Simbolo
     */
    public void colocar(String escopo, Simbolo variavel) {
        Tabela t = this.tabelas.get(escopo);
        if (!t.addSimbolo(variavel, variavel.getLexema())) {
            this.erros.add("ERRO SEMÂNTICO - "
                    + "'" + variavel.getLexema() + "' já declarado.\n"
                    + "Linha: " + variavel.getLinha() + "\n");
        }
    }

    /**
     * Retorna o símbolo que corresponde ao lexema passado no escopo informado
     *
     * @param escopo String
     * @param lexema String
     *
     * @return Simbolo
     */
    public Simbolo pegar(String escopo, String lexema) {
        return this.tabelas.get(escopo).getSimbolo(lexema);
    }

    /**
     * Classifica os parâmetros formais de uma procedure de acordo com seu tipo
     *
     * @param simbolos ArrayList<Simbolo>
     * @param escopo String
     * @param tipo String
     */
    public void classificaParametros(ArrayList<Simbolo> simbolos, String escopo, String tipo) {
        Tabela t = this.tabelas.get(escopo);

        for (Simbolo s : simbolos) {
            s.setTipo(tipo);
            t.addSimbolo(s, s.getLexema());
        }
    }

    /**
     * Verifica se um símbolo usado já foi declarado no escopo atual
     *
     * @param escopo String
     * @param token Token
     *
     * @return boolean
     */
    public boolean verificaDeclaracao(String escopo, Token token) {
        Tabela t = this.tabelas.get(escopo);

        if (t.getSimbolos().get(token.getLexema()) == null) {
            this.erros.add("ERRO SEMÂNTICO - "
                    + "'" + token.getLexema() + "' não declarado.\n"
                    + "Linha: " + token.getLinha() + "\n");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifica a ordem, número e tipo dos parâmetros reais e formais
     *
     * @param escopo String
     * @param booleana boolean que representa se o tipo passado é boolean ou não
     * @param token Token
     */
    public void verificaParametro(String escopo, boolean booleana, Token token) {
        if (this.parametrosFormais.size() > 0) {
            if (this.parametrosFormais.get(0).getTipo().equals("tipo_boolean")) {
                if (!booleana) {
                    this.erros.add("ERRO SEMÂNTICO - "
                            + "Tipo do parâmetros inesperado!\n"
                            + "Linha: " + token.getLinha() + "\n");
                }
            } else {
                if (booleana) {
                    this.erros.add("ERRO SEMÂNTICO - "
                            + "Tipo do parâmetros inesperado!\n"
                            + "Linha: " + token.getLinha() + "\n");
                }
            }
            this.parametrosFormais.remove(0);
        } else {
            this.erros.add("ERRO SEMÂNTICO - "
                    + "Númnero de parâmetros inesperado!\n"
                    + "Linha: " + token.getLinha() + "\n");
        }
    }

    /**
     * Retornar os símbolos que são parametros da procedure
     *
     * @param procedure String
     *
     * @return ArrayList<Simbolos> parametros
     */
    public void getParametros(String procedure) {
        Tabela t = this.tabelas.get(procedure);
        Set<String> simbolos = t.getSimbolos().keySet();
        Simbolo simb;
        this.parametrosFormais = new ArrayList();

        for (String s : simbolos) {
            simb = t.getSimbolos().get(s);

            if (simb.getCategaoria() == Simbolo.PARAMETRO) {
                this.parametrosFormais.add(simb);
            }
        }

        Collections.sort(this.parametrosFormais, new OrdenaOrdem());
    }

    /**
     * Verifica se a atribuição à variável está correta
     *
     * @param escopo String
     * @param token Token
     * @param booleana boolean
     */
    public void verificaAtribuicao(String escopo, Token token, boolean booleana) {
        Tabela t = this.tabelas.get(escopo);

        Simbolo s = t.getSimbolo(token.getLexema());
        if (s.getTipo().equals("tipo_boolean")) {
            if (!booleana) {
                this.erros.add("ERRO SEMÂNTICO - "
                        + "'" + token.getLexema() + "' não não é do tipo inteiro.\n"
                        + "Linha: " + token.getLinha() + "\n");
            }
        } else {
            if (booleana) {
                this.erros.add("ERRO SEMÂNTICO - "
                        + "'" + token.getLexema() + "' não não é do tipo boolean.\n"
                        + "Linha: " + token.getLinha() + "\n");
            }
        }
    }

    /**
     * Informa se a variável em questão é booleana ou não
     *
     * @param escopo
     * @param token
     * @return
     */
    public boolean verificaBooleanVariavel(String escopo, Token token) {
        Tabela t = this.tabelas.get(escopo);

        return t.getSimbolo(token.getLexema()).getTipo().equals("tipo_boolean");
    }

    /**
     * Marca o símbolo como usado
     *
     * @param escopo String
     * @param token Token
     */
    public void usaSimbolo(String escopo, Token token) {
        Tabela t = this.tabelas.get(escopo);

        t.getSimbolo(token.getLexema()).setUtilizada(true);
    }

    /**
     * Percorre as tabelas verificando se as variáveis declaradas foram usadas
     */
    public void verificaNaoUsadas() {
        Set<String> ts = this.tabelas.keySet();
        Set<String> t2;
        Tabela t;
        Simbolo simb;
        
        for (String s : ts) {
            t = this.tabelas.get(s);
            t2 = t.getSimbolos().keySet();

            for (String s2 : t2) {
                simb = t.getSimbolos().get(s2);
                if (!simb.isUtilizada()) {
                    this.erros.add("AVISO SEMÂNTICO - "
                            + "'" + simb.getLexema() + "' nunca usado no escopo: "
                            + s + "\n");
                }
            }
        }
    }
}
