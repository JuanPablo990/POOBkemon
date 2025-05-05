package domain;

public class MirrorShot extends Movimiento {
    public MirrorShot() {
        super("Mirror Shot", "Acero", 65, 85, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.30) {
            System.out.println(objetivo.getNombre() + " redujo su Precisión.");
        }
    }
}
