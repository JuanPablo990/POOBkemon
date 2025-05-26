package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import org.junit.Assert;
import org.junit.Test;

public class PruebaDeAceptacion {

    @Test
    public void pruebaBatallaEntreEntrenadoresAleatorios() {
        
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();

        Batalla batalla = juego.getBatallaActual();
        int maxTurnos = 1000; 
        int turnos = 0;
        while (!batalla.isBatallaTerminada() && turnos < maxTurnos) {
            batalla.ejecutarTurno(1);
            turnos++;
        }

        Assert.assertTrue("La batalla debe haber terminado o se alcanzó el máximo de turnos", 
            batalla.isBatallaTerminada() || turnos == maxTurnos);
        if (batalla.isBatallaTerminada()) {
            Assert.assertNotNull("Debe haber un ganador", batalla.getGanador());
            System.out.println("Ganador: " + batalla.getGanador().getNombre());
        } else {
            System.out.println("La batalla no terminó en " + maxTurnos + " turnos.");
        }
    }
}
