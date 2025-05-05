package domain;

public class ConfuseRay extends Movimiento {
    public ConfuseRay() {
        super("Confuse Ray", "Fantasma", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(objetivo.getNombre() + " quedó confundido.");
    }
}
