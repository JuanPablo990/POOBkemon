package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class KnockOff extends Movimiento {
    public KnockOff() {
        super("Knock Off", "Siniestro", 65, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(objetivo.getNombre() + " perdió su objeto.");
        // No se maneja inventario de objetos, solo mensaje
    }
}
