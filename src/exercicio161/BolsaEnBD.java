/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicio161;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author ROBERTOVA
 */




class BolsaEnBD implements Bolsa {
    private static Connection con;

    public static Connection getCon() {
        return con;
    }
    
    
    @Override
    public boolean iniciar() {
        boolean x = false;
        try {          
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bolsa","root","root");
            x = true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public boolean actualizar() {
        boolean x = false;
        try {
            PreparedStatement statement = getCon().prepareStatement("SELECT * FROM valores");
            ResultSet rs = statement.executeQuery();
            x = true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public boolean novo(String login, String clave, float capital) {
        boolean x = false;
        if (identificar(login, clave) == false) {
            try {
                PreparedStatement statement = getCon().prepareStatement("INSERT INTO usuarios (login,clave,capital) VALUES (?,?,?)");
                statement.setString(1, login);
                statement.setString(2, clave);
                statement.setFloat(3, capital);
                statement.execute();
                x = true;
            } catch (SQLException ex) {
                Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return x;
    }

    @Override
    public boolean identificar(String login, String clave) {
        boolean x = false;
        try {          
            PreparedStatement statement = getCon().prepareStatement("SELECT * FROM usuarios WHERE login=? AND clave=?");
            statement.setString(1, login);
            statement.setString(2, clave);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            x = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
}
