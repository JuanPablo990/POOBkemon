package domain; 

/**
 * Movimiento Swords Dance: Aumenta el ataque del usuario en 2 niveles.
 */
public class SwordsDance extends Movimiento {
    public SwordsDance() {
        super("Swords Dance", "Normal", 0, 0, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarAtaque(2);
        System.out.println("El ataque de " + usuario.getNombre() + " subió drásticamente!");
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        this.pp--;
        aplicarEfectoSecundario(usuario, objetivo);
        return true;
    }
}
