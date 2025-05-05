package domain;

/**
 * Movimiento Close Combat - Tipo Lucha
 */
public class CloseCombat extends Movimiento {
    public CloseCombat() {
        super("Close Combat", "Lucha", 120, 100, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.disminuirDefensa(1);
        usuario.disminuirDefensaEspecial(1);
        System.out.println("¡La defensa y defensa especial de " + usuario.getNombre() + " bajaron!");
    }
}