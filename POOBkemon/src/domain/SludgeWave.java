package domain;
/**
 * Movimiento Veneno: Sludge Wave
 * 10% de envenenar al rival
 */
public class SludgeWave extends Movimiento {
    public SludgeWave() {
        super("Sludge Wave", "Veneno", 95, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.10) {
            System.out.println("¡" + objetivo.getNombre() + " fue envenenado!");
            // Implementar lógica de envenenamiento
        }
    }
}