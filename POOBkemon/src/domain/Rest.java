package domain;

public class Rest extends Movimiento {
    public Rest() {
        super("Rest", "Psíquico", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarPS(usuario.getPsMaximos());
        System.out.println(usuario.getNombre() + " se curó completamente, ¡pero se quedó dormido!");
    }
}

