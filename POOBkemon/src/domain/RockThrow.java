package domain;
/**
 * Movimiento Roca: Rock Throw
 * Sin efecto secundario
 */
public class RockThrow extends Movimiento {
    public RockThrow() {
        super("Rock Throw", "Roca", 50, 90, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto secundario
    }
}