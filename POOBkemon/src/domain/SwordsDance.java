package domain;

public class SwordsDance extends Movimiento {
    public SwordsDance() {
        super("Swords Dance", "Normal", 0, 100, 20, 0); // Movimiento de estado
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaque(2);
        System.out.println(usuario.getNombre() + " aumentó mucho su Ataque.");
    }
}
