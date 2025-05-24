package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class WillOWisp extends Movimiento {
    public WillOWisp() {
        super("Will-O-Wisp", "Fuego", 0, 85, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(objetivo.getNombre() + " fue quemado.");
    }
}
