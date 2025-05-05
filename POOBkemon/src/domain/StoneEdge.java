package domain;
/**
 * Movimiento Stone Edge - Tipo Roca
 */
public class StoneEdge extends Movimiento {
    public StoneEdge() {
        super("Stone Edge", "Roca", 100, 80, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Mayor probabilidad de golpe crítico está implícita en el cálculo de daño
    }
}