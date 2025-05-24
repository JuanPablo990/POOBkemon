package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class PainSplit extends Movimiento {
    public PainSplit() {
        super("Pain Split", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡La vida se igualó entre " + usuario.getNombre() + " y " + objetivo.getNombre() + "!");
    }
}
