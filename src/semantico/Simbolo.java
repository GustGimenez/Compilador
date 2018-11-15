package semantico;

import lexico.Token;

/**
 *
 * @author Gustavo Gimenez
 */
public class Simbolo {

    private Token token;
    private String valor;
    private boolean utilizada;
    private int categaoria;
    private String tipo;

    public static final int PROCEDURE = 1;

    public static final int VARIAVEL = 2;

    public static final int PARAMETRO = 3;

    public Simbolo(Token token, String valor, int categoria) {
        this.token = token;
        this.valor = valor;
        this.utilizada = false;
        this.categaoria = categoria;
    }

    public String getClassificacao() {
        return this.token.getClassificacao();
    }

    public String getLexema() {
        return this.token.getLexema();
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
