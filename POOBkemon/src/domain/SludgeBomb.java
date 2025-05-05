package domain;
/**
 * Movimiento Veneno: Sludge Bomb
 * 30% de envenenar al rival
 */
public class SludgeBomb extends Movimiento {
    public SludgeBomb() {
        super("Sludge Bomb", "Veneno", 90, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.30) {
            System.out.println("¡" + objetivo.getNombre() + " fue envenenado!");
            // Implementar lógica de envenenamiento
        }
    }
}