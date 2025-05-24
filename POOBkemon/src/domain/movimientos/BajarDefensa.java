package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que reduce la defensa del oponente
 */
public class BajarDefensa extends Movimiento {
    public BajarDefensa() {
    	super("Bajar Defensa", "normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirDefensa(1); // Usa el método existente
        System.out.println(objetivo.getNombre() + " bajó su defensa!");
    }
}
