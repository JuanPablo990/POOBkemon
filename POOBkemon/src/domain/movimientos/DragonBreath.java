package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class DragonBreath extends Movimiento {
    public DragonBreath() {
        super("Dragon Breath", "Dragón", 60, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.30) {
            System.out.println(objetivo.getNombre() + " quedó paralizado.");
        }
    }
}
