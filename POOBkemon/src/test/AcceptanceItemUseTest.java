
package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import org.junit.Assert;
import org.junit.Test;

public class AcceptanceItemUseTest {

    @Test
    public void testTrainerUsesItemDuringBattle() {
       
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

        
        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();
        Batalla batalla = juego.getBatallaActual();

        
        if (!ash.getMochilaItems().isEmpty()) {
            int initialPs = ash.getPokemonActivo().getPs();
            batalla.ejecutarTurno(2); // Option 2: Use item
            int afterPs = ash.getPokemonActivo().getPs();
            
            Assert.assertTrue("Pokémon should have same or more HP after using item", afterPs >= initialPs);
        } else {
            System.out.println("Ash has no items to use, skipping item use test.");
        }
    }
}
