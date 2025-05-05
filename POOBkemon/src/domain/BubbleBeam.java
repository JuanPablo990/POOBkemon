package domain;

public class BubbleBeam extends Movimiento {
    public BubbleBeam() {
        super("Bubble Beam", "Agua", 65, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            objetivo.disminuirVelocidad(1);
            System.out.println(objetivo.getNombre() + " redujo su Velocidad.");
        }
    }
}
