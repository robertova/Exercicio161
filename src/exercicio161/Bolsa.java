/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicio161;

/**
 *
 * @author ROBERTOVA
 */
public interface Bolsa {

    boolean iniciar();

    boolean actualizar();

    boolean novo(String login, String clave, float capital);

    boolean identificar(String login, String clave);
}
