package domain;

public class MeteorMash extends Movimiento {
    public MeteorMash() {
        super("Meteor Mash", "Acero", 90, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.20) {
            usuario.aumentarAtaque(1);
            System.out.println(usuario.getNombre() + " aumentó su Ataque.");
        }
    }
}
