package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Eléctrico: Zap Cannon
 * Paraliza al rival
 */
public class ZapCannon extends Movimiento {
    public ZapCannon() {
        super("Zap Cannon", "Eléctrico", 120, 50, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡" + objetivo.getNombre() + " fue paralizado!");
        // Aquí deberías implementar la lógica para aplicar el estado "paralizado"
    }
}