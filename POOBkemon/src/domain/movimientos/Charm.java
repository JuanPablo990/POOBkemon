package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Charm: Reduce el ataque del rival en 2 niveles.
 */
public class Charm extends Movimiento {
    public Charm() {
        super("Charm", "Hada", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirAtaque(2);
        System.out.println("El ataque de " + objetivo.getNombre() + " bajó drásticamente!");
    }
}
