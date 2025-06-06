package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Clase principal del juego POOBkemon.
 * Maneja la creación de entrenadores, pokémons, movimientos y la lógica de batalla.
 */
public class POOBkemon implements Serializable {
    private static final long serialVersionUID = 1L;
    private Poquedex poquedex;
    private List<Entrenador> entrenadores;
    private Batalla batallaActual;
    private Entrenador jugador1;
    private Entrenador jugador2;
    private boolean turnoJugador1;
    private boolean modoSupervivencia = false;

    /**
	 * Constructor por defecto que inicializa el Poquedex y la lista de entrenadores.
	 */
    public POOBkemon() {
        this.poquedex = Poquedex.getInstancia();
        this.entrenadores = new ArrayList<>();
    }

    /**
	 * Constructor que inicializa el juego con dos jugadores y un modo de supervivencia.
	 * @param nombreJugador1 Nombre del primer jugador.
	 * @param nombreJugador2 Nombre del segundo jugador.
	 * @param modoSupervivencia Indica si se juega en modo supervivencia.
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
     * Constructor que inicializa el juego con dos entrenadores y un modo de supervivencia.
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
	 * Constructor que inicializa el juego con dos máquinas y un modo de supervivencia.
	 * @param jugador1 Máquina del primer jugador.
	 * @param jugador2 Máquina del segundo jugador.
	 * @param modoSupervivencia Indica si se juega en modo supervivencia.
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
	 * Genera equipos aleatorios para los jugadores si están en modo supervivencia.
	 */
    private void generarEquiposAleatorios() {
        if (jugador1 != null) jugador1.generarEquipoAleatorioCompleto();
        if (jugador2 != null) jugador2.generarEquipoAleatorioCompleto();
    }

    /**
	 * Crea un nuevo entrenador con el nombre especificado.
	 * @param nombre Nombre del entrenador.
	 * @return El nuevo entrenador creado.
	 */
    public Entrenador crearEntrenador(String nombre) {
        Entrenador entrenador = new Entrenador(nombre);
        entrenadores.add(entrenador);
        return entrenador;
    }

    /**
     * Crea un nuevo entrenador con un equipo aleatorio completo.
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
	 * Crea un Pokémon con el nombre especificado.
	 * @param nombrePokemon Nombre del Pokémon a crear.
	 * @return El Pokémon creado.
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
	 * Obtiene un Pokémon por su nombre del equipo de un entrenador.
	 * @param entrenador Entrenador del cual se busca el Pokémon.
	 * @param nombre Nombre del Pokémon a buscar.
	 * @return El Pokémon encontrado o null si no existe.
	 */
    public Pokemon getPokemonPorNombre(Entrenador entrenador, String nombre) {
        return entrenador.getPokemonPorNombre(nombre);
    }

    /**
     * Crea un movimiento con el nombre especificado.
     * @param nombreMovimiento
     * @return
     */
    public Movimiento crearMovimiento(String nombreMovimiento) {
        return poquedex.crearMovimiento(nombreMovimiento);
    }

    /**
	 * Obtiene una lista de nombres de movimientos disponibles en el Poquedex.
	 * @return Lista de nombres de movimientos.
	 */
    public List<String> obtenerNombresMovimientosDisponibles() {
        return poquedex.obtenerNombresMovimientosDisponibles();
    }

    /**
	 * Obtiene una lista de movimientos por tipo.
	 * @param tipo Tipo de movimiento.
	 * @return Lista de nombres de movimientos del tipo especificado.
	 */
    public List<String> obtenerMovimientosPorTipo(String tipo) {
        return poquedex.obtenerMovimientosPorTipo(tipo);
    }

    /**
	 * Asigna movimientos a un Pokémon de un entrenador.
	 * @param entrenador Entrenador al que pertenece el Pokémon.
	 * @param indicePokemon Índice del Pokémon en el equipo del entrenador.
	 * @param nombresMovimientos Lista de nombres de movimientos a asignar.
	 */
    public void asignarMovimientosAPokemon(Entrenador entrenador, int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = nombresMovimientos.stream()
            .map(this::crearMovimiento)
            .collect(Collectors.toList());
        entrenador.asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    /**
     * Inicia una batalla entre los dos entrenadores.
     */
    public void iniciarBatalla() {
        if (jugador1 != null && jugador2 != null) {
            this.batallaActual = new Batalla(jugador1, jugador2);
            this.turnoJugador1 = batallaActual.isTurnoJugador1();
            batallaActual.iniciarBatalla();
        }
    }

    /**
	 * Establece los jugadores de la batalla.
	 * @param jugador1 Entrenador del primer jugador.
	 * @param jugador2 Entrenador del segundo jugador.
	 */
    public void setJugadores(Entrenador jugador1, Entrenador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        if (!entrenadores.contains(jugador1)) entrenadores.add(jugador1);
        if (!entrenadores.contains(jugador2)) entrenadores.add(jugador2);
        this.turnoJugador1 = new Random().nextBoolean();
    }

    /**
	 * Prepara la batalla con los jugadores actuales.
	 */
    public void prepararBatalla() {
        setJugadores(jugador1, jugador2);
        iniciarBatalla();
    }

    /**
     * Realiza un ataque de un Pokémon a otro.
     * @param entrenador
     * @param indiceItem
     * @param indicePokemon
     * @return
     */
    public String usarItem(Entrenador entrenador, int indiceItem, int indicePokemon) {
        return entrenador.usarItem(indiceItem, indicePokemon);
    }

    /**
	 * Realiza un ataque de un Pokémon a otro.
	 * @param entrenador
	 * @param indiceMovimiento
	 * @param pokemonAtacado
	 * @return
	 */
    public Entrenador getEntrenador1() {
        return jugador1 != null ? jugador1 : (batallaActual != null ? batallaActual.getEntrenador1() : null);
    }

    /**
     * Obtiene el entrenador 2 de la batalla actual o del jugador 2 si no hay batalla.
     * @return
     */
    public Entrenador getEntrenador2() {
        return jugador2 != null ? jugador2 : (batallaActual != null ? batallaActual.getEntrenador2() : null);
    }

    /**
     * Realiza un ataque de un Pokémon a otro.
     * @return
     */
    public boolean isBatallaTerminada() {
        return batallaActual != null && batallaActual.isBatallaTerminada();
    }

    /**
	 * Obtiene el ganador de la batalla actual.
	 * @return El entrenador ganador o null si no hay batalla.
	 */
    public Entrenador getGanador() {
        return batallaActual != null ? batallaActual.getGanador() : null;
    }

    /**
     * Obtiene el Pokémon activo de un entrenador.
     * @param entrenador
     * @return
     */
    public Pokemon getPokemonActivo(Entrenador entrenador) {
        if (entrenador == null) return null;
        return entrenador.getPokemonActivo();
    }

    /**
	 * Obtiene los movimientos disponibles de un Pokémon.
	 * @param pokemon
	 * @return
	 */
    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon != null ? 
            pokemon.getMovimientos().stream().filter(m -> m.getPp() > 0).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Obtiene las opciones de turno disponibles para un entrenador.
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
	 * Obtiene los Pokémon disponibles para cambiar en el equipo de un entrenador.
	 * @param entrenador
	 * @return
	 */
    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador != null ? 
            entrenador.getEquipoPokemon().stream().filter(p -> !p.equals(entrenador.getPokemonActivo()) && !p.estaDebilitado()).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Obtiene la lista de entrenadores registrados en el juego.
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
     * Verifica si es el turno del jugador 1.
     * @return
     */
    public boolean esTurnoJugador1() {
        return turnoJugador1;
    }

    /**
	 * Cambia el turno al siguiente jugador.
	 */
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (batallaActual != null) batallaActual.cambiarTurno();
    }

    /**
     * Obtiene el entrenador que está en turno.
     * @return
     */
    public Entrenador getEntrenadorEnTurno() {
        return turnoJugador1 ? jugador1 : jugador2;
    }

    /**
	 * Reinicia el juego, eliminando entrenadores y batallas actuales.
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
	 * @param entrenador Entrenador al que se le generará el equipo.
	 * @param cantidad Cantidad de Pokémon a generar.
	 */
    public void generarEquipoAleatorio(Entrenador entrenador, int cantidad) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorio(cantidad);
    }

    /**
	 * Genera un equipo aleatorio completo para un entrenador.
	 * @param entrenador Entrenador al que se le generará el equipo.
	 */
    public void generarEquipoAleatorioCompleto(Entrenador entrenador) {
        if (entrenador == null) throw new IllegalArgumentException("Entrenador no puede ser nulo");
        entrenador.generarEquipoAleatorioCompleto();
    }

    /**
	 * Obtiene el Poquedex del juego.
	 * @return
	 */
    public Poquedex getPoquedex() {
        return poquedex;
    }
}
