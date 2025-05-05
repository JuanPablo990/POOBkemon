package domain;

public class ShadowClaw extends Movimiento {
    public ShadowClaw() {
        super("Shadow Claw", "Fantasma", 70, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Tiene alta probabilidad de golpe crítico!");
    }
}
