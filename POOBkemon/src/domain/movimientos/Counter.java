package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Lucha: Counter
 * Efecto secundario: Devuelve 2x daño físico recibido
 */
public class Counter extends Movimiento {
    public Counter() {
        super("Counter", "Lucha", 0, 100, 20, -5); // Prioridad -5
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento requiere lógica especial para contrarrestar el daño físico recibido
        System.out.println("¡" + usuario.getNombre() + " se prepara para contraatacar!");
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Este movimiento normalmente no se usa directamente, sino en respuesta a un ataque físico
        System.out.println("¡Counter debe usarse como respuesta a un ataque físico!");
        return false;
    }
}