package domain;
/**
 * Movimiento Hada: Play Rough
 * 10% de reducir Ataque del rival
 */
public class PlayRough extends Movimiento {
    public PlayRough() {
        super("Play Rough", "Hada", 90, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.10) {
            objetivo.disminuirAtaque(1);
            System.out.println("¡El Ataque de " + objetivo.getNombre() + " bajó!");
        }
    }
}
