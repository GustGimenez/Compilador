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
        String file = "src/lexico/language.lex";

        File sourceCode = new File(file);

        jflex.Main.generate(sourceCode);
    }
}
