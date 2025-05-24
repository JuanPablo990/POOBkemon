package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class AquaTail extends Movimiento {
    public AquaTail() {
        super("Aqua Tail", "Agua", 90, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
