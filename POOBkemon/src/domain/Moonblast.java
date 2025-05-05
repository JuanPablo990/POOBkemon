package domain;

public class Moonblast extends Movimiento {
    public Moonblast() {
        super("Moonblast", "Hada", 95, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.30) {
            objetivo.disminuirAtaqueEspecial(1);
            System.out.println(objetivo.getNombre() + " redujo su Ataque Especial.");
        }
    }
}
