package domain;

public class AquaJet extends Movimiento {
    public AquaJet() {
        super("AquaJet", "Agua", 40, 100, 20, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: -
    }
}
