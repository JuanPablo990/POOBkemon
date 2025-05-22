package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class AttackingTrainer extends Machine {
    private static final String NOMBRE = "AttackingTrainer";
    private static final List<String> MOVIMIENTOS_OFENSIVOS = List.of(
        "Subir Ataque",
        "Subir AtaqueEspecial",
        "Bajar Defensa",
        "Bajar DefensaEspecial"
    );
    
    private final Random random = new Random();

    public AttackingTrainer() {
        super(NOMBRE);
    }

    @Override
    public void seleccionarEquipo() {
        // Generar equipo de Pokémon con alto ataque
        generarEquipoOfensivo();
        
        // Configurar items ofensivos
        configurarItems();
    }

    @Override
    public void seleccionarMovimientos() {
        // Para cada Pokémon en el equipo, asignar movimientos
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> movimientos = new ArrayList<>();
            
            // Asegurar exactamente 2 movimientos ofensivos (de estadísticas)
            for (int i = 0; i < 2; i++) {
                String nombreMovimiento = MOVIMIENTOS_OFENSIVOS.get(random.nextInt(MOVIMIENTOS_OFENSIVOS.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
            }
            
            // Obtener movimientos compatibles con los tipos del Pokémon
            List<String> movimientosCompatibles = obtenerMovimientosCompatibles(pokemon);
            
            // Eliminar movimientos ofensivos de estadísticas de la lista de compatibles
            movimientosCompatibles.removeAll(MOVIMIENTOS_OFENSIVOS);
            
            // Seleccionar 2 movimientos aleatorios compatibles (priorizando daño)
            for (int i = 0; i < 2 && !movimientosCompatibles.isEmpty(); i++) {
                String nombreMovimiento = movimientosCompatibles.get(random.nextInt(movimientosCompatibles.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
                movimientosCompatibles.remove(nombreMovimiento);
            }
            
            pokemon.asignarMovimientos(movimientos);
        }
    }
    
    private boolean esMovimientoCompatible(String tipoMovimiento, String tipoPrincipal, String tipoSecundario) {
        // Mapa de compatibilidad basado en el que existe en Pokemon
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

    private List<String> obtenerMovimientosCompatibles(Pokemon pokemon) {
        List<String> movimientosCompatibles = new ArrayList<>();
        
        // Obtener todos los movimientos disponibles
        List<String> todosMovimientos = Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
        
        // Obtener tipos del Pokémon
        String tipoPrincipal = pokemon.getTipoPrincipal();
        String tipoSecundario = pokemon.getTipoSecundario();
        
        // Filtrar por compatibilidad con los tipos del Pokémon
        for (String nombreMovimiento : todosMovimientos) {
            Movimiento movimiento = Poquedex.getInstancia().crearMovimiento(nombreMovimiento);
            String tipoMovimiento = movimiento.getTipo();
            
            if (esMovimientoCompatible(tipoMovimiento, tipoPrincipal, tipoSecundario)) {
                movimientosCompatibles.add(nombreMovimiento);
            }
        }
        
        return movimientosCompatibles;
    }

    @Override
    public void seleccionarItems() {
        // Los items ya fueron configurados en seleccionarEquipo()
    }

    private void generarEquipoOfensivo() {
        entrenador.getEquipoPokemon().clear();
        
        // Obtener todos los Pokémon disponibles ordenados por su potencial ofensivo
        List<String> nombresPokemon = Poquedex.getInstancia().obtenerNombresPokemonDisponibles().stream()
            .sorted((p1, p2) -> {
                Pokemon pok1 = Poquedex.getInstancia().crearPokemon(p1);
                Pokemon pok2 = Poquedex.getInstancia().crearPokemon(p2);
                return Integer.compare(pok2.getAtaque() + pok2.getAtaqueEspecial(), 
                                     pok1.getAtaque() + pok1.getAtaqueEspecial());
            })
            .collect(Collectors.toList());
        
        // Seleccionar los 6 Pokémon con mayor ataque
        for (int i = 0; i < 6 && i < nombresPokemon.size(); i++) {
            entrenador.agregarPokemon(Poquedex.getInstancia().crearPokemon(nombresPokemon.get(i)));
        }
    }

    private void configurarItems() {
        entrenador.getMochilaItems().clear();
        
        // Items ofensivos (más pociones para mantener a los Pokémon en batalla)
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
        entrenador.agregarItem(new HyperPotion());
    }

    @Override
    protected int tomarDecision() {
        if (batalla == null || batalla.isBatallaTerminada()) {
            return 1; // Valor por defecto si la batalla no es válida
        }

        Pokemon activo = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        
        // 1. Usar poción si la salud está baja
        if (necesitaUsarItem() && tienePociones()) {
            return 2; // Usar item
        }
        
        // 2. Prioridad: usar movimientos ofensivos (de estadísticas)
        boolean tieneMovimientosOfensivos = activo.getMovimientos().stream()
            .anyMatch(m -> m.getPp() > 0 && esMovimientoOfensivo(m));
        
        if (tieneMovimientosOfensivos && random.nextDouble() < 0.7) {
            return 1; // Atacar (usando movimiento ofensivo)
        }
        
        // 3. Cambiar Pokémon solo si está en gran desventaja
        if (deberiaCambiarPokemon() && random.nextDouble() < 0.3) {
            return 3; // Cambiar Pokémon
        }
        
        // 4. Por defecto, atacar
        return 1; // Atacar
    }

    @Override
    protected void atacar() {
        Pokemon activo = entrenador.getPokemonActivo();
        Pokemon oponente = obtenerPokemonOponente();
        
        if (activo == null || oponente == null) {
            return;
        }
        
        // Filtrar movimientos disponibles
        List<Movimiento> movimientosDisponibles = activo.getMovimientos().stream()
            .filter(m -> m.getPp() > 0)
            .collect(Collectors.toList());
        
        if (movimientosDisponibles.isEmpty()) {
            return;
        }
        
        // Separar movimientos ofensivos (de estadísticas) y otros movimientos
        List<Movimiento> ofensivos = movimientosDisponibles.stream()
            .filter(this::esMovimientoOfensivo)
            .collect(Collectors.toList());
        
        List<Movimiento> otrosMovimientos = movimientosDisponibles.stream()
            .filter(m -> !esMovimientoOfensivo(m))
            .collect(Collectors.toList());
        
        Movimiento movimientoElegido;
        
        // Elegir movimiento: 70% probabilidad de elegir ofensivo si hay disponibles
        if (!ofensivos.isEmpty() && random.nextDouble() < 0.7) {
            movimientoElegido = ofensivos.get(random.nextInt(ofensivos.size()));
        } else if (!otrosMovimientos.isEmpty()) {
            // Elegir el movimiento más efectivo
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
            // Solo quedan movimientos ofensivos
            movimientoElegido = ofensivos.get(random.nextInt(ofensivos.size()));
        }
        
        // Ejecutar el ataque
        batalla.ejecutarTurno(1);
    }

    private Pokemon obtenerPokemonOponente() {
        return batalla.getEntrenador1().equals(entrenador) 
            ? batalla.getEntrenador2().getPokemonActivo() 
            : batalla.getEntrenador1().getPokemonActivo();
    }

    private boolean esMovimientoOfensivo(Movimiento movimiento) {
        return MOVIMIENTOS_OFENSIVOS.contains(movimiento.getNombre());
    }

    private boolean tienePociones() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion);
    }

    @Override
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null || activo.estaDebilitado()) {
            return false; // No usa Revive este entrenador
        }
        
        // Usar poción si la salud está por debajo del 60%
        double porcentajeSalud = (double) activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.6 && tienePociones();
    }

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
        
        // Calcular efectividad promedio de los movimientos actuales
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
        
        // Cambiar si la efectividad promedio es menor a 0.5
        return efectividadActual < 0.5;
    }
}