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

    public POOBkemon() {
        this.poquedex = Poquedex.getInstancia();
        this.entrenadores = new ArrayList<>();
    }

    // --- Métodos para Pokémon ---
    public Pokemon crearPokemon(String nombrePokemon) {
        return poquedex.crearPokemon(nombrePokemon);
    }

    public List<String> obtenerNombresPokemonDisponibles() {
        return poquedex.obtenerNombresPokemonDisponibles();
    }

    // --- Métodos para Movimientos ---
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

    // --- Métodos para Entrenadores ---
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

    // --- Métodos para Batallas ---
    public void iniciarBatalla() {
        if (jugador1 != null && jugador2 != null) {
            this.batallaActual = new Batalla(jugador1, jugador2);
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

    // --- Getters para Batalla ---
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

    // --- Getters generales ---
    public List<Entrenador> getEntrenadores() {
        return new ArrayList<>(entrenadores);
    }

    public Batalla getBatallaActual() {
        return batallaActual;
    }

    // --- Métodos de reinicio ---
    public void reiniciarJuego() {
        this.entrenadores.clear();
        this.batallaActual = null;
        this.jugador1 = null;
        this.jugador2 = null;
    }
}
