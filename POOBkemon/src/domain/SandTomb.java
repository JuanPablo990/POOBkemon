package domain;

public class SandTomb extends Movimiento {
    public SandTomb() {
        super("Sand Tomb", "Tierra", 35, 85, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println(objetivo.getNombre() + " quedó atrapado por 4-5 turnos.");
    }
}

