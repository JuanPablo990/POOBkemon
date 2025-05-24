package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Eléctrico: Thunder Fang
 * 10% de paralizar o hacer retroceder
 */
public class ThunderFang extends Movimiento {
    public ThunderFang() {
        super("Thunder Fang", "Eléctrico", 65, 95, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        double random = Math.random();
        if (random < 0.10) {
            System.out.println("¡" + objetivo.getNombre() + " fue paralizado!");
            // Implementar lógica de paralización
        } else if (random < 0.20) {
            System.out.println("¡" + objetivo.getNombre() + " retrocedió!");
            // Implementar lógica de retroceso (flinch)
        }
    }
}