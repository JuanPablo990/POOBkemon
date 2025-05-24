package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Veneno: Poison Fang
 * 50% de envenenar gravemente
 */
public class PoisonFang extends Movimiento {
    public PoisonFang() {
        super("Poison Fang", "Veneno", 50, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.50) {
            System.out.println("¡" + objetivo.getNombre() + " fue gravemente envenenado!");
            // Implementar lógica para envenenamiento grave
        }
    }
}