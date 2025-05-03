package domain;

public class ConfuseRay extends Movimiento {
    public ConfuseRay() {
        super("ConfuseRay", "Fantasma", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Confunde al rival
    }
}
