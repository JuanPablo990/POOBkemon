package domain;

public class ExtremeSpeed extends Movimiento {
    public ExtremeSpeed() {
        super("ExtremeSpeed", "Normal", 80, 100, 5, 2);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: -
    }
}
