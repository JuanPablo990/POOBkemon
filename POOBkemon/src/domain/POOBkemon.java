package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class POOBkemon {
    private Poquedex poquedex;
    private List<Entrenador> entrenadores;
    private Batalla batallaActual;
    private Entrenador jugador1;
    private Entrenador jugador2;
    private boolean turnoJugador1 = true;
    private boolean modoSupervivencia = false;
    
    // Constructores
    public POOBkemon() {
        this.poquedex = Poquedex.getInstancia();
        this.entrenadores = new ArrayList<>();
    }

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
    }

    // Método privado para crear entrenador con equipo aleatorio (usado en modo supervivencia)
    private Entrenador crearEntrenadorConEquipoAleatorio(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenador.generarEquipoAleatorioCompleto();
        entrenador.darItemsAleatorios();
        entrenadores.add(entrenador);
        return entrenador;
    }

    // Métodos existentes (se mantienen igual)
    public Pokemon crearPokemon(String nombrePokemon) {
        return poquedex.crearPokemon(nombrePokemon);
    }

    public List<String> obtenerNombresPokemonDisponibles() {
        return poquedex.obtenerNombresPokemonDisponibles();
    }
    
    public Movimiento crearMovimiento(String nombreMovimiento) {
        return poquedex.crearMovimiento(nombreMovimiento);
    }

    public List<String> obtenerNombresMovimientosDisponibles() {
        return poquedex.obtenerNombresMovimientosDisponibles();
    }

    public List<String> obtenerMovimientosPorTipo(String tipo) {
        return poquedex.obtenerMovimientosPorTipo(tipo);
    }

    public Poquedex getPoquedex() {
        return poquedex;
    }

    public Entrenador crearEntrenador(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenadores.add(entrenador);
        return entrenador;
    }

    public void agregarPokemonAEntrenador(Entrenador entrenador, String nombrePokemon) {
        Pokemon pokemon = crearPokemon(nombrePokemon);
        entrenador.agregarPokemon(pokemon);
    }

    public void asignarMovimientosAPokemon(Entrenador entrenador, int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = new ArrayList<>();
        for (String nombre : nombresMovimientos) {
            movimientos.add(crearMovimiento(nombre));
        }
        entrenador.asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    public void iniciarBatalla() {
        if (jugador1 != null && jugador2 != null) {
            this.batallaActual = new Batalla(jugador1, jugador2, null);
            batallaActual.iniciarBatalla();
        }
    }

    public void setJugadores(Entrenador jugador1, Entrenador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        if (!entrenadores.contains(jugador1)) {
            entrenadores.add(jugador1);
        }
        if (!entrenadores.contains(jugador2)) {
            entrenadores.add(jugador2);
        }
    }

    public void prepararBatalla() {
        setJugadores(jugador1, jugador2);
        iniciarBatalla();
    }

    public Entrenador getEntrenador1() {
        return jugador1 != null ? jugador1 : (batallaActual != null ? batallaActual.getEntrenador1() : null);
    }

    public Entrenador getEntrenador2() {
        return jugador2 != null ? jugador2 : (batallaActual != null ? batallaActual.getEntrenador2() : null);
    }

    public boolean isBatallaTerminada() {
        if (batallaActual == null) return true;
        return batallaActual.isBatallaTerminada();
    }

    public Entrenador getGanador() {
        if (batallaActual == null) return null;
        return batallaActual.getGanador();
    }

    public Pokemon getPokemonActivo(Entrenador entrenador) {
        if (batallaActual == null) return null;
        return batallaActual.getPokemonActivo(entrenador);
    }

    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        if (pokemon == null) return new ArrayList<>();
        return pokemon.getMovimientos().stream()
               .filter(m -> m.getPp() > 0)
               .collect(Collectors.toList());
    }

    public List<String> getOpcionesTurno(Entrenador entrenador) {
        List<String> opciones = new ArrayList<>();
        opciones.add("ATACAR");
        if (entrenador != null) {
            boolean puedeCambiar = entrenador.getEquipoPokemon().stream()
                                  .filter(p -> !p.equals(entrenador.getPokemonActivo()))
                                  .anyMatch(p -> !p.estaDebilitado());
            if (puedeCambiar) {
                opciones.add("CAMBIAR_POKEMON");
            }
            if (!entrenador.getMochilaItems().isEmpty()) {
                opciones.add("USAR_ITEM");
            }
        }
        return opciones;
    }

    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        if (entrenador == null) return new ArrayList<>();
        return entrenador.getEquipoPokemon().stream()
               .filter(p -> !p.equals(entrenador.getPokemonActivo()) && !p.estaDebilitado())
               .collect(Collectors.toList());
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
    }

    public Entrenador getEntrenadorEnTurno() {
        return turnoJugador1 ? jugador1 : jugador2;
    }

    public void reiniciarJuego() {
        this.entrenadores.clear();
        this.batallaActual = null;
        this.jugador1 = null;
        this.jugador2 = null;
        this.turnoJugador1 = true;
    }

    // Métodos para equipos aleatorios
    public void generarEquipoAleatorio(Entrenador entrenador, int cantidad) {
        if (entrenador == null) {
            throw new IllegalArgumentException("El entrenador no puede ser nulo");
        }
        entrenador.generarEquipoAleatorio(cantidad);
    }

    public void generarEquipoAleatorioCompleto(Entrenador entrenador) {
        if (entrenador == null) {
            throw new IllegalArgumentException("El entrenador no puede ser nulo");
        }
        entrenador.generarEquipoAleatorioCompleto();
    }

    // Método para obtener pokémon por nombre
    public Pokemon getPokemonPorNombre(Entrenador entrenador, String nombre) {
        if (entrenador == null || nombre == null) {
            return null;
        }
        return entrenador.getPokemonPorNombre(nombre);
    }

    // Método para usar items
    public String usarItem(Entrenador entrenador, int indiceItem, int indicePokemon) {
        if (entrenador == null) {
            throw new IllegalArgumentException("El entrenador no puede ser nulo");
        }
        return entrenador.usarItem(indiceItem, indicePokemon);
    }

    // Nuevo método para verificar si está en modo supervivencia
    public boolean isModoSupervivencia() {
        return modoSupervivencia;
    }
}