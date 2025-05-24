package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Synthesis: Restaura PS según el clima (simplificado aquí).
 */
public class Synthesis extends Movimiento {
    public Synthesis() {
        super("Synthesis", "Planta", 0, 0, 5, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        this.pp--;
        
        // Simplificado: siempre cura 50% en esta implementación
        int curacion = usuario.getPsMaximos() / 2;
        usuario.aumentarPS(curacion);
        System.out.println(usuario.getNombre() + " recuperó " + curacion + " PS!");
        
        return true;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto principal ya se maneja en ejecutar()
    }
}
