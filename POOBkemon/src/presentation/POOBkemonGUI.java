package presentation;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import domain.*;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;
    private static VentanaCreditos ventanaCreditos;
    private static VentanaSeleccion ventanaSeleccion;
    private static VentanaMovimientos ventanaMovimientos;
    private static VentanaBatalla ventanaBatalla;
    private static VentanaDebug ventanaDebug;

    private static POOBkemon poobkemon;
    private static Entrenador jugador1;
    private static Entrenador jugador2;
    private static Batalla batallaActual;

    private static List<String> seleccionPokemonJugador1;
    private static List<String> seleccionPokemonJugador2;

    private static Map<String, List<String>> movimientosJugador1 = new HashMap<>();
    private static Map<String, List<String>> movimientosJugador2 = new HashMap<>();

    private static boolean mostrandoMovimientosJugador1 = true;
    private static boolean turnoJugador1 = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarVentanaInicio();
            mostrarVentanaDebug();
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

    public static Batalla getBatallaActual() {
        return batallaActual;
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

    public static void setMovimientosDePokemon(String nombrePokemon, List<String> movimientos, boolean esJugador1) {
        if (esJugador1) {
            movimientosJugador1.put(nombrePokemon, movimientos);
        } else {
            movimientosJugador2.put(nombrePokemon, movimientos);
        }
    }

    public static List<String> getMovimientosDePokemon(String nombrePokemon, boolean esJugador1) {
        return esJugador1 ? movimientosJugador1.get(nombrePokemon) : movimientosJugador2.get(nombrePokemon);
    }

    public static Map<String, List<String>> getMovimientosJugador1() {
        return movimientosJugador1;
    }

    public static Map<String, List<String>> getMovimientosJugador2() {
        return movimientosJugador2;
    }

    public static boolean isMostrandoMovimientosJugador1() {
        return mostrandoMovimientosJugador1;
    }

    public static void setMostrandoMovimientosJugador1(boolean valor) {
        mostrandoMovimientosJugador1 = valor;
    }

    public static boolean isTurnoJugador1() {
        return turnoJugador1;
    }

    public static void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (ventanaBatalla != null) {
            ventanaBatalla.actualizarVistaJugador();
        }
    }

    public static void reiniciarAplicacion() {
        cerrarTodasLasVentanas();
        poobkemon = null;
        jugador1 = null;
        jugador2 = null;
        batallaActual = null;
        seleccionPokemonJugador1 = null;
        seleccionPokemonJugador2 = null;
        movimientosJugador1.clear();
        movimientosJugador2.clear();
        mostrandoMovimientosJugador1 = true;
        turnoJugador1 = true;
        mostrarVentanaInicio();
    }

    private static void cerrarTodasLasVentanas() {
        if (ventanaInicio != null) ventanaInicio.dispose();
        if (ventanaOpciones != null) ventanaOpciones.dispose();
        if (ventanaCreditos != null) ventanaCreditos.dispose();
        if (ventanaSeleccion != null) ventanaSeleccion.dispose();
        if (ventanaMovimientos != null) ventanaMovimientos.dispose();
        if (ventanaBatalla != null) ventanaBatalla.dispose();
        if (ventanaDebug != null) ventanaDebug.dispose();
        ventanaInicio = null;
        ventanaOpciones = null;
        ventanaCreditos = null;
        ventanaSeleccion = null;
        ventanaMovimientos = null;
        ventanaBatalla = null;
        ventanaDebug = null;
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
        mostrarVentanaSeleccion(true);
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
        getPoobkemon().setJugadores(jugador1, jugador2);
        ventanaBatalla = new VentanaBatalla(nombresPokemonSeleccionados);
        batallaActual = new Batalla(jugador1, jugador2, ventanaBatalla);
        ventanaBatalla.mostrar();
        batallaActual.iniciarBatalla();
    }

    public static void mostrarVentanaDebug() {
        if (ventanaDebug != null) ventanaDebug.dispose();
        ventanaDebug = new VentanaDebug();
        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaDebug.dispose();
                ventanaDebug = new VentanaDebug();
                ventanaDebug.setVisible(true);
            }
        });
        ventanaDebug.add(botonActualizar, java.awt.BorderLayout.SOUTH);
        ventanaDebug.setVisible(true);
    }

    public static VentanaBatalla getVentanaBatalla() {
        return ventanaBatalla;
    }

    private static void cerrarOtrasVentanas(JFrame ventanaActual) {
        if (ventanaInicio != null && ventanaInicio != ventanaActual) ventanaInicio.dispose();
        if (ventanaOpciones != null && ventanaOpciones != ventanaActual) ventanaOpciones.dispose();
        if (ventanaCreditos != null && ventanaCreditos != ventanaActual) ventanaCreditos.dispose();
        if (ventanaSeleccion != null && ventanaSeleccion != ventanaActual) ventanaSeleccion.dispose();
        if (ventanaMovimientos != null && ventanaMovimientos != ventanaActual) ventanaMovimientos.dispose();
        if (ventanaBatalla != null && ventanaBatalla != ventanaActual) ventanaBatalla.dispose();
    }
}