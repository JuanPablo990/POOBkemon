package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que disminuye el ataque especial del Pokémon objetivo.
 */
public class BajarAtaqueEspecial extends Movimiento {
    
    public BajarAtaqueEspecial() {
        super("Bajar AtaqueEspecial", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirAtaqueEspecial(1);
        System.out.println(objetivo.getNombre() + " ha disminuido su ataque especial!");
    }
}