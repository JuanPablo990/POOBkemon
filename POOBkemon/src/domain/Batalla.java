package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import presentation.VentanaBatalla;

	/**
	 * Clase que representa una batalla entre dos entrenadores.
	 * Permite gestionar los turnos, ataques, cambios de Pokémon y el estado de la batalla.
	 */
public class Batalla implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Entrenador entrenador1;
    private final Entrenador entrenador2;
    private final VentanaBatalla ventanaBatalla;
    private boolean turnoJugador1;
    private boolean modoConsola;

    /**
	 * Constructor de la clase Batalla.
	 *
	 * @param entrenador1   El primer entrenador.
	 * @param entrenador2   El segundo entrenador.
	 * @param ventanaBatalla La ventana de batalla (puede ser null si es modo consola).
	 */
    public Batalla(Entrenador entrenador1, Entrenador entrenador2, VentanaBatalla ventanaBatalla) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.ventanaBatalla = ventanaBatalla;
        this.turnoJugador1 = new Random().nextBoolean();
        this.modoConsola = (ventanaBatalla == null);
    }

    /**
     * Inicia la batalla entre los dos entrenadores.
     */
    public void iniciarBatalla() {
        if (modoConsola) {
            iniciarBatallaConsola();
        } else {
            ventanaBatalla.mostrarMensaje("¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");
            ventanaBatalla.mostrarMensaje("El turno inicial es para: " + (turnoJugador1 ? entrenador1.getNombre() : entrenador2.getNombre()));
            enviarPrimerPokemon(entrenador1);
            enviarPrimerPokemon(entrenador2);
            ventanaBatalla.cargarPokemonesIniciales();
            ventanaBatalla.actualizarVistaJugador();
        }
    }

    /**
	 * Inicia la batalla en modo consola.
	 */
    public void iniciarBatallaConsola() {
        mostrarMensaje("¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");
        mostrarMensaje("El turno inicial es para: " + (turnoJugador1 ? entrenador1.getNombre() : entrenador2.getNombre()));
        enviarPrimerPokemon(entrenador1);
        enviarPrimerPokemon(entrenador2);
    }

    /**
     * Verifica si la batalla ha terminado.
     * @return
     */
    public boolean isBatallaTerminada() {
        return this.batallaTerminada();
    }

    /**
     * Envía el primer Pokémon del entrenador a la batalla.
     * @param entrenador
     */
    private void enviarPrimerPokemon(Entrenador entrenador) {
        if (entrenador.getEquipoPokemon().isEmpty()) {
            throw new IllegalStateException("El entrenador " + entrenador.getNombre() + " no tiene Pokémon en su equipo");
        }
        Pokemon activo = entrenador.getEquipoPokemon().get(0);
        entrenador.setPokemonActivo(activo);
        mostrarMensaje("¡" + entrenador.getNombre() + " envía a " + activo.getNombre() + "!");
    }

    /**
	 * Ejecuta el turno del jugador actual.
	 * @param opcion La opción elegida por el jugador (1: Atacar, 2: Usar item, 3: Cambiar Pokémon).
	 */
    public void ejecutarTurno(int opcion) {
        Entrenador atacante = turnoJugador1 ? entrenador1 : entrenador2;
        Entrenador defensor = turnoJugador1 ? entrenador2 : entrenador1;
        switch (opcion) {
            case 1 -> atacar(atacante, defensor);
            case 2 -> usarItem(atacante);
            case 3 -> cambiarPokemon(atacante);
            default -> mostrarMensaje("Opción inválida. Pierdes tu turno.");
        }
        if (!batallaTerminada()) {
            cambiarTurno();
        }
    }

    /**
     * Realiza un ataque del Pokémon activo del entrenador atacante al Pokémon activo del defensor.
     * @param atacante
     * @param defensor
     */
    private void atacar(Entrenador atacante, Entrenador defensor) {
        Pokemon pokemonAtacante = atacante.getPokemonActivo();
        Pokemon objetivo = defensor.getPokemonActivo();
        if (!validarPokemonActivo(pokemonAtacante, objetivo)) {
            return;
        }
        Movimiento movimiento = obtenerMovimiento(pokemonAtacante);
        if (movimiento == null) {
            return;
        }
        ejecutarMovimiento(movimiento, pokemonAtacante, objetivo);
        verificarEstadoBatalla(defensor);
    }

    /**
     * Valida si el Pokémon atacante y el objetivo están activos y no debilitados.
     * @param atacante
     * @param objetivo
     * @return
     */
    private boolean validarPokemonActivo(Pokemon atacante, Pokemon objetivo) {
        if (atacante == null || atacante.estaDebilitado()) {
            mostrarMensaje("No tienes un Pokémon activo para atacar.");
            return false;
        }
        if (objetivo == null || objetivo.estaDebilitado()) {
            mostrarMensaje("El oponente no tiene un Pokémon activo.");
            return false;
        }
        return true;
    }

    /**
	 * Obtiene un movimiento del Pokémon atacante.
	 * @param pokemon
	 * @return
	 */
    private Movimiento obtenerMovimiento(Pokemon pokemon) {
        List<Movimiento> movimientos = pokemon.getMovimientos().stream()
                .filter(m -> m.getPp() > 0)
                .collect(Collectors.toList());

        if (movimientos.isEmpty()) {
            mostrarMensaje("No hay movimientos disponibles.");
            return null;
        }
        return movimientos.get(0);
    }

    /**
     * Ejecuta el movimiento del Pokémon atacante contra el objetivo.
     * @param movimiento
     * @param atacante
     * @param objetivo
     */
    private void ejecutarMovimiento(Movimiento movimiento, Pokemon atacante, Pokemon objetivo) {
        mostrarMensaje("¡" + atacante.getNombre() + " usa " + movimiento.getNombre() + "!");
        double efectividad = calcularEfectividad(movimiento, objetivo);
        String mensajeEfectividad = obtenerMensajeEfectividad(efectividad);
        mostrarMensaje(mensajeEfectividad);
        int psAntes = objetivo.getPs();
        boolean exito = movimiento.ejecutar(atacante, objetivo, efectividad);
        if (exito) {
            int danio = psAntes - objetivo.getPs();
            mostrarMensaje("¡El ataque hizo " + danio + " de daño!");
            if (!modoConsola) {
                ventanaBatalla.actualizarVidaPokemon1(entrenador1.getPokemonActivo().getPs());
                ventanaBatalla.actualizarVidaPokemon2(entrenador2.getPokemonActivo().getPs());
            }
        } else {
            mostrarMensaje("¡El ataque falló!");
        }
    }

    /**
	 * Obtiene un mensaje de efectividad basado en el multiplicador.
	 * @param efectividad
	 * @return
	 */
    private String obtenerMensajeEfectividad(double efectividad) {
        String mensaje = String.format("[Multiplicador: x%.1f]%n", efectividad);
        if (efectividad <= 0.0) {
            mensaje += "INEFECTIVO";
        } else if (efectividad >= 2.0) {
            mensaje += "SUPEREFECTIVO";
        } else if (efectividad == 0.5) {
            mensaje += "EFECTIVO";
        } else {
            mensaje += "NEUTRAL";
        }
        return mensaje;
    }

    /**
     * Calcula la efectividad de un movimiento contra un Pokémon objetivo.
     * @param movimiento
     * @param objetivo
     * @return
     */
    public double calcularEfectividad(Movimiento movimiento, Pokemon objetivo) {
        String tipoAtaque = movimiento.getTipo().toLowerCase().replace("é", "e");
        String tipoDefensa = objetivo.getTipoPrincipal().toLowerCase().replace("é", "e");
        double efectividad = Efectividad.calcular(tipoAtaque, tipoDefensa);
        if (objetivo.getTipoSecundario() != null && !objetivo.getTipoSecundario().isEmpty()) {
            String tipoSecundario = objetivo.getTipoSecundario().toLowerCase().replace("é", "e");
            efectividad *= Efectividad.calcular(tipoAtaque, tipoSecundario);
        }
        return efectividad;
    }

    /**
	 * Verifica el estado de la batalla y muestra un mensaje si el Pokémon objetivo está debilitado.
	 * @param defensor
	 */
    private void verificarEstadoBatalla(Entrenador defensor) {
        Pokemon objetivo = defensor.getPokemonActivo();
        if (objetivo.estaDebilitado()) {
            mostrarMensaje("¡" + objetivo.getNombre() + " fue debilitado!");
            if (!equipoDebilitado(defensor)) {
                if (!modoConsola) {
                    ventanaBatalla.mostrarVentanaCambioObligatorio(defensor);
                }
            }
        }
    }

    /**
     * Usa un item del entrenador.
     * @param entrenador
     */
    private void usarItem(Entrenador entrenador) {
        if (entrenador.getMochilaItems().isEmpty()) {
            mostrarMensaje("No tienes items en tu mochila.");
            return;
        }
        if (!modoConsola) {
            ventanaBatalla.mostrarVentanaItem();
        }
    }

    /**
	 * Cambia el Pokémon activo del entrenador.
	 * @param entrenador
	 * @param pokemon
	 */
    public Pokemon getPokemonActivo(Entrenador entrenador) {
        if (!entrenador.equals(entrenador1) && !entrenador.equals(entrenador2)) {
            throw new IllegalArgumentException("El entrenador no está en esta batalla.");
        }
        return entrenador.getPokemonActivo();
    }

    /**
     * Cambia el Pokémon activo del entrenador.
     * @param entrenador
     */
    private void cambiarPokemon(Entrenador entrenador) {
        if (getPokemonsDisponiblesParaCambio(entrenador).isEmpty()) {
            mostrarMensaje("No tienes otros Pokémon disponibles para cambiar.");
            return;
        }
        if (!modoConsola) {
            ventanaBatalla.mostrarVentanaCambioPokemon();
        }
    }

    /**
	 * Cambia el turno al siguiente jugador.
	 */
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (!modoConsola) {
            ventanaBatalla.actualizarVistaJugador();
        }
    }

    /**
     * Verifica si la batalla ha terminado.
     * @return
     */
    public boolean batallaTerminada() {
        boolean terminada = equipoDebilitado(entrenador1) || equipoDebilitado(entrenador2);
        if (terminada) {
            determinarGanador();
        }
        return terminada;
    }

    /**
	 * Verifica si el equipo de un entrenador está debilitado.
	 * @param entrenador
	 * @return
	 */
    private boolean equipoDebilitado(Entrenador entrenador) {
        return entrenador.getEquipoPokemon().stream().allMatch(Pokemon::estaDebilitado);
    }

    /**
     * Determina el ganador de la batalla.
     */
    private void determinarGanador() {
        Entrenador ganador = equipoDebilitado(entrenador1) ? entrenador2 : entrenador1;
        mostrarMensaje("\n¡" + ganador.getNombre() + " gana la batalla!");
    }

    /**
	 * Devuelve el entrenador ganador de la batalla.
	 * @return
	 */
    public Entrenador getGanador() {
        return this.isBatallaTerminada() ? (this.equipoDebilitado(entrenador1) ? entrenador2 : entrenador1) : null;
    }

    /**
     * Devuelve una lista de movimientos disponibles para el Pokémon.
     * @param pokemon
     * @return
     */
    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon.getMovimientos().stream().filter(m -> m.getPp() > 0).collect(Collectors.toList());
    }

    /**
	 * Devuelve una lista de Pokémon disponibles para cambiar.
	 * @param entrenador
	 * @return
	 */
    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador.getEquipoPokemon().stream()
                .filter(p -> !p.equals(entrenador.getPokemonActivo()))
                .filter(p -> !p.estaDebilitado())
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el Pokémon activo del entrenador.
     * @return
     */
    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    /**
	 * Devuelve el Pokémon activo del entrenador.
	 * @return
	 */
    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    /**
     * Devuelve el Pokémon activo del entrenador.
     * @return
     */
    public boolean isTurnoJugador1() {
        return turnoJugador1;
    }

    /**
	 * Devuelve el modo de batalla (consola o ventana).
	 * @return
	 */
    private void mostrarMensaje(String mensaje) {
        if (modoConsola) {
            System.out.println(mensaje);
        } else if (ventanaBatalla != null) {
            ventanaBatalla.mostrarMensaje(mensaje);
        }
    }
}