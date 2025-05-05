package domain;

public class ShadowBall extends Movimiento {
    public ShadowBall() {
        super("Shadow Ball", "Fantasma", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.20) {
            objetivo.disminuirDefensaEspecial(1);
            System.out.println(objetivo.getNombre() + " redujo su Defensa Especial.");
        }
    }
}
