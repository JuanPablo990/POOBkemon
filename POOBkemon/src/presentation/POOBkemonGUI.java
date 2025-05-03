package presentation;

import javax.swing.*;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarVentanaInicio();
        });
    }

    // Métodos para manejar la instancia de POOBkemon
    public static POOBkemon getPoobkemon() {
        if (poobkemon == null) {
            poobkemon = new POOBkemon();
        }
        return poobkemon;
    }

    // Métodos para manejar los entrenadores
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

    public static void reiniciarAplicacion() {
        cerrarTodasLasVentanas();
        // Limpiar referencias al reiniciar
        poobkemon = null;
        jugador1 = null;
        jugador2 = null;
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
        ventanaSeleccion = new VentanaSeleccion();
        ventanaSeleccion.mostrar();
    }

    public static void mostrarVentanaMovimientos() {
        cerrarOtrasVentanas(null);
        ventanaMovimientos = new VentanaMovimientos(null);
        ventanaMovimientos.mostrar();
    }

    public static void mostrarVentanaMovimientos(List<String> rutasPokemonSeleccionados) {
        cerrarOtrasVentanas(null);
        ventanaMovimientos = new VentanaMovimientos(rutasPokemonSeleccionados);
        ventanaMovimientos.mostrar();
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