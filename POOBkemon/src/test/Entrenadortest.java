package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class Entrenadortest {

    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        entrenador = new Entrenador("Ash");
    }

    @Test
    void felizAgregarPokemon() {
        Pokemon zubat = new Zubat();
        entrenador.agregarPokemon(zubat);
        assertTrue(entrenador.getEquipoPokemon().contains(zubat));
        assertEquals(zubat, entrenador.getPokemonActivo());
    }

    @Test
    void noFelizAgregarPokemonExceso() {
        for (int i = 0; i < 6; i++) {
            entrenador.agregarPokemon(new Zubat());
        }
        assertThrows(IllegalStateException.class, () -> {
            entrenador.agregarPokemon(new Zubat());
        });
    }

    @Test
    void felizSetPokemonActivoPorIndice() {
        entrenador.agregarPokemon(new Aron());
        entrenador.agregarPokemon(new Zubat());
        entrenador.setPokemonActivo(1);
        assertEquals("Zubat", entrenador.getPokemonActivo().getNombre());
    }

    @Test
    void noFelizSetPokemonActivoIndiceInvalido() {
        entrenador.agregarPokemon(new Aron());
        assertThrows(IllegalArgumentException.class, () -> {
            entrenador.setPokemonActivo(5);
        });
    }

    @Test
    void felizEliminarPokemon() {
        entrenador.agregarPokemon(new Zubat());
        entrenador.eliminarPokemon(0);
        assertTrue(entrenador.getEquipoPokemon().isEmpty());
    }

    @Test
    void noFelizEliminarPokemonIndiceInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            entrenador.eliminarPokemon(0);
        });
    }

    @Test
    void noFelizAgregarItemNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            entrenador.agregarItem(null);
        });
    }

    @Test
    void felizGetPokemonPorNombre() {
        Pokemon zubat = new Zubat();
        entrenador.agregarPokemon(zubat);
        assertEquals(zubat, entrenador.getPokemonPorNombre("Zubat"));
    }

    @Test
    void noFelizGetPokemonPorNombreInexistente() {
        assertNull(entrenador.getPokemonPorNombre("Charmander"));
    }

    @Test
    void felizTienePokemonDisponibles() {
        entrenador.agregarPokemon(new Zubat());
        assertTrue(entrenador.tienePokemonDisponibles());
    }
} 
