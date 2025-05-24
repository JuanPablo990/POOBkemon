package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento que disminuye la defensa especial del Pokémon objetivo.
 */
public class BajarDefensaEspecial extends Movimiento {
    
    public BajarDefensaEspecial() {
        super("Bajar DefensaEspecial", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirDefensaEspecial(1);
        System.out.println(objetivo.getNombre() + " ha disminuido su defensa especial!");
    }
}