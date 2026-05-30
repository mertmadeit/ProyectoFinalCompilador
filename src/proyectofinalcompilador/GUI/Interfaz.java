/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectofinalcompilador.GUI;

import proyectofinalcompilador.MVC.controller.CompiladorController;
import proyectofinalcompilador.MVC.model.ResultadoCompilacion;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author MERT
 */
@SuppressWarnings({ "serial", "this-escape" })
public class Interfaz extends javax.swing.JFrame {
        private static final long serialVersionUID = 1L;

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(Interfaz.class.getName());
        private javax.swing.JTable tableTokensLista;
        private DefaultTableModel modeloTokensLista;
        private DefaultTableModel modeloSimbolosLista;
        private javax.swing.JTextArea textAreaProducciones;
        private javax.swing.JTextArea textAreaPila;
        private final CompiladorController controlador;

        /**
         * Creates new form Interfaz
         */
        public Interfaz() {
                controlador = new CompiladorController();
                initComponents();
                inicializarTablaTokensLista();
                inicializarVistaAnalizador();
                jTabbedPane1.setSelectedIndex(1);
        }

        private void inicializarTablaTokensLista() {
                modeloTokensLista = new DefaultTableModel(new Object[] { "Token" }, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };

                tableTokensLista = new javax.swing.JTable(modeloTokensLista);
                tableTokensLista.setTableHeader(null);
                tableTokensLista.setRowSelectionAllowed(false);
                tableTokensLista.setFocusable(false);
                javax.swing.JScrollPane scrollTokensLista = new javax.swing.JScrollPane(tableTokensLista);
                jTabbedPane1.insertTab("Tabla de Tokens", null, scrollTokensLista, null, 0);
        }

        private void inicializarVistaAnalizador() {
                javax.swing.JPanel panelVista = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 8, 8));
                panelVista.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

                javax.swing.JTable tablaTokensVista = new javax.swing.JTable(modeloTokensLista);
                tablaTokensVista.setTableHeader(null);
                tablaTokensVista.setRowSelectionAllowed(false);
                javax.swing.JScrollPane scrollTokensVista = new javax.swing.JScrollPane(tablaTokensVista);
                javax.swing.JPanel panelTokens = new javax.swing.JPanel(new java.awt.BorderLayout());
                panelTokens.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Tokens"));
                panelTokens.add(scrollTokensVista, java.awt.BorderLayout.CENTER);

                modeloSimbolosLista = new DefaultTableModel(new Object[] { "Simbolo" }, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };
                javax.swing.JTable tablaSimbolosVista = new javax.swing.JTable(modeloSimbolosLista);
                tablaSimbolosVista.setTableHeader(null);
                tablaSimbolosVista.setRowSelectionAllowed(false);
                javax.swing.JScrollPane scrollSimbolosVista = new javax.swing.JScrollPane(tablaSimbolosVista);
                javax.swing.JPanel panelSimbolos = new javax.swing.JPanel(new java.awt.BorderLayout());
                panelSimbolos.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Simbolos"));
                panelSimbolos.add(scrollSimbolosVista, java.awt.BorderLayout.CENTER);

                textAreaProducciones = new javax.swing.JTextArea();
                textAreaProducciones.setEditable(false);
                javax.swing.JScrollPane scrollProducciones = new javax.swing.JScrollPane(textAreaProducciones);
                javax.swing.JPanel panelProducciones = new javax.swing.JPanel(new java.awt.BorderLayout());
                panelProducciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Producciones Utilizadas"));
                panelProducciones.add(scrollProducciones, java.awt.BorderLayout.CENTER);

                textAreaPila = new javax.swing.JTextArea();
                textAreaPila.setEditable(false);
                javax.swing.JScrollPane scrollPila = new javax.swing.JScrollPane(textAreaPila);
                javax.swing.JPanel panelPila = new javax.swing.JPanel(new java.awt.BorderLayout());
                panelPila.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado de la pila"));
                panelPila.add(scrollPila, java.awt.BorderLayout.CENTER);

                panelVista.add(panelTokens);
                panelVista.add(panelSimbolos);
                panelVista.add(panelProducciones);
                panelVista.add(panelPila);

                jTabbedPane1.insertTab("Vista analizador", null, panelVista, null, 1);
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jMenu3 = new javax.swing.JMenu();
                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane2 = new javax.swing.JScrollPane();
                TextAreaEditor = new javax.swing.JTextArea();
                jPanel3 = new javax.swing.JPanel();
                jPanel5 = new javax.swing.JPanel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jScrollPane3 = new javax.swing.JScrollPane();
                TableLexico = new javax.swing.JTable();
                jScrollPane6 = new javax.swing.JScrollPane();
                TextAreaSintactico = new javax.swing.JTextArea();
                jScrollPane4 = new javax.swing.JScrollPane();
                TableSemantico = new javax.swing.JTable();
                jScrollPane5 = new javax.swing.JScrollPane();
                TextAreaCodigoIntermedio = new javax.swing.JTextArea();
                jScrollPane7 = new javax.swing.JScrollPane();
                TextAreaCodigoObjeto = new javax.swing.JTextArea();
                jPanel4 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                TextAreaSalida = new javax.swing.JTextArea();
                jMenuBar1 = new javax.swing.JMenuBar();
                MenuArchivo = new javax.swing.JMenu();
                MenuItemNuevo = new javax.swing.JMenuItem();
                MenuItemAbrir = new javax.swing.JMenuItem();
                MenuItemGuardar = new javax.swing.JMenuItem();
                MenuItemSalir = new javax.swing.JMenuItem();
                MenuEditar = new javax.swing.JMenu();
                MenuItemCopiar = new javax.swing.JMenuItem();
                MenuItemPegar = new javax.swing.JMenuItem();
                MenuItemCortar = new javax.swing.JMenuItem();
                MenuEjecutar = new javax.swing.JMenu();
                MenuItemCompilar = new javax.swing.JMenuItem();
                MenuAyuda = new javax.swing.JMenu();
                MenuItemAcercarDe = new javax.swing.JMenuItem();

                jMenu3.setText("jMenu3");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                setResizable(false);

                jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("IDE"));

                TextAreaEditor.setColumns(20);
                TextAreaEditor.setRows(5);
                jScrollPane2.setViewportView(TextAreaEditor);

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                570, Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                352, Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Compilador"));

                TableLexico.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Token", "Lexema", "Linea"
                                }));
                jScrollPane3.setViewportView(TableLexico);

                jTabbedPane1.addTab("Lexico", jScrollPane3);

                TextAreaSintactico.setColumns(20);
                TextAreaSintactico.setRows(5);
                jScrollPane6.setViewportView(TextAreaSintactico);

                jTabbedPane1.addTab("Sintactico", jScrollPane6);

                TableSemantico.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null }
                                },
                                new String[] {
                                                "Identificador", "Tipo", "Valor", "Alcance"
                                }));
                jScrollPane4.setViewportView(TableSemantico);

                jTabbedPane1.addTab("Semantico", jScrollPane4);

                TextAreaCodigoIntermedio.setColumns(20);
                TextAreaCodigoIntermedio.setRows(5);
                jScrollPane5.setViewportView(TextAreaCodigoIntermedio);

                jTabbedPane1.addTab("Codigo intermedio", jScrollPane5);

                TextAreaCodigoObjeto.setColumns(20);
                TextAreaCodigoObjeto.setRows(5);
                jScrollPane7.setViewportView(TextAreaCodigoObjeto);

                jTabbedPane1.addTab("Codigo objeto", jScrollPane7);

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jTabbedPane1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                552, Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                                Short.MAX_VALUE));

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel5,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel5,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Salida"));

                TextAreaSalida.setColumns(20);
                TextAreaSalida.setRows(5);
                jScrollPane1.setViewportView(TextAreaSalida);

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPane1)
                                                                .addContainerGap()));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout
                                                                .createSequentialGroup()
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                216, Short.MAX_VALUE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jPanel2,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jPanel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));

                MenuArchivo.setText("Archivo");

                MenuItemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemNuevo.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/proyectofinalcompilador/icons/icons8-new-file-16.png"))); // NOI18N
                MenuItemNuevo.setText("Nuevo");
                MenuItemNuevo.setToolTipText("");
                MenuItemNuevo.addActionListener(this::MenuItemNuevoActionPerformed);
                MenuArchivo.add(MenuItemNuevo);

                MenuItemAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemAbrir.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/proyectofinalcompilador/icons/icons8-opened-folder-16.png"))); // NOI18N
                MenuItemAbrir.setText("Abrir");
                MenuArchivo.add(MenuItemAbrir);

                MenuItemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemGuardar.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/proyectofinalcompilador/icons/icons8-save-16.png"))); // NOI18N
                MenuItemGuardar.setText("Guardar");
                MenuArchivo.add(MenuItemGuardar);

                MenuItemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemSalir.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/proyectofinalcompilador/icons/icons8-exit-16.png"))); // NOI18N
                MenuItemSalir.setText("Salir");
                MenuArchivo.add(MenuItemSalir);

                jMenuBar1.add(MenuArchivo);

                MenuEditar.setText("Editar");

                MenuItemCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemCopiar.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/proyectofinalcompilador/icons/icons8-copy-16.png"))); // NOI18N
                MenuItemCopiar.setText("Copiar");
                MenuEditar.add(MenuItemCopiar);

                MenuItemPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemPegar.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/proyectofinalcompilador/icons/icons8-paste-16.png"))); // NOI18N
                MenuItemPegar.setText("Pegar");
                MenuEditar.add(MenuItemPegar);

                MenuItemCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemCortar.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/proyectofinalcompilador/icons/icons8-cut-16.png"))); // NOI18N
                MenuItemCortar.setText("Cortar");
                MenuEditar.add(MenuItemCortar);

                jMenuBar1.add(MenuEditar);

                MenuEjecutar.setText("Ejecutar");

                MenuItemCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemCompilar.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/proyectofinalcompilador/icons/icons8-automation-16.png"))); // NOI18N
                MenuItemCompilar.setText("Compilar");
                MenuItemCompilar.addActionListener(this::MenuItemCompilarActionPerformed);
                MenuEjecutar.add(MenuItemCompilar);

                jMenuBar1.add(MenuEjecutar);

                MenuAyuda.setText("Ayuda");

                MenuItemAcercarDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I,
                                java.awt.event.InputEvent.CTRL_DOWN_MASK));
                MenuItemAcercarDe.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/proyectofinalcompilador/icons/icons8-help-16.png"))); // NOI18N
                MenuItemAcercarDe.setText("Acerca de");
                MenuAyuda.add(MenuItemAcercarDe);

                jMenuBar1.add(MenuAyuda);

                setJMenuBar(jMenuBar1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void MenuItemNuevoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MenuItemNuevoActionPerformed
                TextAreaEditor.setText("");
                TextAreaSalida.setText("");
                TextAreaSintactico.setText("");
                TextAreaCodigoIntermedio.setText("");
                TextAreaCodigoObjeto.setText("");
                ((DefaultTableModel) TableLexico.getModel()).setRowCount(0);
                ((DefaultTableModel) TableSemantico.getModel()).setRowCount(0);
                if (modeloTokensLista != null) {
                        modeloTokensLista.setRowCount(0);
                }
                if (modeloSimbolosLista != null) {
                        modeloSimbolosLista.setRowCount(0);
                }
                if (textAreaProducciones != null) {
                        textAreaProducciones.setText("");
                }
                if (textAreaPila != null) {
                        textAreaPila.setText("");
                }
        }// GEN-LAST:event_MenuItemNuevoActionPerformed

        private void MenuItemCompilarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MenuItemCompilarActionPerformed
                String codigoFuente = TextAreaEditor.getText();
                if (codigoFuente.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese código para compilar",
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                DefaultTableModel modeloLexico = (DefaultTableModel) TableLexico.getModel();
                modeloLexico.setRowCount(0);
                TextAreaSintactico.setText("");
                if (modeloTokensLista != null) {
                        modeloTokensLista.setRowCount(0);
                }
                if (modeloSimbolosLista != null) {
                        modeloSimbolosLista.setRowCount(0);
                }
                if (textAreaProducciones != null) {
                        textAreaProducciones.setText("");
                }
                if (textAreaPila != null) {
                        textAreaPila.setText("");
                }

                ResultadoCompilacion resultado = controlador.compilar(codigoFuente);

                for (proyectofinalcompilador.Compilador.CompiladorLexico.TokenInfo token : resultado.tokens) {
                        Object[] fila = { token.tipo, token.lexema, String.valueOf(token.linea) };
                        modeloLexico.addRow(fila);
                }

                for (String tokenVisual : resultado.tokensVisuales) {
                        modeloTokensLista.addRow(new Object[] { tokenVisual });
                }

                if (modeloSimbolosLista != null) {
                        for (String simbolo : resultado.simbolosVisuales) {
                                modeloSimbolosLista.addRow(new Object[] { simbolo });
                        }
                }

                DefaultTableModel modeloSemantico = (DefaultTableModel) TableSemantico.getModel();
                modeloSemantico.setRowCount(0);
                for (proyectofinalcompilador.Semantico.Simbolo simb : resultado.simbolosSemanticos) {
                        Object[] fila = { simb.getNombre(), simb.getTipo(), simb.getValor(), simb.getAlcance() };
                        modeloSemantico.addRow(fila);
                }

                TextAreaSalida.setText(resultado.salida);
                TextAreaSintactico.setText(resultado.exitoSintacticoSemantico ? resultado.resultadoSintactico
                                : resultado.errorSintacticoSemantico);

                if (resultado.exitoGeneral()) {
                        TextAreaCodigoIntermedio.setText(resultado.codigoIntermedio);
                        TextAreaCodigoObjeto.setText(resultado.codigoObjeto);
                } else {
                        TextAreaCodigoIntermedio.setText("");
                        TextAreaCodigoObjeto.setText("");
                }

                if (textAreaProducciones != null) {
                        textAreaProducciones.setText(resultado.produccionesUtilizadas);
                }
                if (textAreaPila != null) {
                        textAreaPila.setText(resultado.estadoPila);
                }
        }// GEN-LAST:event_MenuItemCompilarActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
                        logger.log(java.util.logging.Level.SEVERE, null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(() -> new Interfaz().setVisible(true));
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        public javax.swing.JMenu MenuArchivo;
        public javax.swing.JMenu MenuAyuda;
        public javax.swing.JMenu MenuEditar;
        public javax.swing.JMenu MenuEjecutar;
        public javax.swing.JMenuItem MenuItemAbrir;
        public javax.swing.JMenuItem MenuItemAcercarDe;
        public javax.swing.JMenuItem MenuItemCompilar;
        public javax.swing.JMenuItem MenuItemCopiar;
        public javax.swing.JMenuItem MenuItemCortar;
        public javax.swing.JMenuItem MenuItemGuardar;
        public javax.swing.JMenuItem MenuItemNuevo;
        public javax.swing.JMenuItem MenuItemPegar;
        public javax.swing.JMenuItem MenuItemSalir;
        public javax.swing.JTable TableLexico;
        public javax.swing.JTable TableSemantico;
        public javax.swing.JTextArea TextAreaCodigoIntermedio;
        public javax.swing.JTextArea TextAreaCodigoObjeto;
        public javax.swing.JTextArea TextAreaEditor;
        public javax.swing.JTextArea TextAreaSalida;
        public javax.swing.JTextArea TextAreaSintactico;
        private javax.swing.JMenu jMenu3;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        public javax.swing.JPanel jPanel3;
        public javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JScrollPane jScrollPane6;
        private javax.swing.JScrollPane jScrollPane7;
        public javax.swing.JTabbedPane jTabbedPane1;
        // End of variables declaration//GEN-END:variables
}

