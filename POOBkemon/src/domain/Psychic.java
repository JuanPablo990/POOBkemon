package domain;
/**
 * Movimiento Psíquico: Psychic
 * Efecto secundario: 10% de reducir Defensa Especial del objetivo
 */
public class Psychic extends Movimiento {
    public Psychic() {
        super("Psychic", "Psíquico", 90, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 10% de probabilidad de reducir Defensa Especial
        if (Math.random() < 0.10) {
            objetivo.disminuirDefensaEspecial(1);
            System.out.println("¡La Defensa Especial de " + objetivo.getNombre() + " bajó!");
        }
    }
}