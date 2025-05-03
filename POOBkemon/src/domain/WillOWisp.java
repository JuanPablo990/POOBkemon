package domain;

public class WillOWisp extends Movimiento {
    public WillOWisp() {
        super("Will-O-Wisp", "Fuego", 0, 85, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Quema al rival
    }
}
