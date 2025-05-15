package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class POOBkemontest {

    private POOBkemon juego;
    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        juego = new POOBkemon();
        entrenador = juego.crearEntrenador("Thomas");
    }

    @Test
    void felizCrearPokemon() {
        Pokemon p = juego.crearPokemon("Zubat");
        assertEquals("Zubat", p.getNombre());
    }

    @Test
    void noFelizCrearPokemonInvalido() {
        assertThrows(IllegalArgumentException.class, () -> juego.crearPokemon("FakeMon"));
    }

    @Test
    void felizCrearMovimiento() {
        Movimiento m = juego.crearMovimiento("Aerial Ace");
        assertEquals("Aerial Ace", m.getNombre());
    }

    @Test
    void noFelizCrearMovimientoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> juego.crearMovimiento("FakeMove"));
    }

    @Test
    void felizAgregarPokemonAEntrenador() {
        juego.agregarPokemonAEntrenador(entrenador, "Zubat");
        assertEquals("Zubat", entrenador.getPokemonActivo().getNombre());
    }

    @Test
    void noFelizAgregarPokemonAEntrenadorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> juego.agregarPokemonAEntrenador(entrenador, "Inexistente"));
    }

    @Test
    void felizAsignarMovimientos() {
        juego.agregarPokemonAEntrenador(entrenador, "Zubat");
        juego.asignarMovimientosAPokemon(entrenador, 0, Arrays.asList("Aerial Ace", "Amnesia"));
        assertEquals(2, entrenador.getPokemonActivo().getMovimientos().size());
    }

    @Test
    void noFelizAsignarMovimientosInvalidos() {
        juego.agregarPokemonAEntrenador(entrenador, "Zubat");
        assertThrows(IllegalArgumentException.class, () -> juego.asignarMovimientosAPokemon(entrenador, 0, Arrays.asList("Falso1", "Falso2")));
    }

    @Test
    void noFelizGenerarEquipoEntrenadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> juego.generarEquipoAleatorio(null, 3));
    }

    @Test
    void noFelizUsarItemEntrenadorNulo() {
        assertThrows(IllegalArgumentException.class, () -> juego.usarItem(null, 0, 0));
    }

    @Test
    void felizReiniciarJuego() {
        juego.agregarPokemonAEntrenador(entrenador, "Zubat");
        juego.reiniciarJuego();
        assertTrue(juego.getEntrenadores().isEmpty());
        assertNull(juego.getBatallaActual());
    }
} 