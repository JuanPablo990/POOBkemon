package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import domain.items.HyperPotion;
import domain.items.Potion;
import domain.items.Revive;
import domain.items.SuperPotion;

    /**
     * Clase que representa a un entrenador de Pokémon.
     * Contiene un nombre, un equipo de Pokémon y una mochila de items.
     */
public class Entrenador {
    private final String nombre;
    private final List<Pokemon> equipoPokemon;
    private final List<Item> mochilaItems;
    private Pokemon pokemonActivo;

    /**
	 * Constructor de la clase Entrenador.
	 * @param nombre Nombre del entrenador.
	 */
    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipoPokemon = new ArrayList<>(6);
        this.mochilaItems = new ArrayList<>();
        this.pokemonActivo = null;
    }

    /**
     * Genera un equipo de Pokémon aleatorio con una cantidad específica de Pokémon.
     * @param cantidad
     */
    public void generarEquipoAleatorio(int cantidad) {
        if (cantidad < 1 || cantidad > 6) {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 6");
        }
        equipoPokemon.clear();
        pokemonActivo = null;
        List<String> nombresPokemon = obtenerListaPokemonDisponibles();
        Collections.shuffle(nombresPokemon);
        List<String> nombresMovimientos = obtenerListaMovimientosDisponibles();
        Collections.shuffle(nombresMovimientos);
        List<String> movimientosAsignados = new ArrayList<>();
        for (int i = 0; i < cantidad && i < nombresPokemon.size(); i++) {
            String nombrePokemon = nombresPokemon.get(i);
            Pokemon pokemon = Poquedex.getInstancia().crearPokemon(nombrePokemon);
            agregarPokemon(pokemon);
            List<String> movimientosPokemon = new ArrayList<>();
            for (int j = 0; j < 4 && j < nombresMovimientos.size(); j++) {
                String movimiento = nombresMovimientos.get((i * 4 + j) % nombresMovimientos.size());
                if (!movimientosAsignados.contains(movimiento)) {
                    movimientosPokemon.add(movimiento);
                    movimientosAsignados.add(movimiento);
                }
            }
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
	 * Genera un equipo de Pokémon aleatorio completo (6 Pokémon).
	 */
    public void generarEquipoAleatorioCompleto() {
        generarEquipoAleatorio(6);
    }

    /**
     * Genera un equipo de Pokémon aleatorio con una cantidad específica de Pokémon.
     * 	
     */
    public void darItemsAleatorios() {
        Random random = new Random();
        mochilaItems.clear(); 
        if (random.nextBoolean()) {
            mochilaItems.add(new Revive());
        }
        int cantidadPociones = random.nextInt(3);
        if (cantidadPociones > 0) {
            List<Class<? extends Item>> tiposPociones = new ArrayList<>();
            tiposPociones.add(Potion.class);
            tiposPociones.add(SuperPotion.class);
            tiposPociones.add(HyperPotion.class);
            Collections.shuffle(tiposPociones);
            List<Class<? extends Item>> tiposSeleccionados = tiposPociones.subList(0, 2);
            for (int i = 0; i < cantidadPociones; i++) {
                Class<? extends Item> tipoPocion = tiposSeleccionados.get(i % tiposSeleccionados.size());
                try {
                    if (tipoPocion == Potion.class) {
                        mochilaItems.add(new Potion());
                    } else if (tipoPocion == SuperPotion.class) {
                        mochilaItems.add(new SuperPotion());
                    } else if (tipoPocion == HyperPotion.class) {
                        mochilaItems.add(new HyperPotion());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
	 * Genera un equipo de Pokémon aleatorio completo (6 Pokémon).
	 * 
	 */
    public void agregarPokemon(Pokemon pokemon) {
        if (equipoPokemon.size() >= 6) {
            throw new IllegalStateException("El equipo ya tiene 6 Pokémon");
        }
        equipoPokemon.add(pokemon);
        if (pokemonActivo == null) {
            pokemonActivo = pokemon;
        }
    }

    /**
     * Establece el Pokémon activo del entrenador.
     * @param pokemon
     */
    public void setPokemonActivo(Pokemon pokemon) {
        if (!equipoPokemon.contains(pokemon)) {
            throw new IllegalArgumentException("El Pokémon no pertenece a este entrenador");
        }
        if (pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes seleccionar un Pokémon debilitado");
        }
        this.pokemonActivo = pokemon;
    }

    /**
	 * Establece el Pokémon activo del entrenador por su índice en el equipo.
	 * @param indice
	 */
    public void setPokemonActivo(int indice) {
        if (indice < 0 || indice >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        setPokemonActivo(equipoPokemon.get(indice));
    }

    /**
     * Obtiene el Pokémon activo del entrenador.
     * @return
     */
    public Pokemon getPokemonActivo() {
        return pokemonActivo;
    }

    /**
	 * Elimina un Pokémon del equipo del entrenador.
	 * @param indice Índice del Pokémon a eliminar.
	 */
    public void eliminarPokemon(int indice) {
        if (indice < 0 || indice >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        Pokemon eliminado = equipoPokemon.remove(indice);
        if (eliminado.equals(pokemonActivo)) {
            buscarNuevoPokemonActivo();
        }
    }

    /**
     * Asigna movimientos a un Pokémon específico en el equipo del entrenador.
     * @param indicePokemon
     * @param movimientos
     */
    public void asignarMovimientosAPokemon(int indicePokemon, List<Movimiento> movimientos) {
        if (indicePokemon < 0 || indicePokemon >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
        equipoPokemon.get(indicePokemon).asignarMovimientos(movimientos);
    }

    /**
	 * Agrega un item a la mochila del entrenador.
	 * @param item
	 */
    public void agregarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }
        mochilaItems.add(item);
    }

    /**
     * Usa un item de la mochila en un Pokémon específico.
     * @param indiceItem
     * @param indicePokemon
     * @return
     */
    public String usarItem(int indiceItem, int indicePokemon) {
        validarIndicesItemsYPokemon(indiceItem, indicePokemon);
        Item item = mochilaItems.get(indiceItem);
        Pokemon pokemon = equipoPokemon.get(indicePokemon);
        validarUsoItem(item, pokemon);
        item.usar(pokemon);
        mochilaItems.remove(indiceItem);
        return String.format("%s usado con éxito en %s", item.getNombre(), pokemon.getNombre());
    }

    /**
	 * Usa un item de la mochila en el Pokémon activo.
	 * @param indiceItem
	 * @return
	 */
    public Pokemon agregarPokemonPorNombre(String nombrePokemon) {
        Pokemon nuevo = Poquedex.getInstancia().crearPokemon(nombrePokemon);
        agregarPokemon(nuevo);
        return nuevo;
    }

    /**
     * Asigna movimientos a un Pokémon específico en el equipo del entrenador por nombre.
     * @param indicePokemon
     * @param nombresMovimientos
     */
    public void asignarMovimientosPorNombre(int indicePokemon, List<String> nombresMovimientos) {
        List<Movimiento> movimientos = new ArrayList<>();
        for (String nombre : nombresMovimientos) {
            movimientos.add(Poquedex.getInstancia().crearMovimiento(nombre));
        }
        asignarMovimientosAPokemon(indicePokemon, movimientos);
    }

    /**
	 * Asigna movimientos a un Pokémon específico en el equipo del entrenador por nombre.
	 * @param nombrePokemon
	 * @param nombresMovimientos
	 */
    public List<String> obtenerListaPokemonDisponibles() {
        return Poquedex.getInstancia().obtenerNombresPokemonDisponibles();
    }

    /**
	 * Obtiene una lista de nombres de movimientos disponibles.
	 * @return
	 */
    public List<String> obtenerListaMovimientosDisponibles() {
        return Poquedex.getInstancia().obtenerNombresMovimientosDisponibles();
    }

    /**
     * Obtiene el nombre del entrenador.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
	 * Obtiene el equipo de Pokémon del entrenador.
	 * @return
	 */
    public List<Pokemon> getEquipoPokemon() {
        return new ArrayList<>(equipoPokemon);
    }

    /**
     * Obtiene la mochila de items del entrenador.
     * @return
     */
    public List<Item> getMochilaItems() {
        return new ArrayList<>(mochilaItems);
    }

    /**
	 * Obtiene el Pokémon activo del entrenador.
	 * @return
	 */
    public List<Item> getItems() {
        return getMochilaItems();
    }

    /**
     * Verifica si el entrenador tiene Pokémon disponibles.
     * @return
     */
    public boolean tienePokemonDisponibles() {
        return equipoPokemon.stream().anyMatch(p -> !p.estaDebilitado());
    }

    /**
	 * Verifica si el entrenador tiene items en la mochila.
	 * @return
	 */
    @Override
    public String toString() {
        return String.format("Entrenador %s - Pokémon: %d/%d - Items: %d - Activo: %s",nombre, equipoPokemon.size(), 6, mochilaItems.size(),pokemonActivo != null ? pokemonActivo.getNombre() : "Ninguno");
    }

    /**
     * Busca un nuevo Pokémon activo en el equipo del entrenador.
     */
    private void buscarNuevoPokemonActivo() {
        pokemonActivo = equipoPokemon.stream()
            .filter(p -> !p.estaDebilitado())
            .findFirst()
            .orElse(null);
    }

    /**
	 * Valida los índices de los items y Pokémon.
	 * @param indiceItem
	 * @param indicePokemon
	 */
    private void validarIndicesItemsYPokemon(int indiceItem, int indicePokemon) {
        if (indiceItem < 0 || indiceItem >= mochilaItems.size()) {
            throw new IllegalArgumentException("Índice de item inválido");
        }
        if (indicePokemon < 0 || indicePokemon >= equipoPokemon.size()) {
            throw new IllegalArgumentException("Índice de Pokémon inválido");
        }
    }

    /**
     * 	Valida el uso de un item en un Pokémon.
     * @param item
     * @param pokemon
     */
    private void validarUsoItem(Item item, Pokemon pokemon) {
        if (item instanceof Revive && !pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes usar Revive en un Pokémon que no está debilitado");
        }
        if ((item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion)
            && pokemon.estaDebilitado()) {
            throw new IllegalStateException("No puedes usar pociones en Pokémon debilitados");
        }
    }

    /**
	 * Busca un Pokémon por su nombre en el equipo del entrenador.
	 * @param nombre
	 * @return
	 */
    public Pokemon getPokemonPorNombre(String nombre) {
        for (Pokemon p : equipoPokemon) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }
}