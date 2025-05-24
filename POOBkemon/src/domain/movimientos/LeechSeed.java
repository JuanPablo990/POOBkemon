package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Leech Seed: Drena vida del objetivo cada turno.
 */
public class LeechSeed extends Movimiento {
    public LeechSeed() {
        super("Leech Seed", "Planta", 0, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Aplicar efecto de drenaje de vida (simplificado)
        int danio = objetivo.getPsMaximos() / 8; // 1/8 de la vida máxima
        objetivo.reducirPS(danio);
        usuario.aumentarPS(danio);
        System.out.println(objetivo.getNombre() + " pierde vida por las semillas!");
        System.out.println(usuario.getNombre() + " recupera " + danio + " PS!");
    }
}
