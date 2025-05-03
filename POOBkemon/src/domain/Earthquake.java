package domain;

public class Earthquake extends Movimiento {
    public Earthquake() {
        super("Earthquake", "Tierra", 100, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Afecta a todos los Pokémon en el campo
    }
}
