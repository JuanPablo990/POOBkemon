package domain; 

/**
 * Movimiento Quick Attack: Ataque con prioridad alta.
 */
public class QuickAttack extends Movimiento {
    public QuickAttack() {
        super("Quick Attack", "Normal", 40, 100, 30, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento no tiene efecto secundario
    }
}
