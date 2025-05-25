package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.Revive;
import domain.items.SuperPotion;

    /**
     * Clase que representa un entrenador defensivo en el juego.
     */
public class DefensiveTrainer extends Machine {
    private static final String NOMBRE = "DefensiveTrainer";
    private static final List<String> MOVIMIENTOS_DEFENSIVOS = List.of(
        "Subir Defensa",
        "Subir DefensaEspecial",
        "Bajar Ataque",
        "Bajar AtaqueEspecial"
    );
    
    private final Random random = new Random();

    /**
	 * Constructor de la clase DefensiveTrainer.
	 * Inicializa el entrenador con un nombre específico.
	 */
    public DefensiveTrainer() {
        super(NOMBRE);
    }

    /**
     * Método para seleccionar el equipo de Pokémon del entrenador.
     * Genera un equipo aleatorio completo y configura los ítems.
     */
    @Override
    public void seleccionarEquipo() {
        entrenador.generarEquipoAleatorioCompleto();
        configurarItems();
    }

    /**
	 * Método para seleccionar los movimientos de los Pokémon del entrenador.
	 * Asigna movimientos defensivos y ofensivos a cada Pokémon.
	 */
    @Override
    public void seleccionarMovimientos() {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> movimientos = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String nombreMovimiento = MOVIMIENTOS_DEFENSIVOS.get(random.nextInt(MOVIMIENTOS_DEFENSIVOS.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
            }
            List<String> movimientosCompatibles = obtenerMovimientosCompatibles(pokemon);
            movimientosCompatibles.removeAll(MOVIMIENTOS_DEFENSIVOS);
            for (int i = 0; i < 2 && !movimientosCompatibles.isEmpty(); i++) {
                String nombreMovimiento = movimientosCompatibles.get(random.nextInt(movimientosCompatibles.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
                movimientosCompatibles.remove(nombreMovimiento);
            }
            pokemon.asignarMovimientos(movimientos);
        }
    }
    
    /**
	 * Método para verificar si un movimiento es compatible con los tipos del Pokémon.
	 * @param tipoMovimiento Tipo del movimiento a verificar.
	 * @param tipoPrincipal Tipo principal del Pokémon.
	 * @param tipoSecundario Tipo secundario del Pokémon (puede ser null).
	 * @return true si el movimiento es compatible, false en caso contrario.
	 */
    private boolean esMovimientoCompatible(String tipoMovimiento, String tipoPrincipal, String tipoSecundario) {
        Map<String, List<String>> compatibilidad = new HashMap<>();
        compatibilidad.put("Acero", List.of("Acero", "Roca", "Tierra","Normal"));
        compatibilidad.put("Agua", List.of("Agua", "Hielo","Normal"));
        compatibilidad.put("Bicho", List.of("Bicho", "Planta", "Veneno","Normal"));
        compatibilidad.put("Dragón", List.of("Dragón", "Agua", "Eléctrico","Normal"));
        compatibilidad.put("Eléctrico", List.of("Eléctrico", "Normal"));
        compatibilidad.put("Fantasma", List.of("Fantasma", "Siniestro","Normal"));
        compatibilidad.put("Fuego", List.of("Fuego", "Volador", "Acero","Normal"));
        compatibilidad.put("Hada", List.of("Hada", "Psíquico", "Planta","Normal"));
        compatibilidad.put("Hielo", List.of("Hielo", "Agua","Normal"));
        compatibilidad.put("Lucha", List.of("Lucha", "Roca", "Siniestro","Normal"));
        compatibilidad.put("Normal", List.of("Normal"));
        compatibilidad.put("Planta", List.of("Planta", "Bicho", "Hada","Normal"));
        compatibilidad.put("Psíquico", List.of("Psíquico", "Hada","Normal"));
        compatibilidad.put("Roca", List.of("Roca", "Tierra", "Acero","Normal"));
        compatibilidad.put("Siniestro", List.of("Siniestro", "Fantasma", "Lucha","Normal"));
        compatibilidad.put("Tierra", List.of("Tierra", "Roca", "Acero","Normal"));
        compatibilidad.put("Veneno", List.of("Veneno", "Planta", "Bicho","Normal"));
        compatibilidad.put("Volador", List.of("Volador", "Normal", "Lucha"));
        List<String> tiposCompatibles = compatibilidad.get(tipoMovimiento);
        if (tiposCompatibles == null) {
            return false;
        }
        return tiposCompatibles.contains(tipoPrincipal) || 
               (tipoSecundario != null && tiposCompatibles.contains(tipoSecundario));
    }

    /**
     * Método para obtener los movimientos compatibles con el Pokémon.
     * @param pokemon
     * @return
     */
    private List<String> obtenerMovimientosCompatibles(Pokemon pokemon) {
        List<String> movimientosCompatibles = new ArrayList<>();
        List<String> todosMovimientos = Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
        String tipoPrincipal = pokemon.getTipoPrincipal();
        String tipoSecundario = pokemon.getTipoSecundario();
        for (String nombreMovimiento : todosMovimientos) {
            Movimiento movimiento = Poquedex.getInstancia().crearMovimiento(nombreMovimiento);
            String tipoMovimiento = movimiento.getTipo();
            if (esMovimientoCompatible(tipoMovimiento, tipoPrincipal, tipoSecundario)) {
                movimientosCompatibles.add(nombreMovimiento);
            }
        }
        
        return movimientosCompatibles;
    }

    /**
	 * Método para seleccionar los ítems del entrenador.
	 * En este caso, no se seleccionan ítems adicionales.
	 */
    @Override
    public void seleccionarItems() {
    }

    /**
     * Método para configurar los ítems del entrenador.
     * Agrega un Revive y dos HyperPociones a la mochila del entrenador.
     */
    private void configurarItems() {
        entrenador.getMochilaItems().clear();
        entrenador.agregarItem(new Revive());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
    }

    /**
	 * Método para determinar si el entrenador debería cambiar de Pokémon.
	 * @return true si debería cambiar, false en caso contrario.
	 */
    @Override
    protected int tomarDecision() {
        if (batalla == null || batalla.isBatallaTerminada()) {
            return 1;
        }
        Pokemon activo = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        if (activo == null || activo.estaDebilitado()) {
            if (tieneRevive()) {
                return 2;
            }
            return 3;
        }
        if (necesitaUsarItem() && tienePociones()) {
            return 2; 
        }
        boolean tieneMovimientosDefensivos = activo.getMovimientos().stream().anyMatch(m -> m.getPp() > 0 && esMovimientoDefensivo(m));
        
        if (tieneMovimientosDefensivos && random.nextDouble() < 0.7) {
            return 1;
        }
        if (deberiaCambiarPokemon()) {
            return 3;
        }
        return 1;
    }

    /**
     * Método para determinar si el entrenador debería cambiar de Pokémon.
     * Verifica si el Pokémon activo tiene menos del 50% de salud y si hay un Pokémon en la reserva.
     */
    @Override
    protected void atacar() {
        Pokemon activo = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        if (activo == null || oponente == null) {
            return;
        }
        List<Movimiento> movimientosDisponibles = activo.getMovimientos().stream().filter(m -> m.getPp() > 0).collect(Collectors.toList());
        if (movimientosDisponibles.isEmpty()) {
            return;
        }
        List<Movimiento> defensivos = movimientosDisponibles.stream().filter(this::esMovimientoDefensivo).collect(Collectors.toList());
        List<Movimiento> ofensivos = movimientosDisponibles.stream().filter(m -> !esMovimientoDefensivo(m)).collect(Collectors.toList());
        Movimiento movimientoElegido;
        if (!defensivos.isEmpty() && random.nextDouble() < 0.7) {
            movimientoElegido = defensivos.get(random.nextInt(defensivos.size()));
        } else if (!ofensivos.isEmpty()) {
            movimientoElegido = ofensivos.get(0);
            double mejorEfectividad = 0;
            for (Movimiento m : ofensivos) {
                double efectividad = batalla.calcularEfectividad(m, oponente);
                if (efectividad > mejorEfectividad) {
                    movimientoElegido = m;
                    mejorEfectividad = efectividad;
                }
            }
        } else {
            movimientoElegido = defensivos.get(random.nextInt(defensivos.size()));
        }
        batalla.ejecutarTurno(1);
    }

    /**
     * Método para determinar si el entrenador debería cambiar de Pokémon.
     * @return
     */
    private Pokemon obtenerPokemonOponente() {
        return batalla.getEntrenador1().equals(entrenador) 
            ? batalla.getEntrenador2().getPokemonActivo() : batalla.getEntrenador1().getPokemonActivo();
    }

    /**
	 * Método para determinar si el entrenador debería cambiar de Pokémon.
	 * Verifica si el Pokémon activo tiene menos del 50% de salud y si hay un Pokémon en la reserva.
	 * @return true si debería cambiar, false en caso contrario.
	 */
    private boolean esMovimientoDefensivo(Movimiento movimiento) {
        return MOVIMIENTOS_DEFENSIVOS.contains(movimiento.getNombre());
    }

    /**
	 * Método para determinar si el entrenador debería cambiar de Pokémon.
	 * Verifica si el Pokémon activo tiene menos del 50% de salud y si hay un Pokémon en la reserva.
	 * @return true si debería cambiar, false en caso contrario.
	 */
    private boolean tieneRevive() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Revive);
    }

    /**
     * Método para determinar si el entrenador tiene pociones.
     * Verifica si hay pociones, superpociones o hiperpociones en la mochila.
     * @return
     */
    private boolean tienePociones() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion);
    }

    /**
     * Método para determinar si el entrenador necesita usar un ítem.
     * Verifica si el Pokémon activo está debilitado o si tiene menos del 50% de salud.
     */
    @Override
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null || activo.estaDebilitado()) {
            return tieneRevive();
        }
        double porcentajeSalud = (double) activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.5 && tienePociones();
    }
}