package domain;
/**
 * Movimiento Roca: Sandstorm
 * Activa tormenta de arena (5 turnos)
 */
public class Sandstorm extends Movimiento {
    public Sandstorm() {
        super("Sandstorm", "Roca", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Una tormenta de arena azota el campo de batalla!");
        // Aquí deberías implementar la lógica para el efecto de tormenta de arena
    }
}