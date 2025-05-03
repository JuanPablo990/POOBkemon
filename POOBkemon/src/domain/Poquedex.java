package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Poquedex {
    private static Poquedex instancia;
    private Map<String, Class<? extends Pokemon>> pokemonesDisponibles;
    private Map<String, Movimiento> movimientosDisponibles;
    
    // Constructor privado para Singleton
    private Poquedex() {
        this.pokemonesDisponibles = new HashMap<>();
        this.movimientosDisponibles = new HashMap<>();
        registrarPokemonesBase();
        registrarMovimientosBase();
    }
    
    // Método para obtener la instancia única (Singleton)
    public static synchronized Poquedex getInstancia() {
        if (instancia == null) {
            instancia = new Poquedex();
        }
        return instancia;
    }
    
    // Registra los Pokémon básicos disponibles
    private void registrarPokemonesBase() {
        registrarPokemon("Magikarp", Magikarp.class);
        registrarPokemon("Torkoal", Torkoal.class);
        registrarPokemon("Skitty", Skitty.class);
    }
    
    // Registra los movimientos básicos disponibles
    private void registrarMovimientosBase() {
        registrarMovimiento(new Splash());
        registrarMovimiento(new Tackle());
        registrarMovimiento(new HydroPump());
        registrarMovimiento(new Flail());
    }
    
    /**
     * Registra un nuevo tipo de Pokémon en la Poquedex
     * @param nombre Nombre del Pokémon
     * @param clasePokemon Clase del Pokémon (debe heredar de Pokemon)
     */
    public void registrarPokemon(String nombre, Class<? extends Pokemon> clasePokemon) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (clasePokemon == null) {
            throw new IllegalArgumentException("La clase de Pokémon no puede ser nula");
        }
        pokemonesDisponibles.put(nombre, clasePokemon);
    }
    
    /**
     * Registra un nuevo movimiento en la Poquedex
     * @param movimiento Movimiento a registrar
     */
    public void registrarMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento no puede ser nulo");
        }
        movimientosDisponibles.put(movimiento.getNombre(), movimiento);
    }
    
    /**
     * Elimina un Pokémon del catálogo disponible
     * @param nombre Nombre del Pokémon a eliminar
     */
    public void eliminarPokemonDisponible(String nombre) {
        pokemonesDisponibles.remove(nombre);
    }
    
    /**
     * Elimina un movimiento del catálogo disponible
     * @param nombre Nombre del movimiento a eliminar
     */
    public void eliminarMovimientoDisponible(String nombre) {
        movimientosDisponibles.remove(nombre);
    }
    
    /**
     * Crea una nueva instancia de un Pokémon por su nombre
     * @param nombre Nombre del Pokémon a crear
     * @return Nueva instancia del Pokémon
     * @throws IllegalArgumentException Si el Pokémon no existe
     */
    public Pokemon crearPokemon(String nombre) {
        Class<? extends Pokemon> clasePokemon = pokemonesDisponibles.get(nombre);
        if (clasePokemon == null) {
            throw new IllegalArgumentException("El Pokémon " + nombre + " no existe en la Poquedex");
        }
        
        try {
            return clasePokemon.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el Pokémon: " + e.getMessage(), e);
        }
    }
    
    /**
     * Crea una nueva instancia de un movimiento por su nombre
     * @param nombre Nombre del movimiento a crear
     * @return Nueva instancia del movimiento
     * @throws IllegalArgumentException Si el movimiento no existe
     */
    public Movimiento crearMovimiento(String nombre) {
        Movimiento original = movimientosDisponibles.get(nombre);
        if (original == null) {
            throw new IllegalArgumentException("El movimiento " + nombre + " no existe en la Poquedex");
        }
        
        try {
            // Crear una nueva instancia copiando los valores del original
            return original.getClass()
                .getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el movimiento: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la lista de nombres de Pokémon disponibles
     * @return Lista de nombres de Pokémon
     */
    public List<String> obtenerNombresPokemonDisponibles() {
        return new ArrayList<>(pokemonesDisponibles.keySet());
    }
    
    /**
     * Obtiene la lista de nombres de movimientos disponibles
     * @return Lista de nombres de movimientos
     */
    public List<String> obtenerNombresMovimientosDisponibles() {
        return new ArrayList<>(movimientosDisponibles.keySet());
    }
    
    /**
     * Obtiene Pokémon filtrados por tipo (principal o secundario)
     * @param tipo Tipo a filtrar (ej. "Agua", "Fuego")
     * @return Lista de nombres de Pokémon del tipo especificado
     */
    public List<String> obtenerPokemonPorTipo(String tipo) {
        List<String> pokemonFiltrados = new ArrayList<>();
        
        for (String nombre : pokemonesDisponibles.keySet()) {
            Pokemon pokemon = crearPokemon(nombre);
            if (pokemon.getTipoPrincipal().equalsIgnoreCase(tipo) || 
                (pokemon.getTipoSecundario() != null && 
                 pokemon.getTipoSecundario().equalsIgnoreCase(tipo))) {
                pokemonFiltrados.add(nombre);
            }
        }
        
        return pokemonFiltrados;
    }
    
    /**
     * Obtiene movimientos filtrados por tipo
     * @param tipo Tipo a filtrar (ej. "Agua", "Normal")
     * @return Lista de nombres de movimientos del tipo especificado
     */
    public List<String> obtenerMovimientosPorTipo(String tipo) {
        List<String> movimientosFiltrados = new ArrayList<>();
        
        for (Movimiento movimiento : movimientosDisponibles.values()) {
            if (movimiento.getTipo().equalsIgnoreCase(tipo)) {
                movimientosFiltrados.add(movimiento.getNombre());
            }
        }
        
        return movimientosFiltrados;
    }
    
    /**
     * Obtiene todos los tipos únicos de Pokémon disponibles
     * @return Lista de tipos de Pokémon
     */
    public List<String> obtenerTiposPokemonDisponibles() {
        List<String> tipos = new ArrayList<>();
        
        for (String nombre : pokemonesDisponibles.keySet()) {
            Pokemon pokemon = crearPokemon(nombre);
            if (!tipos.contains(pokemon.getTipoPrincipal())) {
                tipos.add(pokemon.getTipoPrincipal());
            }
            if (pokemon.getTipoSecundario() != null && 
                !tipos.contains(pokemon.getTipoSecundario())) {
                tipos.add(pokemon.getTipoSecundario());
            }
        }
        
        return tipos;
    }
    
    /**
     * Obtiene todos los tipos únicos de movimientos disponibles
     * @return Lista de tipos de movimientos
     */
    public List<String> obtenerTiposMovimientosDisponibles() {
        List<String> tipos = new ArrayList<>();
        
        for (Movimiento movimiento : movimientosDisponibles.values()) {
            if (!tipos.contains(movimiento.getTipo())) {
                tipos.add(movimiento.getTipo());
            }
        }
        
        return tipos;
    }
    
    /**
     * Verifica si un Pokémon existe en la Poquedex
     * @param nombre Nombre del Pokémon a verificar
     * @return true si existe, false si no
     */
    public boolean existePokemon(String nombre) {
        return pokemonesDisponibles.containsKey(nombre);
    }
    
    /**
     * Verifica si un movimiento existe en la Poquedex
     * @param nombre Nombre del movimiento a verificar
     * @return true si existe, false si no
     */
    public boolean existeMovimiento(String nombre) {
        return movimientosDisponibles.containsKey(nombre);
    }
}