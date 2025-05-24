package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que reduce la velocidad del oponente
 */
public class BajarVelocidad extends Movimiento {
    public BajarVelocidad() {
    	super("Bajar Velocidad", "normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirVelocidad(1); // Usa el método existente
        System.out.println(objetivo.getNombre() + " bajó su velocidad!");
    }
}