package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entrenador {
    private final String nombre;
    private final List<Pokemon> equipoPokemon;
    private final List<Item> mochilaItems;
    private Pokemon pokemonActivo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipoPokemon = new ArrayList<>(6);
        this.mochilaItems = new ArrayList<>();
        this.pokemonActivo = null;
    }

    /**
     * Genera un equipo aleatorio de Pokémon con movimientos únicos para cada uno.
     * @param cantidad Número de Pokémon en el equipo (1-6)
     */
    public void generarEquipoAleatorio(int cantidad) {
        if (cantidad < 1 || cantidad > 6) {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 6");
        }
        
        // Limpiar equipo actual
        equipoPokemon.clear();
        pokemonActivo = null;
        
        // Obtener todos los Pokémon disponibles
        List<String> nombresPokemon = obtenerListaPokemonDisponibles();
        Collections.shuffle(nombresPokemon);
        
        // Obtener todos los movimientos disponibles
        List<String> nombresMovimientos = obtenerListaMovimientosDisponibles();
        Collections.shuffle(nombresMovimientos);
        
        // Lista para llevar registro de movimientos ya asignados
        List<String> movimientosAsignados = new ArrayList<>();
        
        // Seleccionar Pokémon aleatorios
        for (int i = 0; i < cantidad && i < nombresPokemon.size(); i++) {
            String nombrePokemon = nombresPokemon.get(i);
            Pokemon pokemon = Poquedex.getInstancia().crearPokemon(nombrePokemon);
            agregarPokemon(pokemon);
            
            // Seleccionar 4 movimientos aleatorios únicos para este Pokémon
            List<String> movimientosPokemon = new ArrayList<>();
            for (int j = 0; j < 4 && j < nombresMovimientos.size(); j++) {
                String movimiento = nombresMovimientos.get((i * 4 + j) % nombresMovimientos.size());
                if (!movimientosAsignados.contains(movimiento)) {
                    movimientosPokemon.add(movimiento);
                    movimientosAsignados.add(movimiento);
                }
            }
            
            // Si no conseguimos 4 movimientos únicos, completar con repetidos pero únicos para el Pokémon
            while (movimientosPokemon.size() < 4 && !nombresMovimientos.isEmpty()) {
                String movimiento;
                do {
                    movimiento = nombresMovimientos.get((int)(Math.random() * nombresMovimientos.size()));
                } while (movimientosPokemon.contains(movimiento));
                
                movimientosPokemon.add(movimiento);
            }
            
            asignarMovimientosPorNombre(i, movimientosPokemon);
        }
    }

    /**
     * Genera un equipo aleatorio completo de 6 Pokémon con movimientos únicos.
     */
    public void generarEquipoAleatorioCompleto() {
        generarEquipoAleatorio(6);
    }

    public void agregarPokemon(Pokemon pokemon) {
        if (equipoPokemon.size() >= 6) {
            throw new IllegalStateException("El equipo ya tiene 6 Pokémon");
        }
        equipoPokemon.add(pokemon);
        if (pokemonActivo == null) {
            pokemonActivo = pokemon;
        }
    }

    public void setPokemonActivo(Pokemon pokemon) {
        if (!equipoPokemon.contains(pokemon)) {
            throw new IllegalArgumentException("El Pokémon no pertenece a este entrenador");
        }
        if (pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes seleccionar un Pokémon debilitado");
        }
        this.pokemonActivo = pokemon;
    }

    public void setPokemonActivo(int indice) {
        if (indice < 0 || indice >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        setPokemonActivo(equipoPokemon.get(indice));
    }

    public Pokemon getPokemonActivo() {
        return pokemonActivo;
    }

    public void eliminarPokemon(int indice) {
        if (indice < 0 || indice >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        Pokemon eliminado = equipoPokemon.remove(indice);
        if (eliminado.equals(pokemonActivo)) {
            buscarNuevoPokemonActivo();
        }
    }

    public void asignarMovimientosAPokemon(int indicePokemon, List<Movimiento> movimientos) {
        if (indicePokemon < 0 || indicePokemon >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        equipoPokemon.get(indicePokemon).asignarMovimientos(movimientos);
    }

    public void agregarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }
        mochilaItems.add(item);
    }

    public String usarItem(int indiceItem, int indicePokemon) {
        validarIndicesItemsYPokemon(indiceItem, indicePokemon);
        Item item = mochilaItems.get(indiceItem);
        Pokemon pokemon = equipoPokemon.get(indicePokemon);
        validarUsoItem(item, pokemon);
        item.usar(pokemon);
        mochilaItems.remove(indiceItem);
        return String.format("%s usado con éxito en %s", item.getNombre(), pokemon.getNombre());
    }

    public Pokemon agregarPokemonPorNombre(String nombrePokemon) {
        Pokemon nuevo = Poquedex.getInstancia().crearPokemon(nombrePokemon);
        agregarPokemon(nuevo);
        return nuevo;
    }

    public void asignarMovimientosPorNombre(int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = new ArrayList<>();
        for (String nombre : nombresMovimientos) {
            movimientos.add(Poquedex.getInstancia().crearMovimiento(nombre));
        }
        asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    public List<String> obtenerListaPokemonDisponibles() {
        return Poquedex.getInstancia().obtenerNombresPokemonDisponibles();
    }

    public List<String> obtenerListaMovimientosDisponibles() {
        return Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pokemon> getEquipoPokemon() {
        return new ArrayList<>(equipoPokemon);
    }

    public List<Item> getMochilaItems() {
        return new ArrayList<>(mochilaItems);
    }

    public List<Item> getItems() {
        return getMochilaItems();
    }

    public boolean tienePokemonDisponibles() {
        return equipoPokemon.stream().anyMatch(p -> !p.estaDebilitado());
    }

    @Override
    public String toString() {
        return String.format(
            "Entrenador %s - Pokémon: %d/%d - Items: %d - Activo: %s",
            nombre, equipoPokemon.size(), 6, mochilaItems.size(),
            pokemonActivo != null ? pokemonActivo.getNombre() : "Ninguno"
        );
    }

    private void buscarNuevoPokemonActivo() {
        pokemonActivo = equipoPokemon.stream()
            .filter(p -> !p.estaDebilitado())
            .findFirst()
            .orElse(null);
    }

    private void validarIndicesItemsYPokemon(int indiceItem, int indicePokemon) {
        if (indiceItem < 0 || indiceItem >= mochilaItems.size()) {
            throw new IllegalArgumentException("Índice de item inválido");
        }
        if (indicePokemon < 0 || indicePokemon >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
    }

    private void validarUsoItem(Item item, Pokemon pokemon) {
        if (item instanceof Revive && !pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes usar Revive en un Pokémon que no está debilitado");
        }
        if ((item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion)
            && pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes usar pociones en Pokémon debilitados");
        }
    }

    public Pokemon getPokemonPorNombre(String nombre) {
        for (Pokemon p : equipoPokemon) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }
}