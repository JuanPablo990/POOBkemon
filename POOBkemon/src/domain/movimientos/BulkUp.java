package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Bulk Up - Tipo Lucha (movimiento de estado)
 */
public class BulkUp extends Movimiento {
    public BulkUp() {
        super("Bulk Up", "Lucha", 0, 0, 20, 0); // Movimiento de estado
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaque(1);
        usuario.aumentarDefensa(1);
        System.out.println("¡El ataque y defensa de " + usuario.getNombre() + " aumentaron!");
    }
}