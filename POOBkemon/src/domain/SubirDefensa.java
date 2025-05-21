package domain;

/**
 * Movimiento que aumenta la defensa del usuario
 */
public class SubirDefensa extends Movimiento {
    public SubirDefensa() {
    	super("Subir Defensa", "normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarDefensa(1); // Usa el método existente
        System.out.println(usuario.getNombre() + " aumentó su defensa!");
    }
}