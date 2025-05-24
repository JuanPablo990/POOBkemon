package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que aumenta la velocidad del usuario
 */
public class SubirVelocidad extends Movimiento {
    public SubirVelocidad() {
    	super("Subir Velocidad", "normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarVelocidad(1); // Usa el método existente
        System.out.println(usuario.getNombre() + " aumentó su velocidad!");
    }
}