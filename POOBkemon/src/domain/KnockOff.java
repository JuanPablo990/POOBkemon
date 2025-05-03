package domain;

public class KnockOff extends Movimiento {
    public KnockOff() {
        super("KnockOff", "Siniestro", 65, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: Elimina el objeto del rival
    }
}
