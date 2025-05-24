package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class ShadowSneak extends Movimiento {
    public ShadowSneak() {
        super("Shadow Sneak", "Fantasma", 40, 100, 30, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
