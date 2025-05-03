package domain;

public class Tackle extends Movimiento {
    public Tackle() {
        super("Tackle", "Normal", 40, 100, 35, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: -
    }
}
