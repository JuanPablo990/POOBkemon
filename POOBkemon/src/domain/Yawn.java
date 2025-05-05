package domain;
/**
 * Movimiento Normal: Yawn
 * Duerme al rival en el siguiente turno
 */
public class Yawn extends Movimiento {
    public Yawn() {
        super("Yawn", "Normal", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡" + objetivo.getNombre() + " comenzó a sentir sueño!");
        // Aquí deberías implementar la lógica para aplicar sueño al siguiente turno
    }
}