package domain;
 

/**
 * Movimiento Dragon Dance: Aumenta el ataque y velocidad del usuario.
 */
public class DragonDance extends Movimiento {
    public DragonDance() {
        super("Dragon Dance", "Dragón", 0, 0, 20, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        this.pp--;
        
        usuario.aumentarAtaque(1);
        usuario.aumentarVelocidad(1);
        System.out.println(usuario.getNombre() + " aumentó su ataque y velocidad!");
        
        return true;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto principal ya se maneja en ejecutar()
    }
}
