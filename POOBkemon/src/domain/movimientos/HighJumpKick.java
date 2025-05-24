package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento High Jump Kick - Tipo Lucha
 */
public class HighJumpKick extends Movimiento {
    public HighJumpKick() {
        super("High Jump Kick", "Lucha", 130, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto de fallar se maneja en ejecutar
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        
        this.pp--;
        
        if (Math.random() * 100 > this.precision) {
            System.out.println("¡El ataque falló!");
            int danio = usuario.getPsMaximos() / 2;
            usuario.reducirPS(danio);
            System.out.println("¡" + usuario.getNombre() + " se lastimó al fallar y perdió " + danio + " PS!");
            return false;
        }
        
        int danio = calcularDanio(usuario, objetivo, efectividad);
        objetivo.reducirPS(danio);
        System.out.println("¡El ataque hizo " + danio + " de daño!");
        return true;
    }
}