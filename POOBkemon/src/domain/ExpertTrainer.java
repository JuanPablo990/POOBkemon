package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.Revive;
import domain.items.SuperPotion;


    /**
     * Esta clase representa un entrenador experto en el juego.
     */
public class ExpertTrainer extends Machine {
    
	/**
	 * Constructor de la clase ExpertTrainer.
	 * @param nombre El nombre del entrenador experto.
	 */
    public ExpertTrainer(String nombre) {
        super(nombre);
    }
    
    /**
	 * Método para seleccionar el equipo de Pokémon del entrenador experto.
	 * Se seleccionan Pokémon de tipos estratégicos: fuego, agua, planta, eléctrico, tierra y volador.
	 */
    @Override
    public void seleccionarEquipo() {
        List<String> todosPokemon = Poquedex.getInstancia().obtenerNombresPokemonDisponibles();
        entrenador.getEquipoPokemon().clear();
        String[] tiposEstrategicos = {"fuego", "agua", "planta", "eléctrico", "tierra", "volador"};
        for (String tipo : tiposEstrategicos) {
            for (String nombrePokemon : todosPokemon) {
                Pokemon p = Poquedex.getInstancia().crearPokemon(nombrePokemon);
                if (p.getTipoPrincipal().equalsIgnoreCase(tipo) || 
                   (p.getTipoSecundario() != null && p.getTipoSecundario().equalsIgnoreCase(tipo))) {
                    entrenador.agregarPokemon(p);
                    break;
                }
            }
        }
    }
    
    /**
     * Método para seleccionar los movimientos de los Pokémon del entrenador experto.
     * 
     */
    @Override
    public void seleccionarMovimientos() {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> mejoresMovimientos = seleccionarMejoresMovimientos(pokemon);
            pokemon.asignarMovimientos(mejoresMovimientos);
        }
    }
    
    /**
	 * Método para seleccionar los mejores movimientos para un Pokémon.
	 * 
	 * @param pokemon El Pokémon para el cual se seleccionan los movimientos.
	 * @return Una lista de los mejores movimientos seleccionados.
	 */
    private List<Movimiento> seleccionarMejoresMovimientos(Pokemon pokemon) {
        List<String> todosMovimientos = Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
        List<Movimiento> movimientosSeleccionados = new ArrayList<>();
        movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
            buscarMovimientoPorTipo(todosMovimientos, pokemon.getTipoPrincipal())));
        for (String tipo : obtenerDebilidades(pokemon)) {
            movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
                buscarMovimientoPorTipo(todosMovimientos, tipo)));
            if (movimientosSeleccionados.size() >= 4) break;
        }
        while (movimientosSeleccionados.size() < 4) {
            movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
                buscarMovimientoMasFuerte(todosMovimientos)));
        }
        return movimientosSeleccionados;
    }
    
    /**
     * Método para seleccionar los items del entrenador experto.
     */
    @Override
    public void seleccionarItems() {
        entrenador.getMochilaItems().clear();
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new Revive());
    }
    
    /**
	 * Método para seleccionar la estrategia del entrenador experto.
	 * 
	 * @return La estrategia seleccionada.
	 */
    @Override
    protected int tomarDecision() {
        Pokemon activo = entrenador.getPokemonActivo();
        Entrenador oponente = getOponente();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        if ((activo == null || activo.estaDebilitado()) && tieneRevive()) {
            return 2;
        }
        if (necesitaUsarItemEstrategico()) {
            return 2;
        }
        if (deberiaCambiarPokemon()) {
            return 3;
        }
        return 1;
    }
    
    /**
     * Método para determinar si el entrenador experto debería cambiar de Pokémon.
     */
    @Override
    protected boolean deberiaCambiarPokemon() {
        Pokemon miPokemon = entrenador.getPokemonActivo();
        if (miPokemon == null || miPokemon.estaDebilitado()) {
            return false;
        }
        Pokemon oponente = getOponente().getPokemonActivo();
        if (oponente == null) {
            return false;
        }
        double efectividadActual = calcularEfectividadPromedio(miPokemon, oponente);
        Pokemon mejorPokemon = null;
        double mejorEfectividad = efectividadActual;
        for (Pokemon p : batalla.getPokemonsDisponiblesParaCambio(entrenador)) {
            double efectividad = calcularEfectividadPromedio(p, oponente);
            if (efectividad > mejorEfectividad) {
                mejorPokemon = p;
                mejorEfectividad = efectividad;
            }
        }
        return mejorPokemon != null && mejorEfectividad >= efectividadActual * 1.1;
    }
    
    /**
	 * Método para calcular la efectividad promedio de un Pokémon contra otro.
	 * 
	 * @param pokemon El Pokémon atacante.
	 * @param oponente El Pokémon defensor.
	 * @return La efectividad promedio.
	 */
    @Override
    protected void atacar() {
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = getOponente().getPokemonActivo();
        Movimiento mejorMovimiento = null;
        double mejorPuntaje = -1;
        for (Movimiento m : miPokemon.getMovimientos()) {
            if (m.getPp() <= 0) continue;
            double efectividad = batalla.calcularEfectividad(m, oponente);
            double puntaje = efectividad * m.getPotencia();
            if (m.getTipo().equalsIgnoreCase(miPokemon.getTipoPrincipal()) || (miPokemon.getTipoSecundario() != null && m.getTipo().equalsIgnoreCase(miPokemon.getTipoSecundario()))) {
                puntaje *= 1.5;
            }
            if (puntaje > mejorPuntaje) {
                mejorMovimiento = m;
                mejorPuntaje = puntaje;
            }
        }
        if (mejorMovimiento != null) {
            batalla.ejecutarTurno(1);
        }
    }
    
    /**
     * Método para obtener el oponente del entrenador experto.
     * @return
     */
    private Entrenador getOponente() {
        return batalla.getEntrenador1().equals(entrenador) ? 
              batalla.getEntrenador2() : batalla.getEntrenador1();
    }
    
    /**
	 * Método para calcular la efectividad promedio de un Pokémon contra otro.
	 * 
	 * @param pokemon El Pokémon atacante.
	 * @param oponente El Pokémon defensor.
	 * @return La efectividad promedio.
	 */
    private boolean tieneRevive() {
        return entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive);
    }
    
    /**
     * Método para determinar si el entrenador necesita usar un item estratégico.
     * @return
     */
    private boolean necesitaUsarItemEstrategico() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null) return false;
        double porcentajeSalud = (double)activo.getPs() / activo.getPsMaximos();
        boolean necesitaCuracion = porcentajeSalud < 0.6;
        boolean tienePocion = entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Potion || i instanceof SuperPotion || i instanceof HyperPotion);
        return necesitaCuracion && tienePocion;
    }
    
    /**
	 * Método para buscar un movimiento por su tipo en una lista de movimientos.
	 * 
	 * @param movimientos La lista de movimientos.
	 * @param tipo El tipo de movimiento a buscar.
	 * @return El movimiento encontrado o el primero de la lista si no se encuentra ninguno.
	 */
    private String buscarMovimientoPorTipo(List<String> movimientos, String tipo) {
        return movimientos.stream().filter(m -> Poquedex.getInstancia().crearMovimiento(m).getTipo().equalsIgnoreCase(tipo)).findFirst().orElse(movimientos.get(0));
    }
    
    /**
     * Método para buscar el movimiento más fuerte en una lista de movimientos.
     * @param movimientos
     * @return
     */
    private String buscarMovimientoMasFuerte(List<String> movimientos) {
        return movimientos.stream().max((m1, m2) -> Integer.compare(Poquedex.getInstancia().crearMovimiento(m1).getPotencia(),Poquedex.getInstancia().crearMovimiento(m2).getPotencia())).orElse(movimientos.get(0));
    }
    
    /**
	 * Método para calcular la efectividad promedio de un Pokémon contra otro.
	 * 
	 * @param pokemon El Pokémon atacante.
	 * @param oponente El Pokémon defensor.
	 * @return La efectividad promedio.
	 */
    private List<String> obtenerDebilidades(Pokemon pokemon) {
        return List.of("agua", "tierra", "roca");
    }
}