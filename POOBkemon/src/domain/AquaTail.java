package domain;

public class AquaTail extends Movimiento {
    public AquaTail() {
        super("AquaTail", "Agua", 90, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: -
    }
}
