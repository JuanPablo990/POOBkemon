package domain;

import java.util.List;
import java.util.Random;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.Revive;
import domain.items.SuperPotion;
    /**
     * Clase abstracta que representa una máquina de batalla.
     * Contiene métodos para seleccionar equipo, movimientos e ítems,
     */
public abstract class Machine {
    protected Entrenador entrenador;
    protected Batalla batalla;
    protected Random random;
    
    /**
	 * Constructor de la clase Machine.
	 * @param nombre Nombre del entrenador.
	 */
    public Machine(String nombre) {
        this.entrenador = new Entrenador(nombre);
        this.random = new Random();
    }
    
    /**
     * Método abstracto para seleccionar el equipo de Pokémon.
     */
    public abstract void seleccionarEquipo();
    
    /**
	 * Método abstracto para seleccionar los movimientos de los Pokémon.
	 */
    public abstract void seleccionarMovimientos();
    
    /**
     * Método abstracto para seleccionar los ítems de la mochila.
     */
    public abstract void seleccionarItems();
    
    /**
	 * Método abstracto para seleccionar el Pokémon activo.
	 */
    public void setBatalla(Batalla batalla) {
        this.batalla = batalla;
    }
    
    /**
     * Método para obtener el entrenador.
     * @return
     */
    public Entrenador getEntrenador() {
        return entrenador;
    }
    
    /**
	 * Método para obtener la batalla.
	 * @return
	 */
    public void realizarTurno() {
        if (batalla == null || batalla.isBatallaTerminada()) {
            return;
        }
        boolean esMiTurno = (batalla.isTurnoJugador1() && batalla.getEntrenador1().equals(entrenador)) ||(!batalla.isTurnoJugador1() && batalla.getEntrenador2().equals(entrenador));
        if (!esMiTurno) {
            return;
        }
        int decision = tomarDecision();
        switch (decision) {
            case 1 -> atacar();
            case 2 -> usarItem();
            case 3 -> cambiarPokemon();
        }
    }
    
    /**
     * Método para tomar una decisión en la batalla.
     * @return
     */
    protected int tomarDecision() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo.getMovimientos().stream().noneMatch(m -> m.getPp() > 0)) {
            return 3;
        }
        if (deberiaCambiarPokemon()) {
            return 3;
        }
        if (tieneItemsUtiles() && necesitaUsarItem()) {
            return 2;
        }
        return 1;
    }
    
    /**
	 * Método para determinar si se debe cambiar de Pokémon.
	 * @return
	 */
    protected boolean deberiaCambiarPokemon() {
        if (batalla == null) return false;
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? batalla.getEntrenador2().getPokemonActivo() : batalla.getEntrenador1().getPokemonActivo();
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            return false;
        }
        double efectividadActual = calcularEfectividadPromedio(miPokemon, oponente);
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, oponente);
            if (efectividad > efectividadActual * 1.5) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método para calcular la efectividad promedio de los movimientos de un Pokémon contra otro.
     * @param atacante
     * @param defensor
     * @return
     */
    protected double calcularEfectividadPromedio(Pokemon atacante, Pokemon defensor) {
        double efectividadTotal = 0;
        int movimientosValidos = 0;
        for (Movimiento m : atacante.getMovimientos()) {
            if (m.getPp() > 0) {
                efectividadTotal += batalla.calcularEfectividad(m, defensor);
                movimientosValidos++;
            }
        }
        return movimientosValidos > 0 ? efectividadTotal / movimientosValidos : 0;
    }
    
    /**
	 * Método para determinar si el Pokémon está debilitado.
	 * @param pokemon
	 * @return
	 */
    protected boolean tieneItemsUtiles() {
        return !entrenador.getMochilaItems().isEmpty();
    }
    
    /**
     * Método para determinar si se necesita usar un ítem.
     * @return
     */
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null || activo.estaDebilitado()) {
            return entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive);
        }
        double porcentajeSalud = (double)activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.5 && entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Potion || i instanceof SuperPotion || i instanceof HyperPotion);
    }
    
    /**
     * Método para atacar al oponente.
     */
    protected void atacar() {
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? batalla.getEntrenador2().getPokemonActivo() : batalla.getEntrenador1().getPokemonActivo();
        Movimiento mejorMovimiento = null;
        double mejorEfectividad = 0;
        for (Movimiento m : miPokemon.getMovimientos()) {
            if (m.getPp() > 0) {
                double efectividad = batalla.calcularEfectividad(m, oponente);
                if (mejorMovimiento == null || efectividad > mejorEfectividad) {
                    mejorMovimiento = m;
                    mejorEfectividad = efectividad;
                }
            }
        }
        if (mejorMovimiento != null) {
            batalla.ejecutarTurno(1);
        }
    }
    
    /**
	 * Método para usar un ítem.
	 */
    protected void usarItem() {
        if (!tieneItemsUtiles()) return;
        if (entrenador.getPokemonActivo() == null || entrenador.getPokemonActivo().estaDebilitado()) {
            for (int i = 0; i < entrenador.getMochilaItems().size(); i++) {
                if (entrenador.getMochilaItems().get(i) instanceof Revive) {
                    batalla.ejecutarTurno(2);
                    return;
                }
            }
        }
        for (int i = 0; i < entrenador.getMochilaItems().size(); i++) {
            Item item = entrenador.getMochilaItems().get(i);
            if (item instanceof HyperPotion) {
                batalla.ejecutarTurno(2);
                return;
            }
        }
        for (int i = 0; i < entrenador.getMochilaItems().size(); i++) {
            Item item = entrenador.getMochilaItems().get(i);
            if (item instanceof SuperPotion) {
                batalla.ejecutarTurno(2);
                return;
            }
        }
        for (int i = 0; i < entrenador.getMochilaItems().size(); i++) {
            Item item = entrenador.getMochilaItems().get(i);
            if (item instanceof Potion) {
                batalla.ejecutarTurno(2);
                return;
            }
        }
    }
    
    /**
     * Método para cambiar de Pokémon.
     */
    protected void cambiarPokemon() {
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? batalla.getEntrenador2().getPokemonActivo() : batalla.getEntrenador1().getPokemonActivo();
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) return;
        Pokemon mejorOpcion = null;
        double mejorEfectividad = 0;
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, oponente);
            if (mejorOpcion == null || efectividad > mejorEfectividad) {
                mejorOpcion = p;
                mejorEfectividad = efectividad;
            }
        }
        if (mejorOpcion != null) {
            batalla.ejecutarTurno(3);
        }
    }
}