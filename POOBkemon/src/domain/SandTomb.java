package domain;
/**
 * Movimiento Tierra: Sand Tomb
 * Atrapa al rival (4-5 turnos)
 */
public class SandTomb extends Movimiento {
    public SandTomb() {
        super("Sand Tomb", "Tierra", 35, 85, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡" + objetivo.getNombre() + " quedó atrapado en un torbellino de arena!");
        // Aquí deberías implementar la lógica para el atrapamiento (4-5 turnos)
    }
}