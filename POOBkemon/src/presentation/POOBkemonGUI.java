package presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import domain.POOBkemon;
import domain.Entrenador;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;
    private static VentanaCreditos ventanaCreditos;
    private static VentanaSeleccion ventanaSeleccion;
    private static VentanaMovimientos ventanaMovimientos;
    private static VentanaBatalla ventanaBatalla;

    private static POOBkemon poobkemon;
    private static Entrenador jugador1;
    private static Entrenador jugador2;

    private static List<String> seleccionPokemonJugador1;
    private static List<String> seleccionPokemonJugador2;

    private static boolean mostrandoMovimientosJugador1 = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarVentanaInicio();
        });
    }

    public static POOBkemon getPoobkemon() {
        if (poobkemon == null) {
            poobkemon = new POOBkemon();
        }
        return poobkemon;
    }

    public static Entrenador getJugador1() {
        return jugador1;
    }

    public static Entrenador getJugador2() {
        return jugador2;
    }

    public static void setJugador1(Entrenador jugador) {
        jugador1 = jugador;
    }

    public static void setJugador2(Entrenador jugador) {
        jugador2 = jugador;
    }

    public static void setSeleccionPokemonJugador1(List<String> seleccion) {
        seleccionPokemonJugador1 = seleccion;
    }

    public static List<String> getSeleccionPokemonJugador1() {
        return seleccionPokemonJugador1;
    }

    public static void setSeleccionPokemonJugador2(List<String> seleccion) {
        seleccionPokemonJugador2 = seleccion;
    }

    public static List<String> getSeleccionPokemonJugador2() {
        return seleccionPokemonJugador2;
    }

    public static boolean isMostrandoMovimientosJugador1() {
        return mostrandoMovimientosJugador1;
    }

    public static void setMostrandoMovimientosJugador1(boolean valor) {
        mostrandoMovimientosJugador1 = valor;
    }

    public static void reiniciarAplicacion() {
        cerrarTodasLasVentanas();
        poobkemon = null;
        jugador1 = null;
        jugador2 = null;
        seleccionPokemonJugador1 = null;
        seleccionPokemonJugador2 = null;
        mostrandoMovimientosJugador1 = true;
        mostrarVentanaInicio();
    }

    private static void cerrarTodasLasVentanas() {
        if (ventanaInicio != null) {
            ventanaInicio.dispose();
            ventanaInicio = null;
        }
        if (ventanaOpciones != null) {
            ventanaOpciones.dispose();
            ventanaOpciones = null;
        }
        if (ventanaCreditos != null) {
            ventanaCreditos.dispose();
            ventanaCreditos = null;
        }
        if (ventanaSeleccion != null) {
            ventanaSeleccion.dispose();
            ventanaSeleccion = null;
        }
        if (ventanaMovimientos != null) {
            ventanaMovimientos.dispose();
            ventanaMovimientos = null;
        }
        if (ventanaBatalla != null) {
            ventanaBatalla.dispose();
            ventanaBatalla = null;
        }
    }

    public static void mostrarVentanaInicio() {
        cerrarOtrasVentanas(null);
        ventanaInicio = new VentanaInicio();
        ventanaInicio.mostrar();
    }

    public static void mostrarVentanaOpciones() {
        cerrarOtrasVentanas(null);
        ventanaOpciones = new VentanaOpciones();
        ventanaOpciones.mostrar();
    }

    public static void mostrarVentanaCreditos() {
        cerrarOtrasVentanas(null);
        ventanaCreditos = new VentanaCreditos();
        ventanaCreditos.mostrar();
    }

    public static void mostrarVentanaSeleccion() {
        cerrarOtrasVentanas(null);
        ventanaSeleccion = new VentanaSeleccion(true);
        ventanaSeleccion.mostrar();
    }

    public static void mostrarVentanaSeleccion(boolean esJugador1) {
        cerrarOtrasVentanas(null);
        ventanaSeleccion = new VentanaSeleccion(esJugador1);
        ventanaSeleccion.mostrar();
    }

    public static void mostrarVentanaMovimientos(List<String> seleccion) {
        cerrarOtrasVentanas(null);
        if (mostrandoMovimientosJugador1) {
            setSeleccionPokemonJugador1(seleccion);
            ventanaMovimientos = new VentanaMovimientos(seleccion, true);
            ventanaMovimientos.mostrar();
        } else {
            setSeleccionPokemonJugador2(seleccion);
            ventanaMovimientos = new VentanaMovimientos(seleccion, false);
            ventanaMovimientos.mostrar();
        }
    }

    public static void mostrarVentanaBatalla(List<String> nombresPokemonSeleccionados) {
        cerrarOtrasVentanas(null);
        ventanaBatalla = new VentanaBatalla(nombresPokemonSeleccionados);
        ventanaBatalla.mostrar();
    }

    private static void cerrarOtrasVentanas(JFrame ventanaActual) {
        if (ventanaInicio != null && ventanaInicio != ventanaActual) {
            ventanaInicio.dispose();
            ventanaInicio = null;
        }
        if (ventanaOpciones != null && ventanaOpciones != ventanaActual) {
            ventanaOpciones.dispose();
            ventanaOpciones = null;
        }
        if (ventanaCreditos != null && ventanaCreditos != ventanaActual) {
            ventanaCreditos.dispose();
            ventanaCreditos = null;
        }
        if (ventanaSeleccion != null && ventanaSeleccion != ventanaActual) {
            ventanaSeleccion.dispose();
            ventanaSeleccion = null;
        }
        if (ventanaMovimientos != null && ventanaMovimientos != ventanaActual) {
            ventanaMovimientos.dispose();
            ventanaMovimientos = null;
        }
        if (ventanaBatalla != null && ventanaBatalla != ventanaActual) {
            ventanaBatalla.dispose();
            ventanaBatalla = null;
        }
    }
}
