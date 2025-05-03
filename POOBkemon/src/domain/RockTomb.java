package domain;

public class RockTomb extends Movimiento {
    public RockTomb() {
        super("RockTomb", "Roca", 60, 95, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Reduce la Velocidad del rival
    }
}
