package domain;

public class XScissor extends Movimiento {
    public XScissor() {
        super("X-Scissor", "Bicho", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
