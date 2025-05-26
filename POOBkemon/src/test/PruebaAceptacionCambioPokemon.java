package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import domain.Pokemon;
import org.junit.Assert;
import org.junit.Test;

public class PruebaAceptacionCambioPokemon {

    @Test
    public void pruebaEntrenadorCambiaPokemonDuranteBatalla() {
        // Dado: Dos entrenadores con equipos aleatorios
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();

        // Ash intenta cambiar de Pokémon si tiene más de uno disponible
        if (ash.getEquipoPokemon().size() > 1) {
            Pokemon original = ash.getPokemonActivo();
            // Buscar otro Pokémon disponible para cambiar
            Pokemon paraCambiar = null;
            for (Pokemon p : ash.getEquipoPokemon()) {
                if (!p.equals(original) && !p.estaDebilitado()) {
                    paraCambiar = p;
                    break;
                }
            }
            if (paraCambiar != null) {
                ash.setPokemonActivo(paraCambiar);
                Assert.assertNotEquals("Ash debería haber cambiado de Pokémon. Actual: " + ash.getEquipoPokemon().indexOf(ash.getPokemonActivo()),
                        original, ash.getPokemonActivo());
            } else {
                System.out.println("No hay otro Pokémon disponible para cambiar.");
            }
        } else {
            System.out.println("Ash solo tiene un Pokémon, se omite la prueba de cambio.");
        }
    }
}
