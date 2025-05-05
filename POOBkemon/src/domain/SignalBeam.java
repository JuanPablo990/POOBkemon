package domain;

public class SignalBeam extends Movimiento {
    public SignalBeam() {
        super("Signal Beam", "Bicho", 75, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            System.out.println(objetivo.getNombre() + " quedó confundido.");
        }
    }
}
