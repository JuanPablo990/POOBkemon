package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class Batallatest {

    private Batalla batalla;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private VentanaBatallaDummy ventana;

    @BeforeEach
    void setUp() {
        ventana = new VentanaBatallaDummy();
        entrenador1 = new Entrenador("Ash");
        entrenador2 = new Entrenador("Gary");
        entrenador1.agregarPokemon(new Zubat());
        entrenador2.agregarPokemon(new Aron());
        entrenador1.asignarMovimientosPorNombre(0, Arrays.asList("Aerial Ace"));
        entrenador2.asignarMovimientosPorNombre(0, Arrays.asList("Aerial Ace"));
        batalla = new Batalla(entrenador1, entrenador2, ventana);
    }

    @Test
    void felizIniciarBatalla() {
        assertDoesNotThrow(() -> batalla.iniciarBatalla());
    }

    @Test
    void noFelizIniciarBatallaSinPokemon() {
        Entrenador vacio = new Entrenador("SinEquipo");
        assertThrows(IllegalStateException.class, () -> new Batalla(vacio, entrenador2, ventana).iniciarBatalla());
    }

    @Test
    void felizEjecutarTurnoAtacar() {
        batalla.iniciarBatalla();
        assertDoesNotThrow(() -> batalla.ejecutarTurno(1));
    }

    @Test
    void noFelizEjecutarTurnoInvalido() {
        batalla.iniciarBatalla();
        assertDoesNotThrow(() -> batalla.ejecutarTurno(99));
    }

    @Test
    void felizGetGanadorDespuesDeDebilitar() {
        batalla.iniciarBatalla();
        entrenador2.getPokemonActivo().reducirPS(entrenador2.getPokemonActivo().getPs());
        batalla.batallaTerminada();
        assertEquals(entrenador1, batalla.getGanador());
    }

    @Test
    void noFelizGetGanadorAntesDeTerminar() {
        batalla.iniciarBatalla();
        assertNull(batalla.getGanador());
    }

    @Test
    void noFelizGetPokemonActivoEntrenadorInvalido() {
        Entrenador otro = new Entrenador("Random");
        assertThrows(IllegalArgumentException.class, () -> batalla.getPokemonActivo(otro));
    }

    @Test
    void felizGetPokemonActivo() {
        assertEquals(entrenador1.getPokemonActivo(), batalla.getPokemonActivo(entrenador1));
    }

    private static class VentanaBatallaDummy extends presentation.VentanaBatalla {
        public void mostrarMensaje(String mensaje) {}
        public void agregarMensaje(String mensaje) {}
        public void cargarPokemonesIniciales() {}
        public void actualizarVistaJugador() {}
        public void actualizarVidaPokemon1(int ps) {}
        public void actualizarVidaPokemon2(int ps) {}
        public void mostrarVentanaItem() {}
        public void mostrarVentanaCambioObligatorio(Entrenador entrenador) {}
        public void mostrarVentanaCambioPokemon() {}
    }
} 