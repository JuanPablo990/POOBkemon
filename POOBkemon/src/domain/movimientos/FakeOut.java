package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Fake Out: Ataque con alta prioridad que solo funciona en el primer turno.
 */
public class FakeOut extends Movimiento {
    public FakeOut() {
        super("Fake Out", "Normal", 40, 100, 10, 3);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Verificar si es el primer turno (esto necesitaría contexto de batalla)
        boolean esPrimerTurno = true; // Esto debería ser determinado por la lógica de batalla
        
        if (!esPrimerTurno) {
            System.out.println("¡Fake Out solo funciona en el primer turno!");
            return false;
        }
        
        return super.ejecutar(usuario, objetivo, efectividad);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Hace retroceder (en el juego real esto haría que el rival retroceda)
        System.out.println(objetivo.getNombre() + " retrocede por el impacto!");
    }
}
