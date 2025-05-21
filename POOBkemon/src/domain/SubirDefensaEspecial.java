package domain;

/**
 * Movimiento que aumenta la defensa especial del Pokémon usuario.
 */
public class SubirDefensaEspecial extends Movimiento {
    
    public SubirDefensaEspecial() {
        super("Subir DefensaEspecial", "Normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        usuario.aumentarDefensaEspecial(1);
        System.out.println(usuario.getNombre() + " ha aumentado su defensa especial!");
    }
}