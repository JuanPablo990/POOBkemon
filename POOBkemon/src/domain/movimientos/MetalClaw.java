package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

public class MetalClaw extends Movimiento {
    public MetalClaw() {
        super("Metal Claw", "Acero", 50, 95, 35, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            usuario.aumentarAtaque(1);
            System.out.println(usuario.getNombre() + " aumentó su Ataque.");
        }
    }
}
