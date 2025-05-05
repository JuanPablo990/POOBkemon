package domain;
/**
 * Movimiento Psíquico: Psycho Cut
 * Mayor probabilidad de golpe crítico
 */
public class PsychoCut extends Movimiento {
    public PsychoCut() {
        super("Psycho Cut", "Psíquico", 70, 100, 20, 0);
    }

    @Override
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Aumentar probabilidad de golpe crítico
        if (Math.random() < 0.25) { // 25% de probabilidad
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