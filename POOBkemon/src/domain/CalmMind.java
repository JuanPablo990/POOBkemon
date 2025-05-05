package domain;

public class CalmMind extends Movimiento {
    public CalmMind() {
        super("Calm Mind", "Psíquico", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaqueEspecial(1);
        usuario.aumentarDefensaEspecial(1);
        System.out.println(usuario.getNombre() + " aumentó su Ataque Especial y Defensa Especial.");
    }
}
