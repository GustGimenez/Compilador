/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import arquivos.GerenciadorArquivos;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import lexico.Token;
import lexico.LexicalAnalyzer;

/**
 *
 * @author Gustavo
 */
public class IPrincipal extends javax.swing.JFrame {

    private LexicalAnalyzer lexico;
    private GerenciadorArquivos arqs;
    private TableCellRenderer colorir;
    private ArrayList<String> palavrasReservadas;

    /**
     * Creates new form IPrincipal
     */
    public IPrincipal() {
        initComponents();
        this.arqs = new GerenciadorArquivos();
        this.inicializaPalavrasReservadas();
    }

    /**
     * Inicializa o vetor de palavras reservadas com as palavras da linguagem
     */
    private void inicializaPalavrasReservadas() {
        this.palavrasReservadas = new ArrayList();

        palavrasReservadas.add("if");
        palavrasReservadas.add("then");
        palavrasReservadas.add("else");
        palavrasReservadas.add("do");
        palavrasReservadas.add("while");
        palavrasReservadas.add("for");
        palavrasReservadas.add("var");
        palavrasReservadas.add("begin");
        palavrasReservadas.add("end");
        palavrasReservadas.add("div");
        palavrasReservadas.add("and");
        palavrasReservadas.add("not");
        palavrasReservadas.add("true");
        palavrasReservadas.add("false");
        palavrasReservadas.add("program");
        palavrasReservadas.add("int");
        palavrasReservadas.add("boolean");
        palavrasReservadas.add("read");
        palavrasReservadas.add("write");
        palavrasReservadas.add("procedure");
    }

    /**
     * Pega o texto no editor de texto e retona a última palavra dividade por
     * espaço
     *
     * @return String
     */
    private String getUltimaPalavra() {
        String texto = this.EditorTexto.getText();
        String[] palavras = texto.split(" ");

        return palavras[palavras.length - 1];
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        EditorTexto = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaTokens = new javax.swing.JTable();
        BarraMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        AbrirArquivo = new javax.swing.JMenuItem();
        SalvarArquivo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        AnalisarLexico = new javax.swing.JMenuItem();
        AnalisarSintatico = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EditorTexto.setContentType("text"); // NOI18N
        EditorTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EditorTextoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(EditorTexto);

        TabelaTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Classificação", "Linha", "Coluna Inicial", "Coluna Final"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaTokens.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(TabelaTokens);
        if (TabelaTokens.getColumnModel().getColumnCount() > 0) {
            TabelaTokens.getColumnModel().getColumn(0).setResizable(false);
            TabelaTokens.getColumnModel().getColumn(1).setResizable(false);
            TabelaTokens.getColumnModel().getColumn(2).setResizable(false);
            TabelaTokens.getColumnModel().getColumn(4).setResizable(false);
        }

        jMenu1.setText("Arquivo");

        AbrirArquivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        AbrirArquivo.setText("Abrir");
        AbrirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirArquivoActionPerformed(evt);
            }
        });
        jMenu1.add(AbrirArquivo);

        SalvarArquivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SalvarArquivo.setText("Salvar");
        SalvarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarArquivoActionPerformed(evt);
            }
        });
        jMenu1.add(SalvarArquivo);

        BarraMenu.add(jMenu1);

        jMenu2.setText("Análise");

        AnalisarLexico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        AnalisarLexico.setText("Léxica");
        AnalisarLexico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalisarLexicoActionPerformed(evt);
            }
        });
        jMenu2.add(AnalisarLexico);

        AnalisarSintatico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        AnalisarSintatico.setText("Sintática");
        AnalisarSintatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalisarSintaticoActionPerformed(evt);
            }
        });
        jMenu2.add(AnalisarSintatico);

        BarraMenu.add(jMenu2);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AnalisarLexicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalisarLexicoActionPerformed
        String entrada = this.EditorTexto.getText();
        ArrayList<Token> tokens;
        this.lexico = new LexicalAnalyzer(new StringReader(entrada));
        try {
            this.lexico.yylex();
            tokens = this.lexico.getTokens();
            this.populaTabelaTokens(tokens);
        } catch (IOException ex) {
            Logger.getLogger(IPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AnalisarLexicoActionPerformed

    private void AbrirArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirArquivoActionPerformed
        // Abertura do arquivo
        String texto = this.arqs.abrirArquivo();
        texto = texto.replace("[", "");
        texto = texto.replace("]", "");

        // Colocando o texto no editor
        this.EditorTexto.setText(texto);
    }//GEN-LAST:event_AbrirArquivoActionPerformed

    private void SalvarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarArquivoActionPerformed
        this.arqs.salvarArquivo(this.EditorTexto.getText());
    }//GEN-LAST:event_SalvarArquivoActionPerformed

    private void EditorTextoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EditorTextoKeyReleased

    }//GEN-LAST:event_EditorTextoKeyReleased

    private void AnalisarSintaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalisarSintaticoActionPerformed
        //pass
    }//GEN-LAST:event_AnalisarSintaticoActionPerformed

    private void populaTabelaTokens(ArrayList<Token> tokens) {
        this.colorir = new Colorir(tokens);
        TableColumn coluna;
        DefaultTableModel tabela = (DefaultTableModel) this.TabelaTokens.getModel();

        tabela.setNumRows(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            tabela.setValueAt(tokens.get(i).getLexema(), i, 0);
            tabela.setValueAt(tokens.get(i).getClassificacao(), i, 1);
            tabela.setValueAt(tokens.get(i).getLinha(), i, 2);
            tabela.setValueAt(tokens.get(i).getColunaInicial(), i, 3);
            tabela.setValueAt(tokens.get(i).getColunaFinal(), i, 4);
        }

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            coluna = this.TabelaTokens.getColumnModel().getColumn(i);
            coluna.setCellRenderer(this.colorir);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPrincipal().setVisible(true);
            }
        });
    }

    private class Colorir extends DefaultTableCellRenderer {

        ArrayList<Token> tokens;

        public Colorir(ArrayList<Token> tokens) {
            this.tokens = tokens;
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);

            if (this.tokens.get(row).getClassificacao().equals("DESCONHECIDO")) {
                setBackground(Color.RED);
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AbrirArquivo;
    private javax.swing.JMenuItem AnalisarLexico;
    private javax.swing.JMenuItem AnalisarSintatico;
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JTextPane EditorTexto;
    private javax.swing.JMenuItem SalvarArquivo;
    private javax.swing.JTable TabelaTokens;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
