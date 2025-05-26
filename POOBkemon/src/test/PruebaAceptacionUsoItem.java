package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import org.junit.Assert;
import org.junit.Test;

public class PruebaAceptacionUsoItem {

    @Test
    public void probarEntrenadorUsaItemDuranteBatalla() {
       
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();
        Batalla batalla = juego.getBatallaActual();

        if (!ash.getMochilaItems().isEmpty()) {
            int psInicial = ash.getPokemonActivo().getPs();
            batalla.ejecutarTurno(2); // Opción 2: Usar ítem
            int psDespues = ash.getPokemonActivo().getPs();
            
            Assert.assertTrue("El Pokémon debe tener la misma o mayor cantidad de PS después de usar el ítem", psDespues >= psInicial);
        } else {
            System.out.println("Ash no tiene ítems para usar, se omite la prueba de uso de ítem.");
        }
    }
}
