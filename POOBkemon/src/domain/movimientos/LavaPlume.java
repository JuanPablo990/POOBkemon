package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Fuego: Lava Plume
 * 30% de quemar al rival
 */
public class LavaPlume extends Movimiento {
    public LavaPlume() {
        super("Lava Plume", "Fuego", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.30) {
            System.out.println("¡" + objetivo.getNombre() + " sufrió quemaduras!");
            // Aquí deberías implementar la lógica para aplicar el estado "quemado"
        }
    }
}