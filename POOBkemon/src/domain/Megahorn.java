package domain;
/**
 * Movimiento Megahorn - Tipo Bicho
 */
public class Megahorn extends Movimiento {
    public Megahorn() {
        super("Megahorn", "Bicho", 120, 85, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario
    }
}