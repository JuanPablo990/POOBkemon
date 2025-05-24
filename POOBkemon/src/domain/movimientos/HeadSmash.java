package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class HeadSmash extends Movimiento {
    public HeadSmash() {
        super("Head Smash", "Roca", 150, 80, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        int retroceso = calcularDanio(usuario, objetivo, 1.0) / 2;
        usuario.reducirPS(retroceso);
        System.out.println(usuario.getNombre() + " sufrió " + retroceso + " de daño por retroceso.");
    }
}
