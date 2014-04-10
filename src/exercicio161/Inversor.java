
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
