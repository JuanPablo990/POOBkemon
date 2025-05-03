package domain;

public class ShadowClaw extends Movimiento {
    public ShadowClaw() {
        super("ShadowClaw", "Fantasma", 70, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Mayor probabilidad de golpe crítico
    }
}
