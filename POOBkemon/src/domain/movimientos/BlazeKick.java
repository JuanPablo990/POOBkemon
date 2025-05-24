package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Blaze Kick - Tipo Fuego
 */
public class BlazeKick extends Movimiento {
    public BlazeKick() {
        super("Blaze Kick", "Fuego", 85, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 10% de quemar
        if (Math.random() < 0.1) {
            // Aquí debería implementarse el estado quemado, pero como no hay clase Estado,
            // simplemente mostramos un mensaje
            System.out.println("¡" + objetivo.getNombre() + " se quemó!");
        }
        
        // Mayor probabilidad de golpe crítico está implícita en el cálculo de daño
    }
}