/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

import java.io.File;
import java.nio.file.Paths;

public class Gerador {

    public static void main(String[] args) {

        String file = "C:/Users/Gustavo Gimenez/Documents/Unesp/4_ano/2_semestre/Compiladores/Compilador/src/lexico/language.lex";

        File sourceCode = new File(file);

        jflex.Main.generate(sourceCode);

    }
}
