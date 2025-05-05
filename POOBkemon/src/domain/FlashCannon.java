package domain;

public class FlashCannon extends Movimiento {
    public FlashCannon() {
        super("Flash Cannon", "Acero", 80, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            objetivo.disminuirDefensaEspecial(1);
            System.out.println(objetivo.getNombre() + " redujo su Defensa Especial.");
        }
    }
}
