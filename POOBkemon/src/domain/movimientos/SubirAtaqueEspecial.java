package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que aumenta el ataque especial del Pokémon usuario.
 */
public class SubirAtaqueEspecial extends Movimiento {
    
    public SubirAtaqueEspecial() {
        super("Subir AtaqueEspecial", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaqueEspecial(1);
        System.out.println(usuario.getNombre() + " ha aumentado su ataque especial!");
    }
}