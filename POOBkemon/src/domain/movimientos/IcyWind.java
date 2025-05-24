package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class IcyWind extends Movimiento {
    public IcyWind() {
        super("Icy Wind", "Hielo", 55, 95, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirVelocidad(1);
        System.out.println(objetivo.getNombre() + " redujo su Velocidad.");
    }
}
