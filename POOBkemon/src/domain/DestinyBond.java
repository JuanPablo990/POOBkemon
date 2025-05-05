package domain;

public class DestinyBond extends Movimiento {
    public DestinyBond() {
        super("Destiny Bond", "Fantasma", 0, 100, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(usuario.getNombre() + " se llevará al rival si es derrotado.");
    }
}
