/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arquivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Gustavo
 */
public class GerenciadorArquivos {

    /**
     *
     * Abre um arquivo em texto e retonar um ArrayList com String das linhas
     *
     * @return ArrayList de String
     */
    public String abrirArquivo() {
        // Variáveis para abertura da janela de seleção de arquivos
        JFileChooser fc = new JFileChooser("C:\\");
        int result = fc.showOpenDialog(fc);
        StringBuilder texto = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            // Caso dê certo
            String fileName = fc.getSelectedFile().getAbsolutePath();
            BufferedReader in = null;

            try {
                // Tentativa de abertura de arquivos
                in = new BufferedReader(new FileReader(fileName));

                // Variáveis para auxiliar a leitura do arquivo
                String linha;
                texto = new StringBuilder();

                // Loop para a leitura do arquivo
                linha = in.readLine();
                while (linha != null) {
                    texto.append(linha);
                    linha = in.readLine();
                }

            } catch (IOException e) {
            } finally {
                // Fechando o BufferedReader
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(GerenciadorArquivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return texto.toString();
    }

    /**
     *
     * Recebe um texto e o salva em um arquivo texto
     *
     * @param texto usado para popular o arquivo
     * 
     * @return boolean com o resultado da operação
     */
    public boolean salvarArquivo(String texto) {
        if (texto.length() > 0) {
            // Se realmente houver texto
            String[] linhas = texto.split("\n");
            JFileChooser fc = new JFileChooser("C:\\");
            int result;
            boolean retorno = false;
            
            result  = fc.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                
                // Abrindo o arquivo
                String arquivoNome = (fc.getSelectedFile().getAbsolutePath());;
                try {
                    PrintWriter arquivo = new PrintWriter(arquivoNome, "UTF-8");
                    
                    // Escrevendo cada linha no arquivo
                    for (int i = 0; i < linhas.length; i++) {
                        arquivo.write(linhas[i] + "\n");
                    }
                    // Fechando o arquivo e retorno TRUE
                    arquivo.close();
                    retorno = true;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GerenciadorArquivos.class.getName()).log(Level.SEVERE, null, ex);
                    retorno = false;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(GerenciadorArquivos.class.getName()).log(Level.SEVERE, null, ex);
                    retorno = false;
                }
                return retorno;
            }
            else {
                // O feche a caixa de diálogo
                return false;
            }
        } else {
            // Se não houver texto
            return false;
        }
    }
}
