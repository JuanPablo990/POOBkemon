package domain;
/**
 * Movimiento Veneno: Toxic Spikes
 * Envenena al rival al entrar
 */
public class ToxicSpikes extends Movimiento {
    public ToxicSpikes() {
        super("Toxic Spikes", "Veneno", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Púas venenosas fueron esparcidas en el campo rival!");
        // Implementar lógica para colocar spikes en el campo
    }
}