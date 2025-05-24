package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class ExtremeSpeed extends Movimiento {
    public ExtremeSpeed() {
        super("Extreme Speed", "Normal", 80, 100, 5, 2);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
