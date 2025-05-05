package domain;
/**
 * Movimiento Agua: Surf
 * Afecta a todos en combate
 */
public class Surf extends Movimiento {
    public Surf() {
        super("Surf", "Agua", 90, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡Un enorme tsunami golpea a todos!");
        // Implementar lógica para afectar múltiples objetivos
    }
}