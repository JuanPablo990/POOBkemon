package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class HydroPump extends Movimiento {
    public HydroPump() {
        super("Hydro Pump", "Agua", 110, 80, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario
    }
}