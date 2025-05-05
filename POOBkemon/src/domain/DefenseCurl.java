
package domain;
/**
 * Movimiento Normal: Defense Curl
 * Aumenta Defensa del usuario
 */
public class DefenseCurl extends Movimiento {
    public DefenseCurl() {
        super("Defense Curl", "Normal", 0, 100, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarDefensa(1);
        System.out.println("¡" + usuario.getNombre() + " aumentó su Defensa!");
    }
}