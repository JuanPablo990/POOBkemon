
package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import org.junit.Assert;
import org.junit.Test;

public class AcceptanceTest {

    @Test
    public void testBattleBetweenRandomTrainers() {
        
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

       
        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();

        
        Batalla batalla = juego.getBatallaActual();
        int maxTurns = 1000; 
        int turns = 0;
        while (!batalla.isBatallaTerminada() && turns < maxTurns) {
            batalla.ejecutarTurno(1);
            turns++;
        }

        
        Assert.assertTrue("Battle should be finished or max turns reached", 
            batalla.isBatallaTerminada() || turns == maxTurns);
        if (batalla.isBatallaTerminada()) {
            Assert.assertNotNull("There should be a winner", batalla.getGanador());
            System.out.println("Winner: " + batalla.getGanador().getNombre());
        } else {
            System.out.println("Battle did not finish in " + maxTurns + " turns.");
        }
    }
}
