package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


    /**
     * Clase principal del juego POOBkemon.
	 * Maneja la creación de entrenadores, pokémons, movimientos y la lógica de batalla.
     */
public class POOBkemon {
    private Poquedex poquedex;
    private List<Entrenador> entrenadores;
    private Batalla batallaActual;
    private Entrenador jugador1;
    private Entrenador jugador2;
    private boolean turnoJugador1;
    private boolean modoSupervivencia = false;
    
    /**
	 * Constructor por defecto.
	 * Inicializa el Poquedex y la lista de entrenadores.
	 */
    public POOBkemon() {
        this.poquedex = Poquedex.getInstancia();
        this.entrenadores = new ArrayList<>();
    }

    /**
     * Constructor que inicializa el juego con dos entrenadores.
     * @param nombreJugador1
     * @param nombreJugador2
     * @param modoSupervivencia
     */
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

    /**
	 * Constructor que inicializa el juego con dos entrenadores.
	 * @param jugador1
	 * @param jugador2
	 * @param modoSupervivencia
	 */
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

    /**
     * Constructor que inicializa el juego con dos máquinas.
     * @param jugador1
     * @param jugador2
     * @param modoSupervivencia
     */
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

    /**
	 * Constructor que inicializa el juego con un entrenador y una máquina.
	 * @param jugador1
	 * @param jugador2
	 * @param modoSupervivencia
	 */
    private void generarEquiposAleatorios() {
        if (jugador1 != null) jugador1.generarEquipoAleatorioCompleto();
        if (jugador2 != null) jugador2.generarEquipoAleatorioCompleto();
    }

    /**
     * Crea un nuevo entrenador y lo agrega a la lista de entrenadores.
     * @param nombre
     * @return
     */
    public Entrenador crearEntrenador(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenadores.add(entrenador);
        return entrenador;
    }

    /**
	 * Crea un nuevo entrenador con un equipo aleatorio y lo agrega a la lista de entrenadores.
	 * @param nombre
	 * @return
	 */
    public Entrenador crearEntrenadorConEquipoAleatorio(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenador.generarEquipoAleatorioCompleto();
        entrenador.darItemsAleatorios();
        entrenadores.add(entrenador);
        return entrenador;
    }

    /**
     * Agrega un Pokémon al equipo de un entrenador.
     * @param entrenador
     * @param nombrePokemon
     */
    public void agregarPokemonAEntrenador(Entrenador entrenador, String nombrePokemon) {
        Pokemon pokemon = crearPokemon(nombrePokemon);
        entrenador.agregarPokemon(pokemon);
    }

    /**
	 * Crea un nuevo Pokémon a partir de su nombre.
	 * @param nombrePokemon
	 * @return
	 */
    public Pokemon crearPokemon(String nombrePokemon) {
        return poquedex.crearPokemon(nombrePokemon);
    }

    /**
     * Obtiene una lista de nombres de Pokémon disponibles en el Poquedex.
     * @return
     */
    public List<String> obtenerNombresPokemonDisponibles() {
        return poquedex.obtenerNombresPokemonDisponibles();
    }

    /**
     * Obtiene un Pokémon por su nombre de un entrenador.
     * @param entrenador
     * @param nombre
     * @return
     */
    public Pokemon getPokemonPorNombre(Entrenador entrenador, String nombre) {
        return entrenador.getPokemonPorNombre(nombre);
    }

    /**
	 * Crea un nuevo movimiento a partir de su nombre.
	 * @param nombreMovimiento
	 * @return
	 */
    public Movimiento crearMovimiento(String nombreMovimiento) {
        return poquedex.crearMovimiento(nombreMovimiento);
    }

    /**
     * Obtiene una lista de nombres de movimientos disponibles en el Poquedex.
     * @return
     */
    public List<String> obtenerNombresMovimientosDisponibles() {
        return poquedex.obtenerNombresMovimientosDisponibles();
    }

    /**
	 * Obtiene una lista de movimientos por tipo de un entrenador.
	 * @param tipo
	 * @return
	 */
    public List<String> obtenerMovimientosPorTipo(String tipo) {
        return poquedex.obtenerMovimientosPorTipo(tipo);
    }

    /**
     * Asigna movimientos a un Pokémon de un entrenador.
     * @param entrenador
     * @param indicePokemon
     * @param nombresMovimientos
     */
    public void asignarMovimientosAPokemon(Entrenador entrenador, int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = nombresMovimientos.stream()
            .map(this::crearMovimiento)
            .collect(Collectors.toList());
        entrenador.asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    /**
	 * Inicia una batalla entre dos entrenadores.
	 */
    public void iniciarBatalla() {
        if (jugador1 != null && jugador2 != null) {
            this.batallaActual = new Batalla(jugador1, jugador2, null);
            this.turnoJugador1 = batallaActual.isTurnoJugador1();
            batallaActual.iniciarBatalla();
        }
    }

    /**
     * Cambia el turno entre los entrenadores
     * @param jugador1
     * @param jugador2
     */
    public void setJugadores(Entrenador jugador1, Entrenador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        if (!entrenadores.contains(jugador1)) entrenadores.add(jugador1);
        if (!entrenadores.contains(jugador2)) entrenadores.add(jugador2);
        this.turnoJugador1 = new Random().nextBoolean();
    }

    /**
     * Prepara la batalla entre los entrenadores.
     */
    public void prepararBatalla() {
        setJugadores(jugador1, jugador2);
        iniciarBatalla();
    }

    /**
	 * Cambia el Pokémon activo de un entrenador.
	 * @param entrenador
	 * @param indicePokemon
	 */
    public String usarItem(Entrenador entrenador, int indiceItem, int indicePokemon) {
        return entrenador.usarItem(indiceItem, indicePokemon);
    }

    /**
     * Cambia el Pokémon activo de un entrenador.
     * @return
     */
    public Entrenador getEntrenador1() {
        return jugador1 != null ? jugador1 : (batallaActual != null ? batallaActual.getEntrenador1() : null);
    }

    /**
	 * Cambia el Pokémon activo de un entrenador.
	 * @return
	 */
    public Entrenador getEntrenador2() {
        return jugador2 != null ? jugador2 : (batallaActual != null ? batallaActual.getEntrenador2() : null);
    }

    /**
     * Cambia el Pokémon activo de un entrenador.
     * @return
     */
    public boolean isBatallaTerminada() {
        return batallaActual != null && batallaActual.isBatallaTerminada();
    }

    /**
	 * Cambia el Pokémon activo de un entrenador.
	 * @param entrenador
	 * @param pokemon
	 */
    public Entrenador getGanador() {
        return batallaActual != null ? batallaActual.getGanador() : null;
    }

    /**
     * Cambia el Pokémon activo de un entrenador.
     * @param entrenador
     * @return
     */
    public Pokemon getPokemonActivo(Entrenador entrenador) {
        return batallaActual != null ? batallaActual.getPokemonActivo(entrenador) : null;
    }

    /**
	 * Cambia el Pokémon activo de un entrenador.
	 * @param entrenador
	 * @param pokemon
	 */
    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon != null ? 
            pokemon.getMovimientos().stream().filter(m -> m.getPp() > 0).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Obtiene una lista de opciones de turno para un entrenador.
     * @param entrenador
     * @return
     */
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

    /**
	 * Cambia el Pokémon activo de un entrenador.
	 * @param entrenador
	 * @param pokemon
	 */
    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador != null ? 
            entrenador.getEquipoPokemon().stream().filter(p -> !p.equals(entrenador.getPokemonActivo()) && !p.estaDebilitado()).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Obtiene una lista de entrenadores.
     * @return
     */
    public List<Entrenador> getEntrenadores() {
        return new ArrayList<>(entrenadores);
    }

    /**
	 * Obtiene el entrenador por su nombre.
	 * @param nombre
	 * @return
	 */
    public Batalla getBatallaActual() {
        return batallaActual;
    }

    /**
     * Obtiene el entrenador por su nombre.
     * @return
     */
    public boolean esTurnoJugador1() {
        return turnoJugador1;
    }

    /**
	 * Cambia el turno entre los entrenadores.
	 */
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (batallaActual != null) batallaActual.cambiarTurno();
    }

    /**
	 * Cambia el turno entre los entrenadores.
	 * @param turnoJugador1
	 */
    public Entrenador getEntrenadorEnTurno() {
        return turnoJugador1 ? jugador1 : jugador2;
    }

    /**
     * Reinicia el juego, eliminando los entrenadores y la batalla actual.
     */
    public void reiniciarJuego() {
        this.entrenadores.clear();
        this.batallaActual = null;
        this.jugador1 = null;
        this.jugador2 = null;
        this.turnoJugador1 = new Random().nextBoolean();
        this.modoSupervivencia = false;
    }

    /**
	 * Genera un equipo aleatorio para un entrenador.
	 * @param entrenador
	 * @param cantidad
	 */
    public void generarEquipoAleatorio(Entrenador entrenador, int cantidad) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorio(cantidad);
    }

    /**
     * Genera un equipo aleatorio completo para un entrenador.
     * @param entrenador
     */
    public void generarEquipoAleatorioCompleto(Entrenador entrenador) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorioCompleto();
    }

    /**
	 * Genera un equipo aleatorio completo para un entrenador.
	 * @param entrenador
	 */
    public Poquedex getPoquedex() {
        return poquedex;
    }
}