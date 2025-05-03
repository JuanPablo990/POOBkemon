package domain;

public class Splash extends Movimiento {
    public Splash() {
        super("Splash", "Normal", 0, 0, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Pero no ocurrió nada!");
    }
}