package domain;

public class Splash extends Movimiento {
    public Splash() {
        super("Splash", "Normal", 0, 100, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: No tiene efecto
    }
}
