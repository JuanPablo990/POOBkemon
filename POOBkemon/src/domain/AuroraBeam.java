package domain;

public class AuroraBeam extends Movimiento {
    public AuroraBeam() {
        super("Aurora Beam", "Hielo", 65, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            objetivo.disminuirAtaque(1);
            System.out.println(objetivo.getNombre() + " redujo su Ataque.");
        }
    }
}
