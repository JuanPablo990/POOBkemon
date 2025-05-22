package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class DefensiveTrainer extends Machine {
    private static final String NOMBRE = "DefensiveTrainer";
    private static final List<String> MOVIMIENTOS_DEFENSIVOS = List.of(
        "Subir Defensa",
        "Subir DefensaEspecial",
        "Bajar Ataque",
        "Bajar AtaqueEspecial"
    );
    
    private final Random random = new Random();

    public DefensiveTrainer() {
        super(NOMBRE);
    }

    @Override
    public void seleccionarEquipo() {
        // Generar equipo aleatorio de 6 Pokémon
        entrenador.generarEquipoAleatorioCompleto();
        
        // Configurar items defensivos
        configurarItems();
    }

    @Override
    public void seleccionarMovimientos() {
        // Para cada Pokémon en el equipo, asignar movimientos
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<Movimiento> movimientos = new ArrayList<>();
            
            // Asegurar exactamente 2 movimientos defensivos
            for (int i = 0; i < 2; i++) {
                String nombreMovimiento = MOVIMIENTOS_DEFENSIVOS.get(random.nextInt(MOVIMIENTOS_DEFENSIVOS.size()));
                movimientos.add(Poquedex.getInstancia().crearMovimiento(nombreMovimiento));
            }
            
            // Obtener movimientos compatibles con los tipos del Pokémon
            List<String> movimientosCompatibles = obtenerMovimientosCompatibles(pokemon);
            
            // Eliminar movimientos defensivos de la lista de compatibles para evitar duplicados
            movimientosCompatibles.removeAll(MOVIMIENTOS_DEFENSIVOS);
            
            // Seleccionar 2 movimientos aleatorios compatibles
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
            
            // Verificar compatibilidad basada en tipos
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

    private void configurarItems() {
        entrenador.getMochilaItems().clear();
        
        // Items defensivos estándar
        entrenador.agregarItem(new Revive());
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
        
        // 1. Prioridad absoluta: usar Revive si no hay Pokémon activo
        if (activo == null || activo.estaDebilitado()) {
            if (tieneRevive()) {
                return 2; // Usar item
            }
            return 3; // Cambiar Pokémon (aunque no debería llegar aquí si tiene Revive)
        }
        
        // 2. Usar poción si la salud está baja
        if (necesitaUsarItem() && tienePociones()) {
            return 2; // Usar item
        }
        
        // 3. Usar movimiento defensivo si es posible
        boolean tieneMovimientosDefensivos = activo.getMovimientos().stream()
            .anyMatch(m -> m.getPp() > 0 && esMovimientoDefensivo(m));
        
        if (tieneMovimientosDefensivos && random.nextDouble() < 0.7) {
            return 1; // Atacar (usando movimiento defensivo)
        }
        
        // 4. Cambiar Pokémon si está en desventaja significativa
        if (deberiaCambiarPokemon()) {
            return 3; // Cambiar Pokémon
        }
        
        // 5. Por defecto, atacar
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
        
        // Separar movimientos defensivos y ofensivos
        List<Movimiento> defensivos = movimientosDisponibles.stream()
            .filter(this::esMovimientoDefensivo)
            .collect(Collectors.toList());
        
        List<Movimiento> ofensivos = movimientosDisponibles.stream()
            .filter(m -> !esMovimientoDefensivo(m))
            .collect(Collectors.toList());
        
        Movimiento movimientoElegido;
        
        // Elegir movimiento: 70% probabilidad de elegir defensivo si hay disponibles
        if (!defensivos.isEmpty() && random.nextDouble() < 0.7) {
            movimientoElegido = defensivos.get(random.nextInt(defensivos.size()));
        } else if (!ofensivos.isEmpty()) {
            // Elegir el movimiento ofensivo más efectivo
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
            // Solo quedan movimientos defensivos
            movimientoElegido = defensivos.get(random.nextInt(defensivos.size()));
        }
        
        // Ejecutar el ataque
        batalla.ejecutarTurno(1); // El parámetro real del movimiento se manejará en Batalla
    }

    private Pokemon obtenerPokemonOponente() {
        return batalla.getEntrenador1().equals(entrenador) 
            ? batalla.getEntrenador2().getPokemonActivo() 
            : batalla.getEntrenador1().getPokemonActivo();
    }

    private boolean esMovimientoDefensivo(Movimiento movimiento) {
        return MOVIMIENTOS_DEFENSIVOS.contains(movimiento.getNombre());
    }

    private boolean tieneRevive() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Revive);
    }

    private boolean tienePociones() {
        return entrenador.getMochilaItems().stream()
            .anyMatch(item -> item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion);
    }

    @Override
    protected boolean necesitaUsarItem() {
        Pokemon activo = entrenador.getPokemonActivo();
        if (activo == null || activo.estaDebilitado()) {
            return tieneRevive();
        }
        
        // Usar poción si la salud está por debajo del 50%
        double porcentajeSalud = (double) activo.getPs() / activo.getPsMaximos();
        return porcentajeSalud < 0.5 && tienePociones();
    }
}