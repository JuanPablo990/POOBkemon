package domain;
/**
 * Movimiento Fantasma: Shadow Ball
 * Efecto secundario: 20% de reducir Defensa Especial del objetivo
 */
public class ShadowBall extends Movimiento {
    public ShadowBall() {
        super("Shadow Ball", "Fantasma", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 20% de probabilidad de reducir Defensa Especial
        if (Math.random() < 0.20) {
            objetivo.disminuirDefensaEspecial(1);
            System.out.println("¡La Defensa Especial de " + objetivo.getNombre() + " bajó!");
        }
    }
}