package domain;

public class BodySlam extends Movimiento {
    public BodySlam() {
        super("Body Slam", "Normal", 85, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.30) {
            System.out.println(objetivo.getNombre() + " quedó paralizado.");
        }
    }
}
