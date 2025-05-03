package domain;

public class SwordsDance extends Movimiento {
    public SwordsDance() {
        super("SwordsDance", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Aumenta el Ataque en 2 niveles
    }
}
