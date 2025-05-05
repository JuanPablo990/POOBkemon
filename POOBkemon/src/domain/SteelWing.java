package domain;
/**
 * Movimiento Acero: Steel Wing
 * 10% de aumentar Defensa
 */
public class SteelWing extends Movimiento {
    public SteelWing() {
        super("Steel Wing", "Acero", 70, 90, 25, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() < 0.10) {
            usuario.aumentarDefensa(1);
            System.out.println("¡" + usuario.getNombre() + " aumentó su Defensa!");
        }
    }
}