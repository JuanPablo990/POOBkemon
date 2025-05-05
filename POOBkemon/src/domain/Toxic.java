package domain;

/**
 * Movimiento Toxic: Envenena gravemente al rival.
 */
public class Toxic extends Movimiento {
    public Toxic() {
        super("Toxic", "Veneno", 0, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Envenenar gravemente al objetivo
        System.out.println(objetivo.getNombre() + " ha sido envenenado gravemente!");
        // En una implementación completa, aquí se aplicaría el estado de envenenamiento grave
    }
}
