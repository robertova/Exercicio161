
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

class InversorEnBD implements Inversor,Resumible {

    private int id;
    private String login;
    private float capital;
    BolsaEnBD b;

    public InversorEnBD(int id, String login, float capital,BolsaEnBD b) {
        this.id = id;
        this.login = login;
        this.capital = capital;
        this.b = b;
    }

    @Override
    public boolean comprar(int id, int cantidade) {
        boolean x = false;
        try {
            /*Consultar se o usuario ten accions deste valor*/
            PreparedStatement statement1 = b.getCon().prepareStatement("SELECT * FROM usuario_valor INNER JOIN valores on usuario_valor.id_valor=valores.id WHERE id_usuario=? AND id_valor=?");
            statement1.setInt(1, this.getId());
            statement1.setInt(2, id);
            ResultSet resultado1 = statement1.executeQuery();
            if (resultado1.isBeforeFirst()) {
                while (resultado1.next()) {
                    /*Actualizacion do valor do usuario se xa tiña accions deste valor*/
                    setCapital(getCapital() - cantidade * resultado1.getFloat("prezo"));
                    cantidade += resultado1.getInt("cantidade");
                    PreparedStatement statement2 = b.getCon().prepareStatement("UPDATE usuario_valor SET cantidade=? WHERE id_usuario=? AND id_valor=?");
                    statement2.setInt(1, cantidade);
                    statement2.setInt(2, this.getId());
                    statement2.setInt(3, id);
                    statement2.execute();
                    statement2 = b.getCon().prepareStatement("UPDATE usuarios SET capital=? WHERE id=?");
                    statement2.setFloat(1, getCapital());
                    statement2.setInt(2, this.getId());
                    statement2.execute();
                }
            } else {
                /*Insercion na BD da cantidade que compra o usuario deste valor se non tiña accions*/
                PreparedStatement statement3 = b.getCon().prepareStatement("SELECT prezo FROM valores WHERE id=?");
                statement3.setInt(1, id);
                ResultSet resultado2 = statement3.executeQuery();
                while (resultado2.next()) {
                    setCapital(getCapital() - cantidade * resultado2.getFloat("prezo"));
                }
                statement3 = b.getCon().prepareStatement("INSERT INTO usuario_valor VALUES(?,?,?)");
                statement3.setInt(1, this.getId());
                statement3.setInt(2, id);
                statement3.setInt(3, cantidade);
                statement3.execute();
                statement3 = b.getCon().prepareStatement("UPDATE usuarios SET capital=? WHERE id=?");
                statement3.setFloat(1, getCapital());
                statement3.setInt(2, this.getId());
                statement3.execute();
            }
            x = true;
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public boolean vender(int id, int cantidade) {
        boolean x = false;
        try {
            /*Consultar se o usuario ten accions deste valor*/
            PreparedStatement statement1 = b.getCon().prepareStatement("SELECT * FROM usuario_valor INNER JOIN valores on usuario_valor.id_valor=valores.id WHERE id_usuario=? AND id_valor=?");
            statement1.setInt(1, this.getId());
            statement1.setInt(2, id);
            ResultSet resultado1 = statement1.executeQuery();
            if (resultado1.isBeforeFirst()) {
                while (resultado1.next()) {
                    /*Actualizacion do valor do usuario se xa tiña accions deste valor*/
                    setCapital(getCapital() + cantidade * resultado1.getFloat("prezo"));
                    cantidade = resultado1.getInt("cantidade") - cantidade;
                    PreparedStatement statement2 = b.getCon().prepareStatement("UPDATE usuario_valor SET cantidade=? WHERE id_usuario=? AND id_valor=?");
                    statement2.setInt(1, cantidade);
                    statement2.setInt(2, this.getId());
                    statement2.setInt(3, id);
                    statement2.execute();
                    statement2 = b.getCon().prepareStatement("UPDATE usuarios SET capital=? WHERE id=?");
                    statement2.setFloat(1, getCapital());
                    statement2.setInt(2, this.getId());
                    statement2.execute();
                    /*Borrar tupla se a cantidade do valor e 0*/
                    statement2 = b.getCon().prepareStatement("SELECT cantidade FROM usuario_valor WHERE id_usuario=? AND id_valor=?");
                    statement2.setInt(1, this.getId());
                    statement2.setInt(2, id);
                    ResultSet rs = statement2.executeQuery();
                    while (rs.next()) {
                        if (rs.getInt("cantidade") == 0) {
                            statement2 = b.getCon().prepareStatement("DELETE FROM usuario_valor WHERE id_usuario=? AND id_valor=?");
                            statement2.setInt(1, this.getId());
                            statement2.setInt(2, id);
                            statement2.execute();
                        }
                    }                 
                }
                x = true;
            } else {
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public float valorar() {
        float x = 0;
        try {         
            PreparedStatement statement1 = b.getCon().prepareStatement("SELECT * FROM usuario_valor INNER JOIN valores on usuario_valor.id_valor=valores.id WHERE id_usuario=?");
            statement1.setInt(1, this.getId());
            ResultSet resultado1 = statement1.executeQuery();  
            while (resultado1.next()) {
                x += resultado1.getInt("cantidade") * resultado1.getFloat("prezo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(InversorEnBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    @Override
    public String resumir() {
        return String.valueOf(this.id) + " " + this.capital;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCapital() {
        return capital;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
