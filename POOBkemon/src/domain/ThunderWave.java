package domain;

public class ThunderWave extends Movimiento {
    public ThunderWave() {
        super("ThunderWave", "Eléctrico", 0, 90, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Paraliza al rival
    }
}
