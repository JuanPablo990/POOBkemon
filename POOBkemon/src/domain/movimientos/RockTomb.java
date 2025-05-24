package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class RockTomb extends Movimiento {
    public RockTomb() {
        super("Rock Tomb", "Roca", 60, 95, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirVelocidad(1);
        System.out.println(objetivo.getNombre() + " redujo su Velocidad.");
    }
}
