package domain;

public class IronHead extends Movimiento {
    public IronHead() {
        super("IronHead", "Acero", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: 30% de hacer retroceder al oponente
    }
}
