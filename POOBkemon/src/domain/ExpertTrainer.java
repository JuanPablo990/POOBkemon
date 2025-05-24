package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.Revive;
import domain.items.SuperPotion;

public class ExpertTrainer extends Machine {
    
    public ExpertTrainer(String nombre) {
        super(nombre);
    }
    
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
    
    @Override
    public void seleccionarMovimientos() {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> mejoresMovimientos = seleccionarMejoresMovimientos(pokemon);
            pokemon.asignarMovimientos(mejoresMovimientos);
        }
    }
    
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
    
    @Override
    public void seleccionarItems() {
        entrenador.getMochilaItems().clear();
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new Revive());
    }
    
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
        boolean tienePocion = entrenador.getMochilaItems().stream().anyMatch(i -> i instanceof Potion || i instanceof SuperPotion || i instanceof HyperPotion);
        return necesitaCuracion && tienePocion;
    }
    
    private String buscarMovimientoPorTipo(List<String> movimientos, String tipo) {
        return movimientos.stream().filter(m -> Poquedex.getInstancia().crearMovimiento(m).getTipo().equalsIgnoreCase(tipo)).findFirst().orElse(movimientos.get(0));
    }
    
    private String buscarMovimientoMasFuerte(List<String> movimientos) {
        return movimientos.stream().max((m1, m2) -> Integer.compare(Poquedex.getInstancia().crearMovimiento(m1).getPotencia(),Poquedex.getInstancia().crearMovimiento(m2).getPotencia())).orElse(movimientos.get(0));
    }
    
    private List<String> obtenerDebilidades(Pokemon pokemon) {
        return List.of("agua", "tierra", "roca");
    }
}