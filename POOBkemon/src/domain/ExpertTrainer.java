package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExpertTrainer extends Machine {
    
    public ExpertTrainer(String nombre) {
        super(nombre);
    }
    
    @Override
    public void seleccionarEquipo() {
        // Crea un equipo balanceado con tipos complementarios
        List<String> todosPokemon = Poquedex.getInstancia().obtenerNombresPokemonDisponibles();
        entrenador.getEquipoPokemon().clear();
        
        // Seleccionar Pokémon con tipos estratégicos
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
    
    @Override
    public void seleccionarMovimientos() {
        // Asigna movimientos de cobertura para cada Pokémon
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> mejoresMovimientos = seleccionarMejoresMovimientos(pokemon);
            pokemon.asignarMovimientos(mejoresMovimientos);
        }
    }
    
    private List<Movimiento> seleccionarMejoresMovimientos(Pokemon pokemon) {
        // Selecciona movimientos que cubran las debilidades del Pokémon
        List<String> todosMovimientos = Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
        List<Movimiento> movimientosSeleccionados = new ArrayList<>();
        
        // Asegurar al menos un movimiento del mismo tipo (STAB)
        movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
            buscarMovimientoPorTipo(todosMovimientos, pokemon.getTipoPrincipal())));
        
        // Añadir movimientos de tipos que contrarresten las debilidades
        for (String tipo : obtenerDebilidades(pokemon)) {
            movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
                buscarMovimientoPorTipo(todosMovimientos, tipo)));
            if (movimientosSeleccionados.size() >= 4) break;
        }
        
        // Completar con movimientos poderosos si aún hay espacio
        while (movimientosSeleccionados.size() < 4) {
            movimientosSeleccionados.add(Poquedex.getInstancia().crearMovimiento(
                buscarMovimientoMasFuerte(todosMovimientos)));
        }
        
        return movimientosSeleccionados;
    }
    
    @Override
    public void seleccionarItems() {
        // Mochila optimizada para batallas
        entrenador.getMochilaItems().clear();
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new Revive());
        // Removed FullRestore as it's not available in the base classes
    }
    
    @Override
    protected int tomarDecision() {
        Pokemon activo = entrenador.getPokemonActivo();
        Entrenador oponente = getOponente();
        Pokemon pokemonOponente = oponente.getPokemonActivo();
        
        // 1. Priorizar Revive si es necesario
        if ((activo == null || activo.estaDebilitado()) && tieneRevive()) {
            return 2;
        }
        
        // 2. Usar item si es beneficioso
        if (necesitaUsarItemEstrategico()) {
            return 2;
        }
        
        // 3. Cambiar Pokémon si da ventaja significativa
        if (deberiaCambiarPokemon()) {
            return 3;
        }
        
        // 4. Atacar con el mejor movimiento
        return 1;
    }
    
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
        
        // Calcular efectividad actual
        double efectividadActual = calcularEfectividadPromedio(miPokemon, oponente);
        
        // Buscar mejor Pokémon disponible
        Pokemon mejorPokemon = null;
        double mejorEfectividad = efectividadActual;
        
        for (Pokemon p : batalla.getPokemonsDisponiblesParaCambio(entrenador)) {
            double efectividad = calcularEfectividadPromedio(p, oponente);
            if (efectividad > mejorEfectividad) {
                mejorPokemon = p;
                mejorEfectividad = efectividad;
            }
        }
        
        // Cambiar si encontramos uno mejor (umbral más bajo que ChangingTrainer)
        return mejorPokemon != null && mejorEfectividad >= efectividadActual * 1.1;
    }
    
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
            
            // Bonus por STAB (Same Type Attack Bonus)
            if (m.getTipo().equalsIgnoreCase(miPokemon.getTipoPrincipal()) || 
               (miPokemon.getTipoSecundario() != null && m.getTipo().equalsIgnoreCase(miPokemon.getTipoSecundario()))) {
                puntaje *= 1.5;
            }
            
            if (puntaje > mejorPuntaje) {
                mejorMovimiento = m;
                mejorPuntaje = puntaje;
            }
        }
        
        if (mejorMovimiento != null) {
            batalla.ejecutarTurno(1); // Ejecutar ataque
        }
    }
    
    // Métodos auxiliares
    private Entrenador getOponente() {
        return batalla.getEntrenador1().equals(entrenador) ? 
              batalla.getEntrenador2() : batalla.getEntrenador1();
    }
    
    private boolean tieneRevive() {
        return entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Revive);
    }
    
    private boolean necesitaUsarItemEstrategico() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null) return false;
        
        double porcentajeSalud = (double)activo.getPs() / activo.getPsMaximos();
        boolean necesitaCuracion = porcentajeSalud < 0.6;
        boolean tienePocion = entrenador.getMochilaItems().stream()
            .anyMatch(i -> i instanceof Potion || i instanceof SuperPotion || i instanceof HyperPotion);
        
        return necesitaCuracion && tienePocion;
    }
    
    private String buscarMovimientoPorTipo(List<String> movimientos, String tipo) {
        // Implementación para buscar movimiento por tipo
        return movimientos.stream()
            .filter(m -> Poquedex.getInstancia().crearMovimiento(m).getTipo().equalsIgnoreCase(tipo))
            .findFirst()
            .orElse(movimientos.get(0));
    }
    
    private String buscarMovimientoMasFuerte(List<String> movimientos) {
        // Implementación para buscar el movimiento más fuerte
        return movimientos.stream()
            .max((m1, m2) -> Integer.compare(
                Poquedex.getInstancia().crearMovimiento(m1).getPotencia(),
                Poquedex.getInstancia().crearMovimiento(m2).getPotencia()))
            .orElse(movimientos.get(0));
    }
    
    private List<String> obtenerDebilidades(Pokemon pokemon) {
        // Implementación simplificada para obtener debilidades
        // Aquí deberías implementar la lógica real basada en los tipos del Pokémon
        return List.of("agua", "tierra", "roca"); // Ejemplo
    }
}