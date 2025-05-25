
package test;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Batalla;
import domain.Pokemon;
import org.junit.Assert;
import org.junit.Test;

public class AcceptanceSwitchPokemonTest {

    @Test
    public void testTrainerSwitchesPokemonDuringBattle() {
        // Given: Two trainers with random teams
        POOBkemon juego = new POOBkemon();
        Entrenador ash = juego.crearEntrenadorConEquipoAleatorio("Ash");
        Entrenador gary = juego.crearEntrenadorConEquipoAleatorio("Gary");

        juego.setJugadores(ash, gary);
        juego.iniciarBatalla();

        // Ash tries to switch Pokémon if he has more than one available
        if (ash.getEquipoPokemon().size() > 1) {
            Pokemon original = ash.getPokemonActivo();
            // Find another available Pokémon to switch to
            Pokemon toSwitch = null;
            for (Pokemon p : ash.getEquipoPokemon()) {
                if (!p.equals(original) && !p.estaDebilitado()) {
                    toSwitch = p;
                    break;
                }
            }
            if (toSwitch != null) {
                ash.setPokemonActivo(toSwitch);
                Assert.assertNotEquals("Ash should have switched Pokémon. Actual: " + ash.getEquipoPokemon().indexOf(ash.getPokemonActivo()),
                        original, ash.getPokemonActivo());
            } else {
                System.out.println("No other available Pokémon to switch.");
            }
        } else {
            System.out.println("Ash has only one Pokémon, skipping switch test.");
        }
    }
}

