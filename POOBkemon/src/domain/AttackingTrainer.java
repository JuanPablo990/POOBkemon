package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.SuperPotion;

    /**
	 * Esta clase representa un entrenador que se especializa en ataques ofensivos.
	 * Selecciona un equipo de Pokémon con alta estadística de ataque y movimientos
	 * ofensivos. También tiene la capacidad de usar pociones para curar a sus Pokémon.
     */
public class AttackingTrainer extends Machine {
    private static final String NOMBRE = "AttackingTrainer";
    private static final List<String> MOVIMIENTOS_OFENSIVOS = List.of(
        "Subir Ataque",
        "Subir AtaqueEspecial",
        "Bajar Defensa",
        "Bajar DefensaEspecial"
    );
    
    /**
	 * Generador de números aleatorios para seleccionar movimientos y Pokémon.
	 */
    private final Random random = new Random();

    /**
     * Constructor de la clase AttackingTrainer.
     */
    public AttackingTrainer() {
        super(NOMBRE);
    }

    /**
     * 	* Selecciona un equipo de Pokémon ofensivo y configura los movimientos y
     */
    @Override
    public void seleccionarEquipo() {
        generarEquipoOfensivo();
        configurarItems();
    }

    /**
	 * Selecciona movimientos para cada Pokémon del equipo. Selecciona 2 movimientos
	 * ofensivos y 2 movimientos compatibles con el tipo del Pokémon.
	 */
    @Override
    public void seleccionarMovimientos() {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> movimientos = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String nombreMovimiento = MOVIMIENTOS_OFENSIVOS.get(random.nextInt(MOVIMIENTOS_OFENSIVOS.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
            }
            List<String> movimientosCompatibles = obtenerMovimientosCompatibles(pokemon);
            movimientosCompatibles.removeAll(MOVIMIENTOS_OFENSIVOS);
            for (int i = 0; i < 2 && !movimientosCompatibles.isEmpty(); i++) {
                String nombreMovimiento = movimientosCompatibles.get(random.nextInt(movimientosCompatibles.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
                movimientosCompatibles.remove(nombreMovimiento);
            }
            pokemon.asignarMovimientos(movimientos);
        }
    }
    
    /**
     * 	* Verifica si el movimiento es compatible con los tipos del Pokémon.
     * @param tipoMovimiento
     * @param tipoPrincipal
     * @param tipoSecundario
     * @return
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
	 * 	* Obtiene una lista de movimientos compatibles con los tipos del Pokémon.
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
     * 	* Selecciona los objetos para el equipo del entrenador. En este caso, se
     */
    @Override
    public void seleccionarItems() {
    }

    /**
     * Genera un equipo de Pokémon ofensivo para el entrenador. Selecciona los
     */
    private void generarEquipoOfensivo() {
        entrenador.getEquipoPokemon().clear();
        List<String> nombresPokemon = Poquedex.getInstancia().obtenerNombresPokemonDisponibles().stream()
            .sorted((p1, p2) -> {
                Pokemon pok1 = Poquedex.getInstancia().crearPokemon(p1);
                Pokemon pok2 = Poquedex.getInstancia().crearPokemon(p2);
                return Integer.compare(pok2.getAtaque() + pok2.getAtaqueEspecial(), pok1.getAtaque() + pok1.getAtaqueEspecial());
            })
            .collect(Collectors.toList());
        for (int i = 0; i < 6 && i < nombresPokemon.size(); i++) {
            entrenador.agregarPokemon(Poquedex.getInstancia().crearPokemon(nombresPokemon.get(i)));
        }
    }

    /**
	 * Configura los objetos del entrenador. En este caso, se agregan 3 pociones
	 * hiper.
	 */
    private void configurarItems() {
        entrenador.getMochilaItems().clear();
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
    }

    /**
     *Toma una decisión en función de la situación actual de la batalla. Puede	
     */
    @Override
    protected int tomarDecision() {
        if (batalla == null || batalla.isBatallaTerminada()) {
            return 1;
        }
        Pokemon activo = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        if (necesitaUsarItem() && tienePociones()) {
            return 2;
        }
        boolean tieneMovimientosOfensivos = activo.getMovimientos().stream().anyMatch(m -> m.getPp() > 0 && esMovimientoOfensivo(m));
        if (tieneMovimientosOfensivos && random.nextDouble() < 0.7) {
            return 1;
        }
        if (deberiaCambiarPokemon() && random.nextDouble() < 0.3) {
            return 3;
        }
        return 1;
    }

    /**
	 * Ejecuta el ataque del Pokémon activo. Selecciona un movimiento ofensivo o
	 * defensivo según la situación actual de la batalla.
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
        List<Movimiento> ofensivos = movimientosDisponibles.stream().filter(this::esMovimientoOfensivo).collect(Collectors.toList());
        List<Movimiento> otrosMovimientos = movimientosDisponibles.stream().filter(m -> !esMovimientoOfensivo(m)).collect(Collectors.toList());
        Movimiento movimientoElegido;
        if (!ofensivos.isEmpty() && random.nextDouble() < 0.7) {
            movimientoElegido = ofensivos.get(random.nextInt(ofensivos.size()));
        } else if (!otrosMovimientos.isEmpty()) {
            movimientoElegido = otrosMovimientos.get(0);
            double mejorEfectividad = 0;
            for (Movimiento m : otrosMovimientos) {
                double efectividad = batalla.calcularEfectividad(m, oponente);
                if (efectividad > mejorEfectividad) {
                    movimientoElegido = m;
                    mejorEfectividad = efectividad;
                }
            }
        } else {
            movimientoElegido = ofensivos.get(random.nextInt(ofensivos.size()));
        }
        batalla.ejecutarTurno(1);
    }

    /**
     * Obtiene el Pokémon oponente en la batalla actual.
     * @return
     */
    private Pokemon obtenerPokemonOponente() {
        return batalla.getEntrenador1().equals(entrenador) 
            ? batalla.getEntrenador2().getPokemonActivo() 
            : batalla.getEntrenador1().getPokemonActivo();
    }
    
	/**
	 * Verifica si el movimiento es ofensivo.
	 * @param movimiento
	 * @return
	 */
    private boolean esMovimientoOfensivo(Movimiento movimiento) {
        return MOVIMIENTOS_OFENSIVOS.contains(movimiento.getNombre());
    }

    /**
	 * Verifica si el entrenador tiene pociones en su mochila.
	 * @return true si tiene pociones, false en caso contrario.
	 */
    private boolean tienePociones() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion);
    }

    /**
     * Verifica si el Pokémon activo necesita usar un objeto. En este caso, se
     */
    @Override
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null || activo.estaDebilitado()) {
            return false;
        }
        double porcentajeSalud = (double) activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.6 && tienePociones();
    }

    /**
	 * Cambia el Pokémon activo por otro del equipo. Selecciona un Pokémon
	 * disponible para el cambio.
	 */
    @Override
    protected boolean deberiaCambiarPokemon() {
        if (batalla == null) return false;
        Pokemon miPokemon = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        if (miPokemon == null || oponente == null) return false;
        List<Pokemon> disponibles = batalla.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            return false;
        }
        double efectividadActual = 0;
        int movimientosEfectivos = 0;
        for (Movimiento m : miPokemon.getMovimientos()) {
            if (m.getPp() > 0) {
                efectividadActual += batalla.calcularEfectividad(m, oponente);
                movimientosEfectivos++;
            }
        }
        if (movimientosEfectivos > 0) {
            efectividadActual /= movimientosEfectivos;
        }
        return efectividadActual < 0.5;
    }
}