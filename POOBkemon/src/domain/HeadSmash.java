package domain;

public class HeadSmash extends Movimiento {
    public HeadSmash() {
        super("HeadSmash", "Roca", 150, 80, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: El usuario recibe daño de retroceso
    }
}
