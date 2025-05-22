package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class POOBkemon {
    private Poquedex poquedex;
    private List<Entrenador> entrenadores;
    private Batalla batallaActual;
    private Entrenador jugador1;
    private Entrenador jugador2;
    private boolean turnoJugador1;
    private boolean modoSupervivencia = false;
    
    // ========================
    // ** CONSTRUCTORES **
    // ========================
    
    // Constructor por defecto
    public POOBkemon() {
        this.poquedex = Poquedex.getInstancia();
        this.entrenadores = new ArrayList<>();
    }

    // Constructor original (para humanos)
    public POOBkemon(String nombreJugador1, String nombreJugador2, boolean modoSupervivencia) {
        this();
        this.modoSupervivencia = modoSupervivencia;
        
        if (modoSupervivencia) {
            this.jugador1 = crearEntrenadorConEquipoAleatorio(nombreJugador1);
            this.jugador2 = crearEntrenadorConEquipoAleatorio(nombreJugador2);
        } else {
            this.jugador1 = crearEntrenador(nombreJugador1);
            this.jugador2 = crearEntrenador(nombreJugador2);
        }
        
        this.turnoJugador1 = new Random().nextBoolean();
    }

    // Nuevo constructor: Humano vs IA
    public POOBkemon(Entrenador jugador1, Machine jugador2, boolean modoSupervivencia) {
        this();
        this.modoSupervivencia = modoSupervivencia;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2.getEntrenador();
        entrenadores.add(jugador1);
        entrenadores.add(this.jugador2);
        
        if (modoSupervivencia) {
            generarEquiposAleatorios();
        }
        
        this.turnoJugador1 = new Random().nextBoolean();
    }

    // Nuevo constructor: IA vs IA
    public POOBkemon(Machine jugador1, Machine jugador2, boolean modoSupervivencia) {
        this();
        this.modoSupervivencia = modoSupervivencia;
        this.jugador1 = jugador1.getEntrenador();
        this.jugador2 = jugador2.getEntrenador();
        entrenadores.add(this.jugador1);
        entrenadores.add(this.jugador2);
        
        if (modoSupervivencia) {
            generarEquiposAleatorios();
        }
        
        this.turnoJugador1 = new Random().nextBoolean();
    }

    // ========================
    // ** MÉTODOS PRIVADOS **
    // ========================
    
    private void generarEquiposAleatorios() {
        if (jugador1 != null) jugador1.generarEquipoAleatorioCompleto();
        if (jugador2 != null) jugador2.generarEquipoAleatorioCompleto();
    }

    // ========================
    // ** MÉTODOS PÚBLICOS **
    // ========================
    
    // ** Métodos para entrenadores **
    public Entrenador crearEntrenador(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenadores.add(entrenador);
        return entrenador;
    }

    public Entrenador crearEntrenadorConEquipoAleatorio(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenador.generarEquipoAleatorioCompleto();
        entrenador.darItemsAleatorios();
        entrenadores.add(entrenador);
        return entrenador;
    }

    public void agregarPokemonAEntrenador(Entrenador entrenador, String nombrePokemon) {
        Pokemon pokemon = crearPokemon(nombrePokemon);
        entrenador.agregarPokemon(pokemon);
    }

    // ** Métodos para Pokémon **
    public Pokemon crearPokemon(String nombrePokemon) {
        return poquedex.crearPokemon(nombrePokemon);
    }

    public List<String> obtenerNombresPokemonDisponibles() {
        return poquedex.obtenerNombresPokemonDisponibles();
    }

    public Pokemon getPokemonPorNombre(Entrenador entrenador, String nombre) {
        return entrenador.getPokemonPorNombre(nombre);
    }

    // ** Métodos para movimientos **
    public Movimiento crearMovimiento(String nombreMovimiento) {
        return poquedex.crearMovimiento(nombreMovimiento);
    }

    public List<String> obtenerNombresMovimientosDisponibles() {
        return poquedex.obtenerNombresMovimientosDisponibles();
    }

    public List<String> obtenerMovimientosPorTipo(String tipo) {
        return poquedex.obtenerMovimientosPorTipo(tipo);
    }

    public void asignarMovimientosAPokemon(Entrenador entrenador, int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = nombresMovimientos.stream()
            .map(this::crearMovimiento)
            .collect(Collectors.toList());
        entrenador.asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    // ** Métodos para batallas **
    public void iniciarBatalla() {
        if (jugador1 != null && jugador2 != null) {
            this.batallaActual = new Batalla(jugador1, jugador2, null);
            this.turnoJugador1 = batallaActual.isTurnoJugador1();
            batallaActual.iniciarBatalla();
        }
    }

    public void setJugadores(Entrenador jugador1, Entrenador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        if (!entrenadores.contains(jugador1)) entrenadores.add(jugador1);
        if (!entrenadores.contains(jugador2)) entrenadores.add(jugador2);
        this.turnoJugador1 = new Random().nextBoolean();
    }

    public void prepararBatalla() {
        setJugadores(jugador1, jugador2);
        iniciarBatalla();
    }

    // ** Métodos para items **
    public String usarItem(Entrenador entrenador, int indiceItem, int indicePokemon) {
        return entrenador.usarItem(indiceItem, indicePokemon);
    }

    // ** Getters y utilidades **
    public Entrenador getEntrenador1() {
        return jugador1 != null ? jugador1 : (batallaActual != null ? batallaActual.getEntrenador1() : null);
    }

    public Entrenador getEntrenador2() {
        return jugador2 != null ? jugador2 : (batallaActual != null ? batallaActual.getEntrenador2() : null);
    }

    public boolean isBatallaTerminada() {
        return batallaActual != null && batallaActual.isBatallaTerminada();
    }

    public Entrenador getGanador() {
        return batallaActual != null ? batallaActual.getGanador() : null;
    }

    public Pokemon getPokemonActivo(Entrenador entrenador) {
        return batallaActual != null ? batallaActual.getPokemonActivo(entrenador) : null;
    }

    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon != null ? 
            pokemon.getMovimientos().stream()
                .filter(m -> m.getPp() > 0)
                .collect(Collectors.toList()) : 
            new ArrayList<>();
    }

    public List<String> getOpcionesTurno(Entrenador entrenador) {
        List<String> opciones = new ArrayList<>();
        opciones.add("ATACAR");
        
        if (entrenador != null) {
            if (entrenador.getEquipoPokemon().stream()
                .filter(p -> !p.equals(entrenador.getPokemonActivo()))
                .anyMatch(p -> !p.estaDebilitado())) {
                opciones.add("CAMBIAR_POKEMON");
            }
            if (!entrenador.getMochilaItems().isEmpty()) {
                opciones.add("USAR_ITEM");
            }
        }
        return opciones;
    }

    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador != null ? 
            entrenador.getEquipoPokemon().stream()
                .filter(p -> !p.equals(entrenador.getPokemonActivo()) && !p.estaDebilitado())
                .collect(Collectors.toList()) : 
            new ArrayList<>();
    }

    public List<Entrenador> getEntrenadores() {
        return new ArrayList<>(entrenadores);
    }

    public Batalla getBatallaActual() {
        return batallaActual;
    }

    public boolean esTurnoJugador1() {
        return turnoJugador1;
    }

    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (batallaActual != null) batallaActual.cambiarTurno();
    }

    public Entrenador getEntrenadorEnTurno() {
        return turnoJugador1 ? jugador1 : jugador2;
    }

    public void reiniciarJuego() {
        this.entrenadores.clear();
        this.batallaActual = null;
        this.jugador1 = null;
        this.jugador2 = null;
        this.turnoJugador1 = new Random().nextBoolean();
        this.modoSupervivencia = false;
    }

    // ** Métodos para equipos aleatorios **
    public void generarEquipoAleatorio(Entrenador entrenador, int cantidad) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorio(cantidad);
    }

    public void generarEquipoAleatorioCompleto(Entrenador entrenador) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorioCompleto();
    }

    // ** Getters adicionales **
    public Poquedex getPoquedex() {
        return poquedex;
    }
}