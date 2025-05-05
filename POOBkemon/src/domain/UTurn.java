package domain;

public class UTurn extends Movimiento {
    public UTurn() {
        super("U-turn", "Bicho", 70, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(usuario.getNombre() + " regresó después del ataque. ¡Es momento de cambiar!");
    }
}
