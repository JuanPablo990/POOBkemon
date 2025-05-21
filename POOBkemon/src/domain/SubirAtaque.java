package domain;

/**
 * Movimiento que aumenta el ataque del usuario
 */
public class SubirAtaque extends Movimiento {
    public SubirAtaque() {
    	        super("Subir Ataque", "normal", 0, 100, 20, 0);
    	    }

    	    @Override
    	    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
    	        usuario.aumentarAtaque(1);
    	        System.out.println(usuario.getNombre() + " aumentó su ataque!");
    }
}