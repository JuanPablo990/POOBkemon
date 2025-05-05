package domain;
 

/**
 * Movimiento Double-Edge: Potente ataque normal que causa retroceso al usuario.
 */
public class DoubleEdge extends Movimiento {
    public DoubleEdge() {
        super("Double-Edge", "Normal", 120, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Calcula el daño causado (25% del daño infligido)
        int danio = (int) (calcularDanio(usuario, objetivo, 1.0) * 0.25);
        usuario.reducirPS(danio);
        System.out.println(usuario.getNombre() + " sufre " + danio + " de daño por retroceso!");
    }
}
