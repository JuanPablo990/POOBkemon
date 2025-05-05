package domain;

/**
 * Movimiento Psíquico: Amnesia
 * Aumenta Defensa Especial en 2 niveles
 */
public class Amnesia extends Movimiento {
    public Amnesia() {
        super("Amnesia", "Psíquico", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarDefensaEspecial(2);
        System.out.println("¡" + usuario.getNombre() + " aumentó mucho su Defensa Especial!");
    }
}