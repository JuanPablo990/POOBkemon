package domain;
/**
 * Movimiento Roca: Rock Slide
 * 30% de hacer retroceder al rival
 */
public class RockSlide extends Movimiento {
    public RockSlide() {
        super("Rock Slide", "Roca", 75, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 30% de probabilidad de hacer retroceder (flinch)
        if (Math.random() < 0.30) {
            System.out.println("¡" + objetivo.getNombre() + " retrocedió!");
            // Aquí deberías implementar la lógica para el estado "flinch"
        }
    }
}