package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poquedex {
    private static Poquedex instancia;
    private Map<String, Class<? extends Pokemon>> pokemonesDisponibles;
    private Map<String, Movimiento> movimientosDisponibles;

    private Poquedex() {
        this.pokemonesDisponibles = new HashMap<>();
        this.movimientosDisponibles = new HashMap<>();
        registrarPokemonesBase();
        registrarMovimientosBase();
    }

    public static synchronized Poquedex getInstancia() {
        if (instancia == null) {
            instancia = new Poquedex();
        }
        return instancia;
    }

    private void registrarPokemonesBase() {
        registrarPokemon("Absol", Absol.class);
        registrarPokemon("Aron", Aron.class);
        registrarPokemon("Azurill", Azurill.class);
        registrarPokemon("Corphish", Corphish.class);
        registrarPokemon("Dratini", Dratini.class);
        registrarPokemon("Duskull", Duskull.class);
        registrarPokemon("Gardevoir", Gardevoir.class);
        registrarPokemon("Geodude", Geodude.class);
        registrarPokemon("Heracross", Heracross.class);
        registrarPokemon("Hitmonlee", Hitmonlee.class);
        registrarPokemon("Magikarp", Magikarp.class);
        registrarPokemon("Magnemite", Magnemite.class);
        registrarPokemon("Metang", Metang.class);
        registrarPokemon("Nosepass", Nosepass.class);
        registrarPokemon("Numel", Numel.class);
        registrarPokemon("Poochyena", Poochyena.class);
        registrarPokemon("Roselia", Roselia.class);
        registrarPokemon("Sandshrew", Sandshrew.class);
        registrarPokemon("Scizor", Scizor.class);
        registrarPokemon("Shedinja", Shedinja.class);
        registrarPokemon("Shroomish", Shroomish.class);
        registrarPokemon("Shuppet", Shuppet.class);
        registrarPokemon("Skitty", Skitty.class);
        registrarPokemon("Skarmory", Skarmory.class);
        registrarPokemon("Snorunt", Snorunt.class);
        registrarPokemon("Spheal", Spheal.class);
        registrarPokemon("Spoink", Spoink.class);
        registrarPokemon("Tentacool", Tentacool.class);
        registrarPokemon("Torchic", Torchic.class);
        registrarPokemon("Torkoal", Torkoal.class);
        registrarPokemon("Trapinch", Trapinch.class);
        registrarPokemon("Tropius", Tropius.class);
        registrarPokemon("Voltorb", Voltorb.class);
        registrarPokemon("Wobbuffet", Wobbuffet.class);
        registrarPokemon("Zangoose", Zangoose.class);
        registrarPokemon("Zubat", Zubat.class);
    }

    private void registrarMovimientosBase() {
        registrarMovimiento(new Splash());
        registrarMovimiento(new Tackle());
        registrarMovimiento(new HydroPump());
        registrarMovimiento(new Flail());

        registrarMovimiento(new KnockOff());
        registrarMovimiento(new MetalClaw());
        registrarMovimiento(new MeteorMash());
        registrarMovimiento(new RockTomb());
        registrarMovimiento(new SwordsDance());
        registrarMovimiento(new XScissor());
        registrarMovimiento(new ZenHeadbutt());
        registrarMovimiento(new AquaTail());
        registrarMovimiento(new ConfuseRay());
        registrarMovimiento(new DragonBreath());
        registrarMovimiento(new ExtremeSpeed());
        registrarMovimiento(new ShadowClaw());
        registrarMovimiento(new ThunderWave());
        registrarMovimiento(new UTurn());
        registrarMovimiento(new WillOWisp());
        registrarMovimiento(new AquaJet());
        registrarMovimiento(new BulletPunch());
        registrarMovimiento(new Crabhammer());
        registrarMovimiento(new DragonDance());
        registrarMovimiento(new Earthquake());
        registrarMovimiento(new HeadSmash());
        registrarMovimiento(new IronHead());
    }


    public void registrarPokemon(String nombre, Class<? extends Pokemon> clasePokemon) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (clasePokemon == null) {
            throw new IllegalArgumentException("La clase de Pokémon no puede ser nula");
        }
        pokemonesDisponibles.put(nombre, clasePokemon);
    }

    public void registrarMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento no puede ser nulo");
        }
        movimientosDisponibles.put(movimiento.getNombre(), movimiento);
    }

    public void eliminarPokemonDisponible(String nombre) {
        pokemonesDisponibles.remove(nombre);
    }

    public void eliminarMovimientoDisponible(String nombre) {
        movimientosDisponibles.remove(nombre);
    }

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

    public Movimiento crearMovimiento(String nombre) {
        Movimiento original = movimientosDisponibles.get(nombre);
        if (original == null) {
            throw new IllegalArgumentException("El movimiento " + nombre + " no existe en la Poquedex");
        }
        try {
            return original.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el movimiento: " + e.getMessage(), e);
        }
    }

    public List<String> obtenerNombresPokemonDisponibles() {
        return new ArrayList<>(pokemonesDisponibles.keySet());
    }

    public List<String> obtenerNombresMovimientosDisponibles() {
        return new ArrayList<>(movimientosDisponibles.keySet());
    }

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

    public List<String> obtenerMovimientosPorTipo(String tipo) {
        List<String> movimientosFiltrados = new ArrayList<>();
        for (Movimiento movimiento : movimientosDisponibles.values()) {
            if (movimiento.getTipo().equalsIgnoreCase(tipo)) {
                movimientosFiltrados.add(movimiento.getNombre());
            }
        }
        return movimientosFiltrados;
    }

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

    public List<String> obtenerTiposMovimientosDisponibles() {
        List<String> tipos = new ArrayList<>();
        for (Movimiento movimiento : movimientosDisponibles.values()) {
            if (!tipos.contains(movimiento.getTipo())) {
                tipos.add(movimiento.getTipo());
            }
        }
        return tipos;
    }

    public boolean existePokemon(String nombre) {
        return pokemonesDisponibles.containsKey(nombre);
    }

    public boolean existeMovimiento(String nombre) {
        return movimientosDisponibles.containsKey(nombre);
    }
}
