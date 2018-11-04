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
            System.out.println("Lido palavra_program");
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("IDENTIFICADOR")) {
            this.erro(t, "IDENTIFICADOR");
        }else {
            System.out.println("Lido IDENTIFICADOR");
        }

        t = lex.getNextToken();
        if (!t.getClassificacao().equals("PONTO_VIRGULA")) {
            this.erro(t, "PONTO_VIRGULA");
        }else {
            System.out.println("Lido PONTO_VIRGULA");
        }

        //this.analiseBloco(lex);
    }
}
