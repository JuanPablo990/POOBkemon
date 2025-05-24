package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Normal: Howl
 * Aumenta Ataque del equipo
 */
public class Howl extends Movimiento {
    public Howl() {
        super("Howl", "Normal", 0, 100, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // En una implementación real, esto afectaría a todo el equipo
        usuario.aumentarAtaque(1);
        System.out.println("¡" + usuario.getNombre() + " aumentó su Ataque!");
    }
}