package domain;
 

/**
 * Movimiento Crush Claw: Ataque que puede reducir la defensa del rival.
 */
public class CrushClaw extends Movimiento {
    public CrushClaw() {
        super("Crush Claw", "Normal", 75, 95, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 50% de probabilidad de reducir defensa
        if (Math.random() < 0.5) {
            objetivo.disminuirDefensa(1);
            System.out.println("La defensa de " + objetivo.getNombre() + " bajó!");
        }
    }
}
