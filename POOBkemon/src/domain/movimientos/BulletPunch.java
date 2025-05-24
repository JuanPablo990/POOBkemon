package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class BulletPunch extends Movimiento {
    public BulletPunch() {
        super("Bullet Punch", "Acero", 40, 100, 30, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario
    }
}
