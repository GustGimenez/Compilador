package semantico;

import java.util.HashMap;

/**
 *
 * @author Gustavo Gimenez
 */
public class Tabela {

    private HashMap<String, Simbolo> simbolos;

    public Tabela() {
        this.simbolos = new HashMap();
    }

    public HashMap<String, Simbolo> getSimbolos() {
        return simbolos;
    }
    
    /**
     * Insere um item à tabela, se já não estiver declarado
     * 
     * @param s objeto Simbolo a ser inserido
     * @param nome_var String com o nome do escopo
     */
    public void addSimbolo(Simbolo s, String nome_var) {
        this.simbolos.put(nome_var, s);
    }
    
    /**
     * Recupera um símbolo presente na tabela
     * 
     * @param lexema que descreve o símbolo
     * 
     * @return Simbolo
     */
    public Simbolo getSimbolo(String lexema) {
        return this.simbolos.get(lexema);
    }
}
