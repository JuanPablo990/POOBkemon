package domain;

public class HydroPump extends Movimiento {
    public HydroPump() {
        super("HydroPump", "Agua", 110, 80, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: -
    }
}
