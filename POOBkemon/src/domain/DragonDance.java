package domain;

public class DragonDance extends Movimiento {
    public DragonDance() {
        super("Dragon Dance", "Dragón", 0, 100, 20, 0); // Potencia 0 = movimiento de estado
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaque(1);
        usuario.aumentarVelocidad(1);
        System.out.println(usuario.getNombre() + " aumentó su Ataque y Velocidad.");
    }
}
