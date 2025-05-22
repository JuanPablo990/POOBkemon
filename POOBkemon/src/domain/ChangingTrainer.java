package domain;

import java.util.List;
import java.util.Random;

public class ChangingTrainer extends Machine {
    
    public ChangingTrainer(String nombre) {
        super(nombre);
    }
    
    @Override
    public void seleccionarEquipo() {
        entrenador.generarEquipoAleatorioCompleto();
    }
    
    @Override
    public void seleccionarMovimientos() {
        // Los movimientos ya se asignan en generarEquipoAleatorioCompleto()
    }
    
    @Override
    public void seleccionarItems() {
        entrenador.darItemsAleatorios();
    }
    
    @Override
    protected int tomarDecision() {
        Pokemon activo = entrenador.getPokemonActivo();
        
        // Priorizar usar Revive si el Pokémon está debilitado
        if ((activo == null || activo.estaDebilitado()) && 
            entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive)) {
            return 2; // Usar item (Revive)
        }
        
        // Decidir si cambiar Pokémon
        if (deberiaCambiarPokemon()) {
            return 3; // Cambiar Pokémon
        }
        
        // Si el Pokémon puede atacar, hacerlo
        if (activo != null && !activo.estaDebilitado() && 
            activo.getMovimientos().stream().anyMatch(m -> m.getPp() > 0)) {
            return 1; // Atacar
        }
        
        // Si no hay movimientos, cambiar Pokémon si es posible
        if (!batalla.getPokemonsDisponiblesParaCambio(entrenador).isEmpty()) {
            return 3; // Cambiar Pokémon
        }
        
        return 1; // Por defecto, atacar
    }
    
    @Override
    protected boolean deberiaCambiarPokemon() {
        if (batalla == null) return false;
        
        Pokemon miPokemon = entrenador.getPokemonActivo();
        if (miPokemon == null || miPokemon.estaDebilitado()) {
            return false;
        }
        
        Entrenador oponente = batalla.getEntrenador1().equals(entrenador) ? 
                            batalla.getEntrenador2() : 
                            batalla.getEntrenador1();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        
        // Cambiar aleatoriamente si el oponente no tiene Pokémon activo
        if (pokemonOponente == null) {
            return random.nextBoolean();
        }
        
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            return false;
        }
        
        // Calcular efectividad actual
        double efectividadActual = calcularEfectividadPromedio(miPokemon, pokemonOponente);
        
        // Buscar mejor opción
        Pokemon mejorOpcion = null;
        double mejorEfectividad = efectividadActual;
        
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, pokemonOponente);
            if (efectividad > mejorEfectividad) {
                mejorOpcion = p;
                mejorEfectividad = efectividad;
            }
        }
        
        // Cambiar si encontramos uno significativamente mejor
        return mejorOpcion != null && mejorEfectividad >= efectividadActual * 1.25;
    }
    
    @Override
    protected void cambiarPokemon() {
        Entrenador oponente = batalla.getEntrenador1().equals(entrenador) ? 
                            batalla.getEntrenador2() : 
                            batalla.getEntrenador1();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) return;
        
        // Cambiar aleatoriamente si no hay oponente activo
        if (pokemonOponente == null) {
            Pokemon seleccionado = disponibles.get(random.nextInt(disponibles.size()));
            entrenador.setPokemonActivo(seleccionado);
            batalla.ejecutarTurno(3);
            return;
        }
        
        // Buscar el Pokémon más efectivo contra el oponente
        Pokemon mejorOpcion = null;
        double mejorEfectividad = 0;
        
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, pokemonOponente);
            if (mejorOpcion == null || efectividad > mejorEfectividad) {
                mejorOpcion = p;
                mejorEfectividad = efectividad;
            }
        }
        
        if (mejorOpcion != null) {
            entrenador.setPokemonActivo(mejorOpcion);
            batalla.ejecutarTurno(3);
        }
    }
}