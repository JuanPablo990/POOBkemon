 package domain;


/**
 * Movimiento Air Slash: Ataque volador que puede hacer retroceder al rival.
 */
public class AirSlash extends Movimiento {
    public AirSlash() {
        super("Air Slash", "Volador", 75, 95, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 30% de probabilidad de hacer retroceder
        if (Math.random() < 0.3) {
            System.out.println(objetivo.getNombre() + " retrocede por el impacto!");
            // En una implementación completa, esto podría afectar la selección de movimientos
        }
    }
}
