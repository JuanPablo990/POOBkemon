package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class Pokemontest {

    private POOBkemon juego;
    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        juego = new POOBkemon();
        entrenador = juego.crearEntrenador("Ash");
    }

    @Test
    void testCrearPokemon_Feliz() {
        Pokemon absol = juego.crearPokemon("Absol");
        assertNotNull(absol);
        assertEquals("Absol", absol.getNombre());
    }

    @Test
    void testCrearPokemon_NoFeliz() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.crearPokemon("NoExiste");
        });
    }

    @Test
    void testCrearMovimiento_Feliz() {
        Movimiento movimiento = juego.crearMovimiento("Aerial Ace");
        assertNotNull(movimiento);
        assertEquals("Aerial Ace", movimiento.getNombre());
    }

    @Test
    void testCrearMovimiento_NoFeliz() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.crearMovimiento("MovimientoFake");
        });
    }

    @Test
    void testAgregarPokemonAEntrenador_Feliz() {
        juego.agregarPokemonAEntrenador(entrenador, "Zubat");
        assertFalse(entrenador.getEquipoPokemon().isEmpty());
    }

    @Test
    void testAgregarPokemonAEntrenador_NoFeliz() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.agregarPokemonAEntrenador(entrenador, "Inexistente");
        });
    }

    @Test
    void testAsignarMovimientosAPokemon_Feliz() {
        juego.agregarPokemonAEntrenador(entrenador, "Roselia");
        List<String> movimientos = Arrays.asList("Aerial Ace", "Amnesia"); // válidos y diferentes
        juego.asignarMovimientosAPokemon(entrenador, 0, movimientos);
        List<Movimiento> asignados = entrenador.getEquipoPokemon().get(0).getMovimientos();
        assertEquals(2, asignados.size());
        assertNotEquals(asignados.get(0).getNombre(), asignados.get(1).getNombre());
    }

    @Test
    void testAsignarMovimientosAPokemon_NoFeliz() {
        juego.agregarPokemonAEntrenador(entrenador, "Roselia");
        List<String> movimientos = Arrays.asList("Inventado1", "Inventado2");
        assertThrows(IllegalArgumentException.class, () -> {
            juego.asignarMovimientosAPokemon(entrenador, 0, movimientos);
        });
    }

    @Test
    void testGenerarEquipoAleatorio_NoFeliz() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.generarEquipoAleatorio(null, 3);
        });
    }

    @Test
    void testUsarItem_NoFeliz() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.usarItem(null, 0, 0);
        });
    }
} 