package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class IceShard extends Movimiento {
    public IceShard() {
        super("Ice Shard", "Hielo", 40, 100, 30, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
