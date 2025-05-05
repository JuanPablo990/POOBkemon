package domain;

public class Psychic extends Movimiento {
    public Psychic() {
        super("Psychic", "Psíquico", 90, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.10) {
            objetivo.disminuirDefensaEspecial(1);
            System.out.println(objetivo.getNombre() + " redujo su Defensa Especial.");
        }
    }
}
