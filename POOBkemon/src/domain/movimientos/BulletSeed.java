package domain.movimientos;

import domain.Movimiento;
import domain.Pokemon;

/**
 * Movimiento Bullet Seed - Tipo Planta
 */
public class BulletSeed extends Movimiento {
    public BulletSeed() {
        super("Bullet Seed", "Planta", 25, 100, 30, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Golpea de 2 a 5 veces (se implementa sobrescribiendo ejecutar)
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        
        this.pp--;
        int golpes = 2 + (int)(Math.random() * 4); // 2-5 golpes
        
        if (Math.random() * 100 > this.precision) {
            System.out.println("¡El ataque falló!");
            return false;
        }
        
        int danioTotal = 0;
        for (int i = 0; i < golpes; i++) {
            int danio = calcularDanio(usuario, objetivo, efectividad);
            objetivo.reducirPS(danio);
            danioTotal += danio;
        }
        
        System.out.println("¡El ataque golpeó " + golpes + " veces por un total de " + danioTotal + " de daño!");
        return true;
    }
}