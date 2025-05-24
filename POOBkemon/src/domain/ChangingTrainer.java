package domain;

import java.util.List;
import java.util.Random;

import domain.items.Revive;

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
    }
    
    @Override
    public void seleccionarItems() {
        entrenador.darItemsAleatorios();
    }
    
    @Override
    protected int tomarDecision() {
        Pokemon activo = entrenador.getPokemonActivo();
        if ((activo == null || activo.estaDebilitado()) && entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive)) {
            return 2;
        }
        if (deberiaCambiarPokemon()) {
            return 3;
        }
        if (activo != null && !activo.estaDebilitado() && 
            activo.getMovimientos().stream().anyMatch(m -> m.getPp() > 0)) {
            return 1;
        }
        if (!batalla.getPokemonsDisponiblesParaCambio(entrenador).isEmpty()) {
            return 3;
        }
        return 1;
    }
    
    @Override
    protected boolean deberiaCambiarPokemon() {
        if (batalla == null) return false;
        Pokemon miPokemon = entrenador.getPokemonActivo();
        if (miPokemon == null || miPokemon.estaDebilitado()) {
            return false;
        }
        Entrenador oponente = batalla.getEntrenador1().equals(entrenador) ? batalla.getEntrenador2() : batalla.getEntrenador1();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        if (pokemonOponente == null) {
            return random.nextBoolean();
        }
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            return false;
        }
        double efectividadActual = calcularEfectividadPromedio(miPokemon, pokemonOponente);
        Pokemon mejorOpcion = null;
        double mejorEfectividad = efectividadActual;
        for (Pokemon p : disponibles) {
            double efectividad = calcularEfectividadPromedio(p, pokemonOponente);
            if (efectividad > mejorEfectividad) {
                mejorOpcion = p;
                mejorEfectividad = efectividad;
            }
        }
        return mejorOpcion != null && mejorEfectividad >= efectividadActual * 1.25;
    }
    
    @Override
    protected void cambiarPokemon() {
        Entrenador oponente = batalla.getEntrenador1().equals(entrenador) ? batalla.getEntrenador2() : batalla.getEntrenador1();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) return;
        if (pokemonOponente == null) {
            Pokemon seleccionado = disponibles.get(random.nextInt(disponibles.size()));
            entrenador.setPokemonActivo(seleccionado);
            batalla.ejecutarTurno(3);
            return;
        }
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