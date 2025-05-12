package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class Pokedextest {

    private Poquedex poquedex;

    @BeforeEach
    void setUp() {
        poquedex = Poquedex.getInstancia();
    }

    @Test
    void felizCrearPokemon() {
        Pokemon p = poquedex.crearPokemon("Zubat");
        assertEquals("Zubat", p.getNombre());
    }

    @Test
    void noFelizCrearPokemonInvalido() {
        assertThrows(IllegalArgumentException.class, () -> poquedex.crearPokemon("FakeMon"));
    }

    @Test
    void felizCrearMovimiento() {
        Movimiento m = poquedex.crearMovimiento("Aerial Ace");
        assertEquals("Aerial Ace", m.getNombre());
    }

    @Test
    void noFelizCrearMovimientoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> poquedex.crearMovimiento("FakeMove"));
    }

    @Test
    void felizObtenerNombresPokemonDisponibles() {
        List<String> pokemones = poquedex.obtenerNombresPokemonDisponibles();
        assertTrue(pokemones.contains("Zubat"));
    }

    @Test
    void felizObtenerMovimientosPorTipo() {
        List<String> movimientos = poquedex.obtenerMovimientosPorTipo("volador");
        assertTrue(movimientos.contains("Aerial Ace"));
    }

    @Test
    void felizObtenerPokemonPorTipo() {
        List<String> porTipo = poquedex.obtenerPokemonPorTipo("volador");
        assertTrue(porTipo.stream().anyMatch(n -> n.equalsIgnoreCase("Zubat")));
    }

    @Test
    void felizObtenerTiposPokemon() {
        List<String> tipos = poquedex.obtenerTiposPokemonDisponibles();
        assertTrue(tipos.size() > 0);
    }

    @Test
    void felizObtenerTiposMovimiento() {
        List<String> tipos = poquedex.obtenerTiposMovimientosDisponibles();
        assertTrue(tipos.size() > 0);
    }

    @Test
    void felizExistePokemon() {
        assertTrue(poquedex.existePokemon("Zubat"));
    }

    @Test
    void noFelizExistePokemon() {
        assertFalse(poquedex.existePokemon("Mentira"));
    }

    @Test
    void felizExisteMovimiento() {
        assertTrue(poquedex.existeMovimiento("Aerial Ace"));
    }

    @Test
    void noFelizExisteMovimiento() {
        assertFalse(poquedex.existeMovimiento("MovimientoMentira"));
    }
} 