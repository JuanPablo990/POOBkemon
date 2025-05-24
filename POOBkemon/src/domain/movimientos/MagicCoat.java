package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Psíquico: Magic Coat
 * Efecto secundario: Refleja movimientos de estado
 */
public class MagicCoat extends Movimiento {
    public MagicCoat() {
        super("Magic Coat", "Psíquico", 0, 100, 15, 4); // Prioridad +4
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento normalmente no hace daño, solo refleja efectos de estado
        System.out.println("¡" + usuario.getNombre() + " se cubrió con Magic Coat!");
    }
}