package domain;

public class ChargeBeam extends Movimiento {
    public ChargeBeam() {
        super("Charge Beam", "Eléctrico", 50, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.70) {
            usuario.aumentarAtaqueEspecial(1);
            System.out.println(usuario.getNombre() + " aumentó su Ataque Especial.");
        }
    }
}
