package domain;

public class ZenHeadbutt extends Movimiento {
    public ZenHeadbutt() {
        super("Zen Headbutt", "Psíquico", 80, 90, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        if (Math.random() <= 0.20) {
            System.out.println(objetivo.getNombre() + " quedó aturdido por el impacto.");
        }
    }
}
