package domain;
/**
 * Movimiento Tierra: Earthquake
 * Afecta a todos en el campo
 */
public class Earthquake extends Movimiento {
    public Earthquake() {
        super("Earthquake", "Tierra", 100, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // La implementación de afectar a todos debería manejarse en la lógica de batalla
        System.out.println("¡El terremoto sacude todo el campo de batalla!");
    }
}