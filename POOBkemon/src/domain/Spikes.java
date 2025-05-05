package domain;
/**
 * Movimiento Normal: Spikes
 * Coloca púas en el campo rival
 */
public class Spikes extends Movimiento {
    public Spikes() {
        super("Spikes", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Púas fueron esparcidas en el campo rival!");
        // Implementar lógica para colocar spikes en el campo
    }
}