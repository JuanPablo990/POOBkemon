package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class AquaJet extends Movimiento {
    public AquaJet() {
        super("Aqua Jet", "Agua", 40, 100, 20, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario
    }
}
