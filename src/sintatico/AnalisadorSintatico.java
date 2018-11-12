/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sintatico;

import lexico.LexicalAnalyzer;
import lexico.Token;

/**
 *
 * @author Gustavo Gimenez
 */
public class AnalisadorSintatico {

    /**
     * Imprime o erro na console
     *
     * @param t, objeto Token lido
     * @param esperado, String do que era esperado
     */
    public void erro(Token t, String esperado) {
        System.out.println("Erro Sintático: " + esperado + " esperado, mas "
                + t.getLexema() + " encontrado \n"
                + "\t -> Linha: " + t.getLinha() + ", Coluna: "
                + t.getColunaInicial());
    }

    public void analisePrograma(LexicalAnalyzer lex) {
        Token t;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();

            if (!t.getClassificacao().equals("palavra_program")) {
                this.erro(t, "palavra_program");
            } else {
                System.out.println("Lido palavra_program " + t.getLexema());
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
            } else {
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
            } else {
                System.out.println("Lido PONTO_VIRGULA " + t.getLexema());
            }
        }

        this.analiseBloco(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FIM")) {
                this.erro(t, "FIM");
            } else {
                System.out.println("Fim do programa");
            }
        }
    }

    private void analiseBloco(LexicalAnalyzer lex) {
        Token t;

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            while (t.getClassificacao().equals("tipo_boolean")
                    || t.getClassificacao().equals("tipo_int")) {
                System.out.println("Lido " + t.getClassificacao() + " " + t.getLexema());
                this.analiseParteDeclaracoesVariaveis(lex);
                t = lex.getNextToken();
            }

            while (t.getClassificacao().equals("palavra_procedure")) {
                this.analiseParteDeclaracoesSubrotinas(lex);
                t = lex.getNextToken();
            }
        }

        lex.rewindTokenCounter();
        this.analiseComandoComposto(lex);
    }

    private void analiseParteDeclaracoesVariaveis(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("IDENTIFICADOR")) {
                    System.out.println("Lido IDENTIFICADOR " + t.getLexema());
                } else {
                    this.erro(t, "IDENTIFICADOR");
                }
            }

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("VIRGULA")) {
                    System.out.println("Lido VIRGULA " + t.getLexema());
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
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("AP")) {
                System.out.println("Lido AP");
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
                System.out.println("Lido PONTO_VIRGULA");
                this.analiseBlocoSubrotina(lex);
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido PONTO_VIRGULA");
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
                System.out.println("Lido palavra_begin");
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
                System.out.println("Lido palavra_end");
            }
        }
    }

    private void analiseParametrosFormais(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("palavra_var")) {
                    this.erro(t, "palavra_var");
                } else {
                    System.out.println("Lido palavra_var");
                }
            }

            this.analiseListaIdentificadores(lex);

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("tipo_int")
                        && !t.getClassificacao().equals("tipo_boolean")) {
                    this.erro(t, "tipo_int/tipo_boolean");
                } else {
                    System.out.println("Lido " + t.getClassificacao());
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

    private void analiseListaIdentificadores(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                    this.erro(t, "IDENTIFICADOR");
                } else {
                    System.out.println("Lido IDENTIFICADOR " + t.getLexema());
                }
            }

            if (lex.hasNextToken()) {
                t = lex.getNextToken();
                if (t.getClassificacao().equals("VIRGULA")) {
                    continue;
                } else if (t.getClassificacao().equals("DOIS_PONTOS")) {
                    return;
                } else {
                    this.erro(t, "VIRGULA/DOIS_PONTOS");
                    return;
                }
            }
        }
    }

    private void analiseBlocoSubrotina(LexicalAnalyzer lex) {
        Token t = lex.getNextToken();

        while (t.getClassificacao().equals("tipo_boolean")
                || t.getClassificacao().equals("tipo_int")) {
            System.out.println("Lido " + t.getClassificacao() + " " + t.getLexema());
            this.analiseParteDeclaracoesVariaveis(lex);
            t = lex.getNextToken();
        }

        if (!t.getClassificacao().equals("palavra_begin")) {
            this.erro(t, "palavra_begin");
        } else {
            System.out.println("Lido palavra_begin");
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
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
                if (lex.hasNextToken()) {
                    aux = lex.getNextToken();
                    if (aux.getClassificacao().equals("OP_ATRI")) { // Atribuição
                        System.out.println("Lido OP_ATRI");
                        this.analiseExpressao(lex);
                    } else { // Chamada de Procedimento
                        lex.rewindTokenCounter();
                        lex.rewindTokenCounter();
                        this.analiseChamadaProcedimento(lex);
                    }
                }
                break;

            case "palavra_write":
            case "palavra_read":
                System.out.println("Lido " + t.getClassificacao());
                if (lex.hasNextToken()) {
                    t = lex.getNextToken();
                    if (t.getClassificacao().equals("AP")) {
                        System.out.println("Lido AP");
                        this.analiseListaExpressoes(lex);

                        t = lex.getNextToken();
                        if (t.getClassificacao().equals("FP")) {
                            System.out.println("Lido FP");
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
                System.out.println("Lido AP");
            }
        }

        this.analiseExpressao(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FP")) {
                this.erro(t, "FP");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido FP");
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_then")) {
                this.erro(t, "palavra_then");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido palavra_then");
            }
        }

        do {
            this.analiseComando(lex);
            t = lex.getNextToken();
        } while (t.getClassificacao().equals("PONTO_VIRGULA"));

        lex.rewindTokenCounter();
        t = lex.getNextToken();
        if (t.getClassificacao().equals("palavra_else")) {
            System.out.println("Lido palavra_else");
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
                System.out.println("Lido palavra_while");
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("AP")) {
                this.erro(t, "AP");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido AP");
            }
        }

        this.analiseExpressao(lex);

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("FP")) {
                this.erro(t, "FP");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido FP");
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_do")) {
                this.erro(t, "palavra_do");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido palavra_do");
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

        if (lex.hasNextToken()) {
            t = lex.getNextToken();

            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
                lex.rewindTokenCounter();
            } else {
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
            }
        }

        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("AP")) {
                System.out.println("Lido AP");
                this.analiseListaExpressoes(lex);

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
            if (t.getClassificacao().equals("OPSOMA")
                    || t.getClassificacao().equals("OPSUB")
                    || t.getClassificacao().equals("palavra_or")) {
                System.out.println("Lido " + t.getClassificacao());
            }
        }

        lex.rewindTokenCounter();
        do {
            this.analiseTermo(lex);
            t = lex.getNextToken();
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
                    System.out.println("Lido IDENTIFICADOR " + t.getLexema());
                    break;

                case "NUM_INT":
                    System.out.println("Lido NUM_INT " + t.getLexema());
                    break;

                case "NUM_REAL":
                    System.out.println("Lido NUM_REAL " + t.getLexema());
                    break;

                case "AP":
                    this.analiseExpressao(lex);
                    break;

                case "palavra_not":
                    System.out.println("Lido palavra_not");
                    this.analiseFator(lex);
                    break;

                case "palavra_true":
                    System.out.println("Lido palavra_true");
                    break;

                case "palavra_false":
                    System.out.println("Lido palavra_false");
                    break;
            }
        }
    }

    private void analiseListaExpressoes(LexicalAnalyzer lex) {
        this.analiseExpressao(lex);

        Token t;
        if (lex.hasNextToken()) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("VIRGULA")) {
                System.out.println("Lido VIRGULA");
                this.analiseListaExpressoes(lex);
            } else {
                lex.rewindTokenCounter();
            }
        }
    }

}
