
/*
 * Principal.java
 *
 * Created on 01-abr-2014, 18:45:23
 */
package exercicio161;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROBERTOVA
 */
public class Principal extends javax.swing.JFrame {

    BolsaEnBD b = new BolsaEnBD();
    InversorEnBD i;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        b.setBd("jdbc:mysql://localhost:3307/bolsa", "root", "root");
        if (b.iniciar()) {
            jTextArea1.append("Conexion con BD Correcta\n");
            jTextArea1.append(b.resumir());
        } else {
            jTextArea1.append("Non hai conexion con BD\n");
        }
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
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Actualizar Datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Novo Usuario");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Identificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Comprar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Vender");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Valorar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (b.actualizar()) {
        try {
            PreparedStatement statement = b.getCon().prepareStatement("SELECT * FROM valores");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                jTextArea1.append(rs.getInt("id") + " " + rs.getString("nome") + " " + rs.getFloat("prezo") + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Login user = new Login(this, true);
    user.setLocationRelativeTo(this);
    user.setVisible(true);
    if (b.novo(user.getLogin(), user.getPass(), user.getPosicion())) {
        jTextArea1.append("Usuario Creado Correctamente\n");
        try {
            PreparedStatement statement = b.getCon().prepareStatement("SELECT id,login,capital FROM USUARIOS WHERE login=? AND clave=?");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPass());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                i = new InversorEnBD(rs.getInt("id"), rs.getString("login"), rs.getFloat("capital"), b);
                jTextArea1.append("Esta traballando como o usuario " + i.getId() + " " + i.getLogin() + " Capital: " + i.getCapital() + "\n" + "A sua posicion actual e de: " + String.valueOf(i.valorar()) + "\n");
                jButton4.setEnabled(true);
                jButton5.setEnabled(true);
                jButton6.setEnabled(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        jTextArea1.append("O Usuario Xa Existe\n");
    }

}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    Login user = new Login(this, true);
    user.setLocationRelativeTo(this);
    user.ocultarPosicion();
    user.setVisible(true);
    if (b.identificar(user.getLogin(), user.getPass())) {
        try {
            PreparedStatement statement = b.getCon().prepareStatement("SELECT id,login,capital FROM USUARIOS WHERE login=? AND clave=?");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPass());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                i = new InversorEnBD(rs.getInt("id"), rs.getString("login"), rs.getFloat("capital"), b);
                jTextArea1.append("Esta traballando como o usuario " + i.getId() + " " + i.getLogin() + " Capital: " + i.getCapital() + "\n" + "A sua posicion actual e de: " + String.valueOf(i.valorar()) + "\n");
                jButton4.setEnabled(true);
                jButton5.setEnabled(true);
                jButton6.setEnabled(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        jTextArea1.append("O Usuario non existe debe crealo\n");
    }
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Valores v = new Valores(this, true, b);
        v.setLocationRelativeTo(this);
        v.setVisible(true);
        i.comprar(v.idValor(), v.cantidadeAccions());
        jTextArea1.append("Comprou " + v.cantidadeAccions() + " accions de " + v.nomeValor() + " O novo Capital: " + i.getCapital() + "\n" + "A sua posicion actual e de: " + String.valueOf(i.valorar()) + "\n");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Valores v = new Valores(this, true, b);
        v.setLocationRelativeTo(this);
        v.nomeBoton("Vender");
        v.setVisible(true);
        i.vender(v.idValor(), v.cantidadeAccions());
        jTextArea1.append("Vendeu " + v.cantidadeAccions() + " accions de " + v.nomeValor() + " O novo Capital: " + i.getCapital() + "\n" + "A sua posicion actual e de: " + String.valueOf(i.valorar()) + "\n");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        try {
            PreparedStatement statement1 = b.getCon().prepareStatement("SELECT * FROM usuario_valor INNER JOIN valores on usuario_valor.id_valor=valores.id WHERE id_usuario=?");
            statement1.setInt(1, i.getId());
            ResultSet rs = statement1.executeQuery();
            while (rs.next()) {
                jTextArea1.append("Posue " + rs.getInt("cantidade") + " accions de " + rs.getString("nome") + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(InversorEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea1.append("A sua posicion actual e de: " + String.valueOf(i.valorar()) + "\n");
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Principal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
