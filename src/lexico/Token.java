/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

/**
 *
 * @author Gustavo
 */
public class Token {

    private String lexema;
    private String classificacao;
    private int linha;
    private int colunaInicial;
    private int colunaFinal;

    public Token(String classificacao, String lexema, int linha, int coluna) {
        this.lexema = lexema;
        this.classificacao = classificacao;
        this.linha = linha;
        this.colunaInicial = coluna;
        this.colunaFinal = colunaInicial + this.lexema.length() - 1;
    }

    public void imprimeAttrs() {
        System.out.println(this.lexema + "\t" + this.classificacao);
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getLinha() {
        return linha;
    }

    public int getColunaInicial() {
        return colunaInicial;
    }

    public int getColunaFinal() {
        return colunaFinal;
    }

    
}
