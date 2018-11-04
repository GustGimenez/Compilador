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
                || t.getClassificacao().equals("tipo_int")){
            System.out.println("Lido " + t.getClassificacao() + " " + t.getLexema());
            this.analiseParteDeclaracoesVariaveis(lex);
            t = lex.getNextToken();
        }
        
//        while (t.getClassificacao().equals("palavra_procedure")){
//            this.analiseParteDeclaracoesSubrotinas(lex);
//            t = lex.getNextToken();
//        }
//
//        this.analiseComandoComposto(lex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void analiseComandoComposto(LexicalAnalyzer lex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
