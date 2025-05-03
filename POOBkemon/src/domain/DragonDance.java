package domain;

public class DragonDance extends Movimiento {
    public DragonDance() {
        super("DragonDance", "Dragón", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Aumenta Ataque y Velocidad
    }
}
