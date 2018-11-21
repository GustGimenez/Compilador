package semantico;

import java.util.Comparator;
import lexico.Token;

/**
 *
 * @author Gustavo Gimenez
 */
public class Simbolo {

    private Token token;
    private boolean utilizada;
    private int categaoria;
    private String tipo;
    private int ordem;

    public static final int PROCEDURE = 1;

    public static final int VARIAVEL = 2;

    public static final int PARAMETRO = 3;

    public Simbolo(Token token, int categoria) {
        this.token = token;
        this.utilizada = false;
        this.categaoria = categoria;
        this.tipo = "--";
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    public int getCategaoria() {
        return categaoria;
    }

    public void setCategaoria(int categaoria) {
        this.categaoria = categaoria;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public int getLinha() {
        return this.token.getLinha();
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
}
