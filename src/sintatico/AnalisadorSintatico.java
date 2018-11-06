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
        Token t = lex.getNextToken();

        if (!t.getClassificacao().equals("palavra_program")) {
            this.erro(t, "palavra_program");
        } else {
            System.out.println("Lido palavra_program " + t.getLexema());
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("IDENTIFICADOR")) {
            this.erro(t, "IDENTIFICADOR");
        } else {
            System.out.println("Lido IDENTIFICADOR " + t.getLexema());
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
            this.erro(t, "PONTO_VIRGULA");
        } else {
            System.out.println("Lido PONTO_VIRGULA " + t.getLexema());
        }

        this.analiseBloco(lex);

//        t = lex.getNextToken();
//        if (!t.getClassificacao().equals("FIM")) {
//            this.erro(t, "FIM");
//        } else {
//            System.out.println("Fim do programa");
//        }
    }

    private void analiseBloco(LexicalAnalyzer lex) {
        Token t = lex.getNextToken();

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

        this.analiseComandoComposto(lex);
    }

    private void analiseParteDeclaracoesVariaveis(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            t = lex.getNextToken();
            if (t.getClassificacao().equals("IDENTIFICADOR")) {
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
            } else {
                this.erro(t, "IDENTIFICADOR");
            }

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

    private void analiseParteDeclaracoesSubrotinas(LexicalAnalyzer lex) {
        Token t;

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("IDENTIFICADOR")) {
            this.erro(t, "IDENTIFICADOR");
        } else {
            System.out.println("Lido IDENTIFICADOR " + t.getLexema());
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("AP")) {
            this.erro(t, "AP");
        } else {
            System.out.println("Lido AP");
            this.analiseParametrosFormais(lex);
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
            this.erro(t, "PONTO_VIRGULA");
        } else {
            System.out.println("Lido PONTO_VIRGULA");
            this.analiseBlocoSubrotina(lex);
        }
    }

    private void analiseComandoComposto(LexicalAnalyzer lex) {
        Token t;

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("palavra_begin")) {
            this.erro(t, "palavra_begin");
        } else {
            System.out.println("Lido palara_begin");
        }

        this.analiseComando(lex);

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("palavra_end")) {
            this.erro(t, "palavra_end");
        } else {
            System.out.println("Lido palavra_end");
        }
    }

    private void analiseParametrosFormais(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("palavra_var")) {
                this.erro(t, "palavra_var");
            } else {
                System.out.println("Lido palavra_var");
            }

            this.analiseListaIdentificadores(lex);

            t = lex.getNextToken();
            if (!t.getClassificacao().equals("tipo_int")
                    && !t.getClassificacao().equals("tipo_boolean")) {
                this.erro(t, "tipo_int/tipo_boolean");
            } else {
                System.out.println("Lido " + t.getClassificacao());
            }

            t = lex.getNextToken();
            if (t.getClassificacao().equals("FP")) {
                return;
            } else if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
                this.erro(t, "PONTO_VIRGULA");
                return;
            }
        }
    }

    private void analiseListaIdentificadores(LexicalAnalyzer lex) {
        Token t;

        while (true) {
            t = lex.getNextToken();
            if (!t.getClassificacao().equals("IDENTIFICADOR")) {
                this.erro(t, "IDENTIFICADOR");
            } else {
                System.out.println("Lido IDENTIFICADOR " + t.getLexema());
            }

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
            this.analiseComandoComposto(lex);
        }

    }

    private void analiseComando(LexicalAnalyzer lex) {
        Token t = lex.getNextToken();
        Token aux;

        switch (t.getClassificacao()) {
            case "IDENTIFICADOR":
                aux = lex.getNextToken();
                if (aux.getClassificacao().equals("OP_ATRI")) { // Atribuição
                    this.analiseExpressao(lex);
                } else { // Chamada de Procedimento
                    this.analiseChamadaProcedimento(lex);
                }
                break;
                
            case "palavra_begin":
                // Comando Composto
                this.analiseComandoComposto(lex);
                break;
                
            case "palavra_if":
                // Comando Condicional
                this.comandoCondicional(lex);
                break;
                
            case "palavra_while":
                // Comando Repetitivo
                this.comandoRepetitivo(lex);
                break;
        }
    }

    private void analiseExpressao(LexicalAnalyzer lex) {
        this.analiseExpressaoSimples(lex);
    }

    private void comandoCondicional(LexicalAnalyzer lex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void comandoRepetitivo(LexicalAnalyzer lex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analiseChamadaProcedimento(LexicalAnalyzer lex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void analiseExpressaoSimples(LexicalAnalyzer lex) {
        Token t = lex.getNextToken();
        if (t.getClassificacao().equals("OPSOMA")
           || t.getClassificacao().equals("OPSUB")
           || t.getClassificacao().equals("palavra_or")) {
            System.out.println("Lido " + t.getClassificacao());
        }
        
        this.analiseTermo(lex);
        
        t = lex.getNextToken();
        switch (t.getClassificacao()) {
            case "OP_MENOR":
            case "OP_MAIOR":
            case "OP_MENOR_IGUAL":
            case "OP_MAIOR_IGUAL":
            case "OP_IGUAL":
            case "OP_DIF":
                lex.rewindTokenCounter();
                this.analiseExpressaoSimples(lex);
                break;
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
        Token t = lex.getNextToken();
        
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
        }
    }

}
