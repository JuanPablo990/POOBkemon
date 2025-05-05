package domain;

public class QuickAttack extends Movimiento {
    public QuickAttack() {
        super("Quick Attack", "Normal", 40, 100, 30, 1);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
