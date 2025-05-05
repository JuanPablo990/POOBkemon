package domain;

public class Explosion extends Movimiento {
    public Explosion() {
        super("Explosion", "Normal", 250, 100, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.reducirPS(usuario.getPs());
        System.out.println(usuario.getNombre() + " se debilitó tras la explosión.");
    }
}
