/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;
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

    public ControladorSemantico() {
        this.tabelas = new HashMap();
        this.erros = new ArrayList();
    }
    
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
        t.addSimbolo(variavel, variavel.getLexema());
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
    public void classificaParametros(ArrayList<Simbolo> simbolos,  String escopo, String tipo) {
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
     */
    public void verificaDeclaracao(String escopo, Token token) {
        Tabela t = this.tabelas.get(escopo);
        
        if (t.getSimbolos().get(token.getLexema()) == null) {
            this.erros.add("ERRO SEMÂNTICO - " + 
                    "'" + token.getLexema() + "' não declarado.\n" + 
                    "Linha: " + token.getLinha() + "\n");
        }
    } 
    
    public void imprimirTabelas() {
        Set<String> ts = this.tabelas.keySet();
        Set<String> t2;
        Tabela t;
        Simbolo simb;
        
        for (String s : ts) {
            System.out.println("ESCOPO: " + s + "\n");
            
            t = this.tabelas.get(s);
            t2 = t.getSimbolos().keySet();
            
            for (String s2 : t2) {
                simb = t.getSimbolos().get(s2);
                System.out.println("SIMBOLO: " + s2);
                System.out.println(simb.getClassificacao() + ", " + simb.getCategaoria() + 
                        ", " + simb.getTipo());
            }
            System.out.println("_______________________");
        }
    }
}
