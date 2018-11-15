/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Gustavo Gimenez
 */
public class ControladorSemantico {

    /**
     * Guarda as tabelas de cada escopo do programa
     */
    private HashMap<String, Tabela> tabelas;

    public ControladorSemantico() {
        this.tabelas.clear();
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
}
