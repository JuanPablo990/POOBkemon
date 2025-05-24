package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Psíquico: Barrier
 * Aumenta Defensa en 2 niveles
 */
public class Barrier extends Movimiento {
    public Barrier() {
        super("Barrier", "Psíquico", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarDefensa(2);
        System.out.println("¡" + usuario.getNombre() + " aumentó mucho su Defensa!");
    }
}