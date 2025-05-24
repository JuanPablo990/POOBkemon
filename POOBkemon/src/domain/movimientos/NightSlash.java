package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Siniestro: Night Slash
 * Mayor probabilidad de golpe crítico
 */
public class NightSlash extends Movimiento {
    public NightSlash() {
        super("Night Slash", "Siniestro", 70, 100, 15, 0);
    }

    @Override
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Aumentar probabilidad de golpe crítico (ratio crítico normalmente es 4.17%, aquí lo aumentamos)
        if (Math.random() < 0.25) { // 25% de probabilidad en lugar del normal
            System.out.println("¡Un golpe crítico!");
            return (int)(super.calcularDanio(usuario, objetivo, efectividad) * 1.5);
        }
        return super.calcularDanio(usuario, objetivo, efectividad);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto ya se maneja en calcularDanio()
    }
}