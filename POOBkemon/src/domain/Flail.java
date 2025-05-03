package domain;

public class Flail extends Movimiento {
    public Flail() {
        super("Flail", "Normal", 55, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Más potencia cuanto menos PS tenga
    }
}
