package domain; 

/**
 * Movimiento Seed Bomb: Ataque de planta sin efecto secundario.
 */
public class SeedBomb extends Movimiento {
    public SeedBomb() {
        super("Seed Bomb", "Planta", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento no tiene efecto secundario
    }
}
