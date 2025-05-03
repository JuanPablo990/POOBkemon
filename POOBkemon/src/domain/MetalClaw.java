package domain;

public class MetalClaw extends Movimiento {
    public MetalClaw() {
        super("MetalClaw", "Acero", 50, 95, 35, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: 10% de aumentar el Ataque del usuario
    }
}
