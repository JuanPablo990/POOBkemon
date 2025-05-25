package presentation;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.*;
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
    private static List<String> seleccionPokemonJugador1 = new ArrayList<>();
    private static List<String> seleccionPokemonJugador2 = new ArrayList<>();
    private static Map<String, List<String>> movimientosJugador1 = new HashMap<>();
    private static Map<String, List<String>> movimientosJugador2 = new HashMap<>();
    private static boolean mostrandoMovimientosJugador1 = true;
    private static boolean turnoJugador1;
    private static boolean modoSupervivencia = false;
    private static boolean modoDebug = false;

    /**
     * Método principal de la aplicación. Inicia la interfaz gráfica.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            turnoJugador1 = new Random().nextBoolean();
            mostrarVentanaInicio();
            if (modoDebug) {
                mostrarVentanaDebug();
            }
        });
    }

    /**
     * Obtiene la instancia de POOBkemon.
     * @return la instancia de POOBkemon.
     */
    public static POOBkemon getPoobkemon() {
        if (poobkemon == null) {
            poobkemon = new POOBkemon();
            poobkemon.cambiarTurno();
        }
        return poobkemon;
    }

    /**
     * Obtiene el jugador 1.
     * @return el Entrenador jugador 1.
     */
    public static Entrenador getJugador1() {
        if (jugador1 == null) {
            throw new IllegalStateException("Jugador 1 no ha sido inicializado");
        }
        return jugador1;
    }

    /**
     * Obtiene el jugador 2.
     * @return el Entrenador jugador 2.
     */
    public static Entrenador getJugador2() {
        if (jugador2 == null && !modoSupervivencia) {
            throw new IllegalStateException("Jugador 2 no ha sido inicializado");
        }
        return jugador2;
    }

    /**
     * Obtiene la batalla actual.
     * @return la batalla actual.
     */
    public static Batalla getBatallaActual() {
        if (batallaActual == null) {
            throw new IllegalStateException("No hay una batalla en curso");
        }
        return batallaActual;
    }

    /**
     * Establece el jugador 1.
     * @param jugador el Entrenador a establecer como jugador 1.
     */
    public static void setJugador1(Entrenador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        jugador1 = jugador;
    }

    /**
     * Establece el jugador 2.
     * @param jugador el Entrenador a establecer como jugador 2.
     */
    public static void setJugador2(Entrenador jugador) {
        if (jugador == null && !modoSupervivencia) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        jugador2 = jugador;
    }

    /**
     * Establece la selección de Pokémon del jugador 1.
     * @param seleccion lista de nombres de Pokémon.
     */
    public static void setSeleccionPokemonJugador1(List<String> seleccion) {
        if (seleccion == null || seleccion.isEmpty() || seleccion.size() > 6) {
            throw new IllegalArgumentException("La selección debe contener entre 1 y 6 Pokémon");
        }
        seleccionPokemonJugador1 = new ArrayList<>(seleccion);
    }

    /**
     * Obtiene la selección de Pokémon del jugador 1.
     * @return lista de nombres de Pokémon.
     */
    public static List<String> getSeleccionPokemonJugador1() {
        return new ArrayList<>(seleccionPokemonJugador1);
    }

    /**
     * Establece la selección de Pokémon del jugador 2.
     * @param seleccion lista de nombres de Pokémon.
     */
    public static void setSeleccionPokemonJugador2(List<String> seleccion) {
        if (seleccion == null || seleccion.isEmpty() || seleccion.size() > 6) {
            throw new IllegalArgumentException("La selección debe contener entre 1 y 6 Pokémon");
        }
        seleccionPokemonJugador2 = new ArrayList<>(seleccion);
    }

    /**
     * Obtiene la selección de Pokémon del jugador 2.
     * @return lista de nombres de Pokémon.
     */
    public static List<String> getSeleccionPokemonJugador2() {
        return new ArrayList<>(seleccionPokemonJugador2);
    }

    /**
     * Establece los movimientos de un Pokémon para un jugador.
     * @param nombrePokemon nombre del Pokémon.
     * @param movimientos lista de movimientos.
     * @param esJugador1 true si es para el jugador 1.
     */
    public static void setMovimientosDePokemon(String nombrePokemon, List<String> movimientos, boolean esJugador1) {
        if (nombrePokemon == null || nombrePokemon.isEmpty()) {
            throw new IllegalArgumentException("El nombre del Pokémon no puede estar vacío");
        }
        if (movimientos == null || movimientos.size() != 4) {
            throw new IllegalArgumentException("Deben seleccionarse exactamente 4 movimientos");
        }

        if (esJugador1) {
            movimientosJugador1.put(nombrePokemon, new ArrayList<>(movimientos));
        } else {
            movimientosJugador2.put(nombrePokemon, new ArrayList<>(movimientos));
        }
    }

    /**
     * Obtiene los movimientos de un Pokémon para un jugador.
     * @param nombrePokemon nombre del Pokémon.
     * @param esJugador1 true si es para el jugador 1.
     * @return lista de movimientos.
     */
    public static List<String> getMovimientosDePokemon(String nombrePokemon, boolean esJugador1) {
        List<String> movimientos = esJugador1 ? movimientosJugador1.get(nombrePokemon) : movimientosJugador2.get(nombrePokemon);     
        return movimientos != null ? new ArrayList<>(movimientos) : null;
    }

    /**
     * Obtiene el mapa de movimientos del jugador 1.
     * @return mapa de movimientos.
     */
    public static Map<String, List<String>> getMovimientosJugador1() {
        return new HashMap<>(movimientosJugador1);
    }

    /**
     * Obtiene el mapa de movimientos del jugador 2.
     * @return mapa de movimientos.
     */
    public static Map<String, List<String>> getMovimientosJugador2() {
        return new HashMap<>(movimientosJugador2);
    }

    /**
     * Indica si se están mostrando los movimientos del jugador 1.
     * @return true si se muestran los movimientos del jugador 1.
     */
    public static boolean isMostrandoMovimientosJugador1() {
        return mostrandoMovimientosJugador1;
    }

    /**
     * Establece si se muestran los movimientos del jugador 1.
     * @param valor true para mostrar los movimientos del jugador 1.
     */
    public static void setMostrandoMovimientosJugador1(boolean valor) {
        mostrandoMovimientosJugador1 = valor;
    }

    /**
     * Indica si es el turno del jugador 1.
     * @return true si es el turno del jugador 1.
     */
    public static boolean isTurnoJugador1() {
        return turnoJugador1;
    }

    /**
     * Indica si está activado el modo supervivencia.
     * @return true si está en modo supervivencia.
     */
    public static boolean isModoSupervivencia() {
        return modoSupervivencia;
    }

    /**
     * Establece el modo supervivencia.
     * @param modo true para activar el modo supervivencia.
     */
    public static void setModoSupervivencia(boolean modo) {
        modoSupervivencia = modo;
    }

    /**
     * Indica si está activado el modo debug.
     * @return true si está en modo debug.
     */
    public static boolean isModoDebug() {
        return modoDebug;
    }

    /**
     * Establece el modo debug.
     * @param modo true para activar el modo debug.
     */
    public static void setModoDebug(boolean modo) {
        modoDebug = modo;
        if (modoDebug && ventanaDebug == null) {
            mostrarVentanaDebug();
        } else if (!modoDebug && ventanaDebug != null) {
            ventanaDebug.dispose();
            ventanaDebug = null;
        }
    }

    /**
     * Cambia el turno entre jugadores.
     */
    public static void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (ventanaBatalla != null) {
            ventanaBatalla.actualizarVistaJugador();
        }
    }

    /**
     * Reinicia la aplicación y sus datos.
     */
    public static void reiniciarAplicacion() {
        cerrarTodasLasVentanas();
        reiniciarDatos();
        mostrarVentanaInicio();
    }

    /**
     * Reinicia los datos internos de la aplicación.
     */
    private static void reiniciarDatos() {
        poobkemon = null;
        jugador1 = null;
        jugador2 = null;
        batallaActual = null;
        seleccionPokemonJugador1.clear();
        seleccionPokemonJugador2.clear();
        movimientosJugador1.clear();
        movimientosJugador2.clear();
        mostrandoMovimientosJugador1 = true;
        turnoJugador1 = new Random().nextBoolean();
        modoSupervivencia = false;
    }

    /**
     * Cierra todas las ventanas de la aplicación.
     */
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

    /**
     * Muestra la ventana de inicio.
     */
    public static void mostrarVentanaInicio() {
        if (ventanaInicio != null) {
            ventanaInicio.dispose();
        }
        ventanaInicio = new VentanaInicio();
        ventanaInicio.mostrar();
    }

    /**
     * Muestra la ventana de opciones.
     */
    public static void mostrarVentanaOpciones() {
        cerrarOtrasVentanas(null);
        ventanaOpciones = new VentanaOpciones();
        ventanaOpciones.mostrar();
    }

    /**
     * Muestra la ventana de créditos.
     */
    public static void mostrarVentanaCreditos() {
        cerrarOtrasVentanas(null);
        ventanaCreditos = new VentanaCreditos();
        ventanaCreditos.mostrar();
    }

    /**
     * Muestra la ventana de selección de Pokémon para el jugador 1.
     */
    public static void mostrarVentanaSeleccion() {
        mostrarVentanaSeleccion(true);
    }

    /**
     * Muestra la ventana de selección de Pokémon.
     * @param esJugador1 true si es para el jugador 1.
     */
    public static void mostrarVentanaSeleccion(boolean esJugador1) {
        cerrarOtrasVentanas(null);
        ventanaSeleccion = new VentanaSeleccion(esJugador1);
        ventanaSeleccion.mostrar();
    }

    /**
     * Muestra la ventana de selección de movimientos.
     * @param seleccion lista de nombres de Pokémon seleccionados.
     */
    public static void mostrarVentanaMovimientos(List<String> seleccion) {
        if (seleccion == null || seleccion.isEmpty() || seleccion.size() > 6) {
            throw new IllegalArgumentException("La selección debe contener entre 1 y 6 Pokémon");
        }
        cerrarOtrasVentanas(null);
        if (mostrandoMovimientosJugador1) {
            setSeleccionPokemonJugador1(seleccion);
            ventanaMovimientos = new VentanaMovimientos(seleccion, true);
        } else {
            setSeleccionPokemonJugador2(seleccion);
            ventanaMovimientos = new VentanaMovimientos(seleccion, false);
        }
        ventanaMovimientos.mostrar();
    }

    /**
     * Muestra la ventana de batalla.
     * @param nombresPokemonSeleccionados lista de nombres de Pokémon seleccionados.
     */
    public static void mostrarVentanaBatalla(List<String> nombresPokemonSeleccionados) {
        if (nombresPokemonSeleccionados == null || nombresPokemonSeleccionados.isEmpty()) {
            throw new IllegalArgumentException("La lista de Pokémon seleccionados no puede estar vacía");
        }
        cerrarOtrasVentanas(null);
        getPoobkemon().setJugadores(jugador1, jugador2);
        ventanaBatalla = new VentanaBatalla(nombresPokemonSeleccionados);
        batallaActual = new Batalla(jugador1, jugador2, ventanaBatalla);
        ventanaBatalla.mostrar();
        batallaActual.iniciarBatalla();
    }

    /**
     * Inicia la batalla según el modo de juego.
     */
    public static void iniciarBatalla() {
        if (isModoSupervivencia()) {
            List<String> nombresPokemon = new ArrayList<>();
            for (Pokemon pokemon : jugador1.getEquipoPokemon()) {
                nombresPokemon.add(pokemon.getNombre());
            }
            mostrarVentanaBatalla(nombresPokemon);
        } else {
            if (mostrandoMovimientosJugador1) {
                mostrarVentanaMovimientos(seleccionPokemonJugador1);
            } else {
                mostrarVentanaMovimientos(seleccionPokemonJugador2);
            }
        }
    }

    /**
     * Muestra la ventana de debug.
     */
    public static void mostrarVentanaDebug() {
        if (!modoDebug) return;
        
        if (ventanaDebug != null) {
            ventanaDebug.dispose();
        }
        ventanaDebug = new VentanaDebug();
        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(e -> {
            ventanaDebug.dispose();
            ventanaDebug = new VentanaDebug();
            ventanaDebug.setVisible(true);
        });
        ventanaDebug.add(botonActualizar, BorderLayout.SOUTH);
        ventanaDebug.setVisible(true);
    }

    /**
     * Obtiene la ventana de batalla.
     * @return la ventana de batalla.
     */
    public static VentanaBatalla getVentanaBatalla() {
        return ventanaBatalla;
    }

    /**
     * Cierra todas las ventanas excepto la actual.
     * @param ventanaActual ventana que no se debe cerrar.
     */
    private static void cerrarOtrasVentanas(JFrame ventanaActual) {
        if (ventanaInicio != null && ventanaInicio != ventanaActual) {
            ventanaInicio.dispose();
        }
        if (ventanaOpciones != null && ventanaOpciones != ventanaActual) {
            ventanaOpciones.dispose();
        }
        if (ventanaCreditos != null && ventanaCreditos != ventanaActual) {
            ventanaCreditos.dispose();
        }
        if (ventanaSeleccion != null && ventanaSeleccion != ventanaActual) {
            ventanaSeleccion.dispose();
        }
        if (ventanaMovimientos != null && ventanaMovimientos != ventanaActual) {
            ventanaMovimientos.dispose();
        }
        if (ventanaBatalla != null && ventanaBatalla != ventanaActual) {
            ventanaBatalla.dispose();
        }
    }
}
