package domain;

public class UTurn extends Movimiento {
    public UTurn() {
        super("U-turn", "Bicho", 70, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Cambia al usuario después del ataque
    }
}
