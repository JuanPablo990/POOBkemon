package domain;

public class ShellSmash extends Movimiento {
    public ShellSmash() {
        super("Shell Smash", "Normal", 0, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaque(1);
        usuario.aumentarAtaqueEspecial(1);
        usuario.aumentarVelocidad(1);
        usuario.disminuirDefensa(1);
        usuario.disminuirDefensaEspecial(1);
        System.out.println(usuario.getNombre() + " usó Shell Smash: subió Ataque, At. Esp. y Velocidad, pero bajó Defensas.");
    }
}
