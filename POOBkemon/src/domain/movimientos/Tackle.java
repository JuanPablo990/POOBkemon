package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class Tackle extends Movimiento {
    public Tackle() {
        super("Tackle", "Normal", 40, 100, 35, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario
    }
}