/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercicio161;

/**
 *
 * @author ROBERTOVA
 */

public interface Inversor {

    boolean comprar(int id, int cantidade);

    boolean vender(int id, int cantidade);

    float valorar();
}
