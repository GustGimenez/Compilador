/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sintatico;

import java.util.ArrayList;
import lexico.LexicalAnalyzer;
import lexico.Token;
import semantico.ControladorSemantico;
import semantico.Simbolo;

/**
 *
 * @author Gustavo Gimenez
 */
public class AnalisadorSintatico {

    private String mensagem;
    private ControladorSemantico semantico;
    private String escopoAtual;
    private String escopoAux;
    private boolean booleana;
    private boolean estaAtribuindo;
    private boolean analiseParametros;

    /**
     * Retorna tudo lido pelo analisador sintático
     *
     * @return String com a mensagem
     */
    public String getMensagem() {
        return this.mensagem;
    }

    /**
     * Imprime o erro na console
     *
     * @param t, objeto Token lido
     * @param esperado, String do que era esperado
     */
    public void erro(Token t, String esperado) {
        this.mensagem += "Erro Sintático: " + esperado + " esperado, mas "
                + t.getLexema() + " encontrado \n"
                + "\t -> Linha: " + t.getLinha() + ", Coluna: "
                + t.getColunaInicial() + "\n";
    }

    /**
     * Retorna os erros semânticos
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getErrosSemanticos() {
        return this.semantico.getErros();
    }

    public void analisePrograma(LexicalAnalyzer lex) {
        this.semantico = new ControladorSemantico();
        this.mensagem = "";

        Token t;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();

            if (!t.getClassificacao().equals("palavra_program")) {
                this.erro(t, "palavra_program");
            } else {
                this.mensagem += "Lido palavra_program " + t.getLexema() + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
            } else {
                this.semantico.colocarEscopo("global");
                this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
            } else {
                this.mensagem += "Lido PONTO_VIRGULA " + t.getLexema() + "\n";
            }
        }

        this.escopoAtual = "global";
        this.analiseBloco(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FIM")) {
                this.erro(t, "FIM");
            } else {
                this.mensagem += "Fim do programa" + "\n";
            }
        }
        
        this.semantico.verificaNaoUsadas();
    }

    private void analiseBloco(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            while (t.getClassificacao().equals("tipo_boolean")
                    || t.getClassificacao().equals("tipo_int")) {
                this.mensagem += "Lido " + t.getClassificacao() + " " + t.getLexema() + "\n";
                this.analiseParteDeclaracoesVariaveis(lex, t.getClassificacao());
                t = lex.getNextToken();
            }

            while (t.getClassificacao().equals("palavra_procedure")) {
                this.analiseParteDeclaracoesSubrotinas(lex);
                this.escopoAtual = "global";
                t = lex.getNextToken();
            }
        }

        this.escopoAtual = "global";
        lex.rewindTokenCounter();
        this.analiseComandoComposto(lex);
    }

    private void analiseParteDeclaracoesVariaveis(LexicalAnalyzer lex, String tipo) {
        Token t;
        Simbolo s;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("IDENTIFICADOR")) {
                    s = new Simbolo(t, Simbolo.VARIAVEL);
                    s.setTipo(tipo);
                    this.semantico.colocar(this.escopoAtual, s);

                    this.mensagem += "Lido IDENTIFICADOR " + t.getLexema();
                } else {
                    this.erro(t, "IDENTIFICADOR");
                }
            }

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("VIRGULA")) {
                    this.mensagem += "Lido VIRGULA " + t.getLexema() + "\n";
                } else {
                    if (t.getClassificacao().equals("PONTO_VIRGULA")) {
                        return;
                    }
                }
            }
        }
    }

    private void analiseParteDeclaracoesSubrotinas(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
            } else {
                this.semantico.colocar(this.escopoAtual,
                        new Simbolo(t, Simbolo.PROCEDURE));
                this.escopoAtual = t.getLexema();
                this.semantico.colocarEscopo(this.escopoAtual);

                this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("AP")) {
                this.mensagem += "Lido AP" + "\n";
                this.analiseParametrosFormais(lex);
            } else {
                lex.rewindTokenCounter();
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
            } else {
                this.mensagem += "Lido PONTO_VIRGULA" + "\n";
                this.analiseBlocoSubrotina(lex);
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido PONTO_VIRGULA" + "\n";
            }
        }
    }

    private void analiseComandoComposto(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_begin")) {
                this.erro(t, "palavra_begin");
            } else {
                this.mensagem += "Lido palavra_begin" + "\n";
            }
        }

        do {
            this.analiseComando(lex);
            t = lex.getNextToken();
        } while (t.getClassificacao().equals("PONTO_VIRGULA"));

        lex.rewindTokenCounter();
        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_end")) {
                this.erro(t, "palavra_end");
            } else {
                this.mensagem += "Lido palavra_end" + "\n";
            }
        }
    }

    private void analiseParametrosFormais(LexicalAnalyzer lex) {
        Token t;
        ArrayList<Simbolo> simbolos;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("palavra_var")) {
                    this.erro(t, "palavra_var");
                } else {
                    this.mensagem += "Lido palavra_var" + "\n";
                }
            }

            simbolos = this.analiseListaIdentificadores(lex);

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("tipo_int")
                        && !t.getClassificacao().equals("tipo_boolean")) {
                    this.erro(t, "tipo_int/tipo_boolean");
                } else {
                    this.semantico.classificaParametros(simbolos, escopoAtual, t.getClassificacao());
                    this.mensagem += "Lido " + t.getClassificacao() + "\n";
                }
            }

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("FP")) {
                    return;
                } else if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                    this.erro(t, "PONTO_VIRGULA");
                    return;
                }
            }
        }
    }

    private ArrayList<Simbolo> analiseListaIdentificadores(LexicalAnalyzer lex) {
        Token t;
        ArrayList<Simbolo> simbolos = new ArrayList();
        Simbolo s;
        int ordem = 0;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                    this.erro(t, "IDENTIFICADOR");
                } else {
                    s = new Simbolo(t, Simbolo.PARAMETRO);
                    s.setOrdem(ordem++);
                    simbolos.add(s);
                    this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
                }
            }

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("VIRGULA")) {
                    continue;
                } else if (t.getClassificacao().equals("DOIS_PONTOS")) {
                    return simbolos;
                } else {
                    this.erro(t, "VIRGULA/DOIS_PONTOS");
                    return simbolos;
                }
            }
        }
    }

    private void analiseBlocoSubrotina(LexicalAnalyzer lex) {
        Token t = lex.getNextToken();

        while (t.getClassificacao().equals("tipo_boolean")
                || t.getClassificacao().equals("tipo_int")) {
            this.mensagem += "Lido " + t.getClassificacao() + " " + t.getLexema() + "\n";
            this.analiseParteDeclaracoesVariaveis(lex, t.getClassificacao());
            t = lex.getNextToken();
        }

        if (!t.getClassificacao().equals("palavra_begin")) {
            this.erro(t, "palavra_begin");
        } else {
            this.mensagem += "Lido palavra_begin" + "\n";
            lex.rewindTokenCounter();
            this.analiseComandoComposto(lex);
        }
    }

    private void analiseComando(LexicalAnalyzer lex) {
        Token t = null;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();
        }
        Token aux;

        switch (t.getClassificacao()) {
            case "IDENTIFICADOR":
                this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
                if (this.semantico.verificaDeclaracao(this.escopoAtual, t)) {
                    this.semantico.usaSimbolo(this.escopoAtual, t);
                }
                if (lex.hasNextToken()) {
                    aux = lex.getNextToken();
                    if (aux.getClassificacao().equals("OP_ATRI")) { // Atribuição
                        this.estaAtribuindo = true;
                        this.mensagem += "Lido OP_ATRI" + "\n";
                        this.booleana = false;
                        this.analiseExpressao(lex);
                        this.semantico.verificaAtribuicao(this.escopoAtual, t, this.booleana);
                        this.booleana = false;
                        this.estaAtribuindo = false;
                    } else { // Chamada de Procedimento
                        lex.rewindTokenCounter();
                        lex.rewindTokenCounter();
                        this.analiseChamadaProcedimento(lex);
                    }
                }
                break;

            case "palavra_write":
            case "palavra_read":
                this.mensagem += "Lido " + t.getClassificacao() + "\n";
                if (lex.hasNextToken()) {
                    t = lex.getNextToken();
                    if (t.getClassificacao().equals("AP")) {
                        this.mensagem += "Lido AP" + "\n";
                        this.analiseListaExpressoes(lex);

                        t = lex.getNextToken();
                        if (t.getClassificacao().equals("FP")) {
                            this.mensagem += "Lido FP" + "\n";
                        } else {
                            this.erro(t, "FP");
                        }
                    } else {
                        this.erro(t, "AP");
                    }
                }
                break;

            case "palavra_begin":
                // Comando Composto
                lex.rewindTokenCounter();
                this.analiseComandoComposto(lex);
                break;

            case "palavra_if":
                // Comando Condicional
                lex.rewindTokenCounter();
                this.comandoCondicional(lex);
                break;

            case "palavra_while":
                // Comando Repetitivo
                lex.rewindTokenCounter();
                this.comandoRepetitivo(lex);
                break;

            default:
                lex.rewindTokenCounter();
                break;
        }
    }

    private void analiseExpressao(LexicalAnalyzer lex) {
        this.analiseExpressaoSimples(lex);
    }

    private void comandoCondicional(LexicalAnalyzer lex) {
        Token t;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_if")) {
                this.erro(t, "palavra_if");
                lex.rewindTokenCounter();
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("AP")) {
                this.erro(t, "AP");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido AP" + "\n";
            }
        }

        this.analiseExpressao(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FP")) {
                this.erro(t, "FP");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido FP" + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_then")) {
                this.erro(t, "palavra_then");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido palavra_then" + "\n";
            }
        }

        do {
            this.analiseComando(lex);
            t = lex.getNextToken();
        } while (t.getClassificacao().equals("PONTO_VIRGULA"));

        lex.rewindTokenCounter();
        t = lex.getNextToken();
        if (t.getClassificacao().equals("palavra_else")) {
            this.mensagem += "Lido palavra_else" + "\n";
            do {
                this.analiseComando(lex);
                t = lex.getNextToken();
            } while (t.getClassificacao().equals("PONTO_VIRGULA"));
            lex.rewindTokenCounter();
        } else {
            lex.rewindTokenCounter();
        }
    }

    private void comandoRepetitivo(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_while")) {
                this.erro(t, "palavra_while");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido palavra_while" + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("AP")) {
                this.erro(t, "AP");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido AP" + "\n";
            }
        }

        this.analiseExpressao(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FP")) {
                this.erro(t, "FP");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido FP" + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_do")) {
                this.erro(t, "palavra_do");
                lex.rewindTokenCounter();
            } else {
                this.mensagem += "Lido palavra_do" + "\n";
            }
        }

        do {
            this.analiseComando(lex);
            t = lex.getNextToken();
        } while (t.getClassificacao().equals("PONTO_VIRGULA"));
        lex.rewindTokenCounter();
    }

    private void analiseChamadaProcedimento(LexicalAnalyzer lex) {
        Token t;
        this.escopoAux = "";

        if (lex.hasNextToken()) {
            t = lex.getNextToken();

            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
                lex.rewindTokenCounter();
            } else {
                if (this.semantico.verificaDeclaracao(this.escopoAtual, t)) {
                    this.semantico.usaSimbolo(this.escopoAtual, t);
                }
                this.escopoAux = t.getLexema();
                this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("AP")) {
                this.mensagem += "Lido AP" + "\n";
                this.semantico.getParametros(this.escopoAux);
                this.analiseParametros = true;
                this.booleana = false;
                this.analiseListaExpressoes(lex);
                this.analiseParametros = false;

                t = lex.getNextToken();
                if (!t.getClassificacao().equals(("FP"))) {
                    this.erro(t, "FP");
                }
            } else {
                if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                    this.erro(t, "PONTO_VIRGULA");
                    lex.rewindTokenCounter();
                }
            }
        }
    }

    public void analiseExpressaoSimples(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("palavra_or") && (this.analiseParametros || this.estaAtribuindo)) {
                this.booleana = true;
            }
            if (t.getClassificacao().equals("OPSOMA")
                    || t.getClassificacao().equals("OPSUB")
                    || t.getClassificacao().equals("palavra_or")) {
                this.mensagem += "Lido " + t.getClassificacao() + "\n";
            }
        }

        lex.rewindTokenCounter();
        do {
            this.analiseTermo(lex);
            t = lex.getNextToken();
            if (t.getClassificacao().equals("palavra_or") && (this.analiseParametros || this.estaAtribuindo)) {
                this.booleana = true;
            }
        } while (t.getClassificacao().equals("OPSOMA")
                || t.getClassificacao().equals("OPSUB")
                || t.getClassificacao().equals("palavra_or"));

        lex.rewindTokenCounter();

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            switch (t.getClassificacao()) {
                case "OP_MENOR":
                case "OP_MAIOR":
                case "OP_MENOR_IGUAL":
                case "OP_MAIOR_IGUAL":
                case "OP_IGUAL":
                case "OP_DIF":
                    if ((this.analiseParametros || this.estaAtribuindo)) {
                        this.booleana = true;
                    }
                    this.analiseExpressaoSimples(lex);
                    break;

                default:
                    lex.rewindTokenCounter();
                    break;
            }
        }
    }

    private void analiseTermo(LexicalAnalyzer lex) {
        Token t;
        do {
            this.analiseFator(lex);
            t = lex.getNextToken();
            if (t.getClassificacao().equals("palavra_and") && (this.analiseParametros || this.estaAtribuindo)) {
                this.booleana = true;
            }
        } while (t.getClassificacao().equals("OPDIV")
                || t.getClassificacao().equals("OPMUL")
                || t.getClassificacao().equals("palavra_and"));

        lex.rewindTokenCounter();
    }

    private void analiseFator(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();

            switch (t.getClassificacao()) {
                case "IDENTIFICADOR":
                    if (this.semantico.verificaDeclaracao(this.escopoAtual, t)) {
                        this.semantico.usaSimbolo(this.escopoAtual, t);
                    }
                    if ((this.analiseParametros || this.estaAtribuindo)) {
                        if (this.semantico.verificaBooleanVariavel(escopoAtual, t)) {
                            this.booleana = true;
                        }
                    }
                    this.mensagem += "Lido IDENTIFICADOR " + t.getLexema() + "\n";
                    break;

                case "NUM_INT":
                    this.mensagem += "Lido NUM_INT " + t.getLexema() + "\n";
                    break;

                case "NUM_REAL":
                    this.mensagem += "Lido NUM_REAL " + t.getLexema() + "\n";
                    break;

                case "AP":
                    this.analiseExpressao(lex);
                    break;

                case "palavra_not":
                    this.mensagem += "Lido palavra_not" + "\n";
                    if ((this.analiseParametros || this.estaAtribuindo)) {
                        this.booleana = true;
                    }
                    this.analiseFator(lex);
                    break;

                case "palavra_true":
                    this.mensagem += "Lido palavra_true" + "\n";
                    if ((this.analiseParametros || this.estaAtribuindo)) {
                        this.booleana = true;
                    }
                    break;

                case "palavra_false":
                    this.mensagem += "Lido palavra_false" + "\n";
                    if ((this.analiseParametros || this.estaAtribuindo)) {
                        this.booleana = true;
                    }
                    break;
            }
        }
    }

    private void analiseListaExpressoes(LexicalAnalyzer lex) {
        this.analiseExpressao(lex);

        if (this.analiseParametros) {
            this.semantico.verificaParametro(this.escopoAux, this.booleana, lex.getNextToken());
            lex.rewindTokenCounter();
            this.booleana = false;
        }

        Token t;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("VIRGULA")) {
                this.mensagem += "Lido VIRGULA" + "\n";
                this.analiseListaExpressoes(lex);
            } else {
                lex.rewindTokenCounter();
            }
        }
    }

}
