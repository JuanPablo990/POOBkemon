package domain;

public class Spikes extends Movimiento {
    public Spikes() {
        super("Spikes", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Púas fueron colocadas en el campo del rival!");
    }
}
