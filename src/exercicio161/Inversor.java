/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicio161;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROBERTOVA
 */

interface Inversor {

    boolean comprar(int id, int cantidade);

    boolean vender(int id, int cantidade);

    float valorar();
}



class InversorEnBD implements Inversor {
    static Connection con;
    private int id; 
    
    @Override
    public boolean comprar(int id, int cantidade) {
        boolean x = false;
        try {        
            BolsaEnBD.getCon().prepareStatement("");
            
        } catch (SQLException ex) {
            Logger.getLogger(InversorEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public boolean vender(int id, int cantidade) {
        boolean x = false;
        return x;
    }

    @Override
    public float valorar() {
        float x = 0;
        return x;
    }
}
