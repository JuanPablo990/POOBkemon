package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class IceBall extends Movimiento {
    public IceBall() {
        super("Ice Ball", "Hielo", 30, 90, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Ice Ball aumentará su potencia si se usa en turnos consecutivos!");
    }
}
