package domain;
/**
 * Movimiento Normal: Rapid Spin
 * Elimina efectos de atrapamiento
 */
public class RapidSpin extends Movimiento {
    public RapidSpin() {
        super("Rapid Spin", "Normal", 50, 100, 40, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        System.out.println("¡" + usuario.getNombre() + " se liberó de los efectos de atrapamiento!");
        // Aquí deberías implementar la lógica para eliminar atrapamientos
    }
}