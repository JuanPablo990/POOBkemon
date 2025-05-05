package domain;

public class RapidSpin extends Movimiento {
    public RapidSpin() {
        super("Rapid Spin", "Normal", 50, 100, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(usuario.getNombre() + " eliminó los efectos de atrapamiento.");
    }
}

