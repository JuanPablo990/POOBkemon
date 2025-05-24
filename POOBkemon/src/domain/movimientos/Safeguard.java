package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Normal: Safeguard
 * Efecto secundario: Previene problemas de estado
 */
public class Safeguard extends Movimiento {
    public Safeguard() {
        super("Safeguard", "Normal", 0, 100, 25, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Activa la protección Safeguard
        System.out.println("¡" + usuario.getNombre() + " se protegió con un manto sagrado!");
        // Aquí deberías implementar la lógica para prevenir problemas de estado
    }
}