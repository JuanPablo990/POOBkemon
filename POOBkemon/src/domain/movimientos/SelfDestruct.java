package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Normal: Self-Destruct
 * El usuario se debilita
 */
public class SelfDestruct extends Movimiento {
    public SelfDestruct() {
        super("Self-Destruct", "Normal", 200, 100, 5, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        boolean exito = super.ejecutar(usuario, objetivo, efectividad);
        if (exito) {
            usuario.reducirPS(usuario.getPs()); // Debilita al usuario
            System.out.println("¡" + usuario.getNombre() + " se autodestruyó!");
        }
        return exito;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto principal ya se maneja en ejecutar()
    }
}