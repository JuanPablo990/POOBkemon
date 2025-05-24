package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Planta: Giga Drain
 * Drena 50% del daño como vida
 */
public class GigaDrain extends Movimiento {
    public GigaDrain() {
        super("Giga Drain", "Planta", 75, 100, 10, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        boolean exito = super.ejecutar(usuario, objetivo, efectividad);
        if (exito && this.potencia > 0) {
            int danio = calcularDanio(usuario, objetivo, efectividad);
            int curacion = danio / 2; // 50% del daño
            usuario.aumentarPS(curacion);
            System.out.println("¡" + usuario.getNombre() + " absorbió energía!");
        }
        return exito;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto principal ya se maneja en ejecutar()
    }
}