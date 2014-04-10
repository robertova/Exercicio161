
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
class BolsaEnBD implements Bolsa, Resumible {

    private Connection con;
    private String bd = "";
    private String user = "";
    private String pass = "";

    public Connection getCon() {
        return con;
    }

    public void setBd(String bd, String user, String pass) {
        this.bd = bd;
        this.user = user;
        this.pass = pass;
    }

    @Override
    public boolean iniciar() {
        boolean x = false;
        try {
            con = DriverManager.getConnection(bd, user, pass);
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
            PreparedStatement statement = getCon().prepareStatement("UPDATE valores SET prezo=prezo*?");
            statement.setDouble(1, 1 + Math.random() * 10 / 100 - Math.random() * 10 / 100);
            statement.execute();
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

    @Override
    public String resumir() {
        String resultado = "Url da bolsa: " + this.bd + "\n";
        try {
            PreparedStatement statement = getCon().prepareStatement("SELECT COUNT(*) AS conta FROM usuarios");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                resultado += "Numero de usuarios na bolsa: " + rs.getInt("conta") + "\n";
            }
            statement = getCon().prepareStatement("SELECT capital FROM usuarios");
            rs = statement.executeQuery();
            float capital = 0;
            while (rs.next()) {
                capital += rs.getFloat("capital");
            }
            statement = getCon().prepareStatement("SELECT * FROM usuario_valor INNER JOIN valores ON usuario_valor.id_valor=valores.id");
            rs = statement.executeQuery();
            float valor = 0;
            while (rs.next()) {
                valor += rs.getInt("cantidade") * rs.getFloat("prezo");
            }
            resultado += "Capital total dos usuarios: " + capital + "\n" + "Valor das accions dos usuarios: " + valor + "\n";
        } catch (SQLException ex) {
            Logger.getLogger(BolsaEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
