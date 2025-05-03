package domain;

public class DragonBreath extends Movimiento {
    public DragonBreath() {
        super("DragonBreath", "Dragón", 60, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: 30% de paralizar al rival
    }
}
