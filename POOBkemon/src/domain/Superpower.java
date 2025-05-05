package domain;

public class Superpower extends Movimiento {
    public Superpower() {
        super("Superpower", "Lucha", 120, 100, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.disminuirAtaque(1);
        usuario.disminuirDefensa(1);
        System.out.println(usuario.getNombre() + " redujo su Ataque y Defensa.");
    }
}
