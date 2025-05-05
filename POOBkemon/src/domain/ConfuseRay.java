package domain;
/**
 * Movimiento Fantasma: Confuse Ray
 * Confunde al rival
 */
public class ConfuseRay extends Movimiento {
    public ConfuseRay() {
        super("Confuse Ray", "Fantasma", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡" + objetivo.getNombre() + " se confundió!");
        // Implementar lógica para confusión
    }
}