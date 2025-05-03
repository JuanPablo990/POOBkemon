package domain;

public class Crabhammer extends Movimiento {
    public Crabhammer() {
        super("Crabhammer", "Agua", 100, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Mayor probabilidad de golpe crítico
    }
}
