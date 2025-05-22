package domain;

import java.util.List;
import java.util.Random;

public abstract class Machine {
    protected Entrenador entrenador;
    protected Batalla batalla;
    protected Random random;
    
    public Machine(String nombre) {
        this.entrenador = new Entrenador(nombre);
        this.random = new Random();
    }
    
    // Métodos abstractos que deben ser implementados por las subclases
    public abstract void seleccionarEquipo();
    public abstract void seleccionarMovimientos();
    public abstract void seleccionarItems();
    
    // Métodos concretos para manejar la batalla
    public void setBatalla(Batalla batalla) {
        this.batalla = batalla;
    }
    
    public Entrenador getEntrenador() {
        return entrenador;
    }
    
    public void realizarTurno() {
        if (batalla == null || batalla.isBatallaTerminada()) {
            return;
        }
        
        // Verificar si es nuestro turno
        boolean esMiTurno = (batalla.isTurnoJugador1() && batalla.getEntrenador1().equals(entrenador)) ||
                           (!batalla.isTurnoJugador1() && batalla.getEntrenador2().equals(entrenador));
        
        if (!esMiTurno) {
            return;
        }
        
        // Tomar decisión sobre qué hacer en este turno
        int decision = tomarDecision();
        
        // Ejecutar la acción correspondiente
        switch (decision) {
            case 1 -> atacar();
            case 2 -> usarItem();
            case 3 -> cambiarPokemon();
        }
    }
    
    protected int tomarDecision() {
        // Lógica básica de decisión (puede ser sobrescrita por subclases)
        Pokemon activo = entrenador.getPokemonActivo();
        
        // Si el Pokémon activo no tiene movimientos disponibles, cambiar
        if (activo.getMovimientos().stream().noneMatch(m -> m.getPp() > 0)) {
            return 3; // Cambiar Pokémon
        }
        
        // Si hay Pokémon disponibles para cambiar y el actual está en desventaja
        if (deberiaCambiarPokemon()) {
            return 3; // Cambiar Pokémon
        }
        
        // Si tiene items útiles y los necesita
        if (tieneItemsUtiles() && necesitaUsarItem()) {
            return 2; // Usar item
        }
        
        // Por defecto, atacar
        return 1; // Atacar
    }
    
    protected boolean deberiaCambiarPokemon() {
        if (batalla == null) return false;
        
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? 
                          batalla.getEntrenador2().getPokemonActivo() : 
                          batalla.getEntrenador1().getPokemonActivo();
        
        // Verificar si hay Pokémon disponibles para cambiar
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            return false;
        }
        
        // Verificar efectividad del Pokémon actual vs el oponente
        double efectividadActual = calcularEfectividadPromedio(miPokemon, oponente);
        
        // Buscar un Pokémon con mejor efectividad
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, oponente);
            if (efectividad > efectividadActual * 1.5) { // 50% mejor efectividad
                return true;
            }
        }
        
        return false;
    }
    
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
    
    protected boolean tieneItemsUtiles() {
        return !entrenador.getMochilaItems().isEmpty();
    }
    
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        
        // Usar Revive si no hay Pokémon activos disponibles
        if (activo == null || activo.estaDebilitado()) {
            return entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive);
        }
        
        // Usar poción si la salud está baja
        double porcentajeSalud = (double)activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.5 && 
               entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Potion || i instanceof SuperPotion || i instanceof HyperPotion);
    }
    
    protected void atacar() {
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? 
                          batalla.getEntrenador2().getPokemonActivo() : 
                          batalla.getEntrenador1().getPokemonActivo();
        
        // Seleccionar el mejor movimiento disponible
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
            batalla.ejecutarTurno(1); // Opción 1 es atacar
            // Nota: En una implementación real, necesitarías una forma de especificar qué movimiento usar
        }
    }
    
    protected void usarItem() {
        if (!tieneItemsUtiles()) return;
        
        // Priorizar Revive si no hay Pokémon activo
        if (entrenador.getPokemonActivo() == null || entrenador.getPokemonActivo().estaDebilitado()) {
            for (int i = 0; i < entrenador.getMochilaItems().size(); i++) {
                if (entrenador.getMochilaItems().get(i) instanceof Revive) {
                    batalla.ejecutarTurno(2); // Opción 2 es usar item
                    // Nota: En una implementación real, necesitarías una forma de especificar qué item usar
                    return;
                }
            }
        }
        
        // Usar la poción más potente disponible
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
    
    protected void cambiarPokemon() {
        Pokemon oponente = batalla.getEntrenador1().equals(entrenador) ? 
                          batalla.getEntrenador2().getPokemonActivo() : 
                          batalla.getEntrenador1().getPokemonActivo();
        
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) return;
        
        // Seleccionar el Pokémon con mejor efectividad contra el oponente
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
            batalla.ejecutarTurno(3); // Opción 3 es cambiar Pokémon
            // Nota: En una implementación real, necesitarías una forma de especificar qué Pokémon cambiar
        }
    }
}