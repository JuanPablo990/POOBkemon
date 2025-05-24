package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class XScissor extends Movimiento {
    public XScissor() {
        super("X-Scissor", "Bicho", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
