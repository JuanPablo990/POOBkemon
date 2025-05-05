package domain;

public class AirCutter extends Movimiento {
    public AirCutter() {
        super("Air Cutter", "Volador", 60, 95, 25, 0);
    }

    @Override
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (Math.random() < 0.125) { // 12.5% de probabilidad (normal es ~4.17%)
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