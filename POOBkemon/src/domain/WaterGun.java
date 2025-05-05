package domain;

public class WaterGun extends Movimiento {
    public WaterGun() {
        super("Water Gun", "Agua", 40, 100, 25, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto
    }
}
