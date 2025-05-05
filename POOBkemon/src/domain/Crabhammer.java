package domain;

public class Crabhammer extends Movimiento {
    public Crabhammer() {
        super("Crabhammer", "Agua", 100, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Tiene alta probabilidad de golpe crítico!");
        // No hay sistema de críticos implementado, es solo informativo
    }
}
