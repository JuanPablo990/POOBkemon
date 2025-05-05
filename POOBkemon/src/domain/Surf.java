package domain;

public class Surf extends Movimiento {
    public Surf() {
        super("Surf", "Agua", 90, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Surf golpea a todos los Pokémon en combate!");
    }
}
