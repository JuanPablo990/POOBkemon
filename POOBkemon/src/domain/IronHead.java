package domain;

public class IronHead extends Movimiento {
    public IronHead() {
        super("Iron Head", "Acero", 80, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.3) {
            System.out.println(objetivo.getNombre() + " retrocedió por el impacto.");
            // No hay un sistema de estados de retroceso implementado, así que solo imprimimos.
        }
    }
}
