package domain;

public class ZenHeadbutt extends Movimiento {
    public ZenHeadbutt() {
        super("ZenHeadbutt", "Psíquico", 80, 90, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: 20% de aturdir al rival
    }
}
