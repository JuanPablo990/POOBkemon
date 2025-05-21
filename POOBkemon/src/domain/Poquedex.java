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
        registrarPokemon("Pikachu", Pikachu.class);
    }

    private void registrarMovimientosBase() {
        registrarMovimiento(new AerialAce());
        registrarMovimiento(new AirCutter());
        registrarMovimiento(new AirSlash());
        registrarMovimiento(new Amnesia());
        registrarMovimiento(new AquaJet());
        registrarMovimiento(new AquaTail());
        registrarMovimiento(new AuroraBeam());
        registrarMovimiento(new Barrier());
        registrarMovimiento(new BlazeKick());
        registrarMovimiento(new BodySlam());
        registrarMovimiento(new BraveBird());
        registrarMovimiento(new BubbleBeam());
        registrarMovimiento(new BulkUp());
        registrarMovimiento(new BulletPunch());
        registrarMovimiento(new BulletSeed());
        registrarMovimiento(new CalmMind());
        registrarMovimiento(new ChargeBeam());
        registrarMovimiento(new Charm());
        registrarMovimiento(new CloseCombat());
        registrarMovimiento(new Counter());
        registrarMovimiento(new Crabhammer());
        registrarMovimiento(new CrushClaw());
        registrarMovimiento(new DragonBreath());
        registrarMovimiento(new Explosion());
        registrarMovimiento(new ExtremeSpeed());
        registrarMovimiento(new FakeOut());
        registrarMovimiento(new Flail());
        registrarMovimiento(new FlashCannon());
        registrarMovimiento(new GigaDrain());
        registrarMovimiento(new HeadSmash());
        registrarMovimiento(new Headbutt());
        registrarMovimiento(new HighJumpKick());
        registrarMovimiento(new Howl());
        registrarMovimiento(new HydroPump());
        registrarMovimiento(new IceBall());
        registrarMovimiento(new IceShard());
        registrarMovimiento(new IcyWind());
        registrarMovimiento(new IronHead());
        registrarMovimiento(new KnockOff());
        registrarMovimiento(new LavaPlume());
        registrarMovimiento(new LeechLife());
        registrarMovimiento(new LeechSeed());
        registrarMovimiento(new MagicCoat());
        registrarMovimiento(new Megahorn());
        registrarMovimiento(new MetalClaw());
        registrarMovimiento(new MeteorMash());
        registrarMovimiento(new MirrorCoat());
        registrarMovimiento(new MirrorShot());
        registrarMovimiento(new Moonblast());
        registrarMovimiento(new NightSlash());
        registrarMovimiento(new PainSplit());
        registrarMovimiento(new PoisonFang());
        registrarMovimiento(new PsychoCut());
        registrarMovimiento(new RockSlide());
        registrarMovimiento(new RockThrow());
        registrarMovimiento(new RockTomb());
        registrarMovimiento(new Roost());
        registrarMovimiento(new Safeguard());
        registrarMovimiento(new Sandstorm());
        registrarMovimiento(new SeedBomb());
        registrarMovimiento(new SelfDestruct());
        registrarMovimiento(new ShadowClaw());
        registrarMovimiento(new ShadowSneak());
        registrarMovimiento(new ShellSmash());
        registrarMovimiento(new SignalBeam());
        registrarMovimiento(new SludgeBomb());
        registrarMovimiento(new SludgeWave());
        registrarMovimiento(new SolarBeam());
        registrarMovimiento(new Splash());
        registrarMovimiento(new Spore());
        registrarMovimiento(new SteelWing());
        registrarMovimiento(new StoneEdge());
        registrarMovimiento(new SuckerPunch());
        registrarMovimiento(new Superpower());
        registrarMovimiento(new Synthesis());
        registrarMovimiento(new Tackle());
        registrarMovimiento(new ThunderFang());
        registrarMovimiento(new ThunderWave());
        registrarMovimiento(new Toxic());
        registrarMovimiento(new ToxicSpikes());
        registrarMovimiento(new UTurn());
        registrarMovimiento(new WaterGun());
        registrarMovimiento(new WillOWisp());
        registrarMovimiento(new XScissor());
        registrarMovimiento(new Yawn());
        registrarMovimiento(new ZapCannon());
        registrarMovimiento(new ZenHeadbutt());
        registrarMovimiento(new SubirAtaque());
        registrarMovimiento(new BajarAtaque());
        registrarMovimiento(new SubirDefensa());
        registrarMovimiento(new BajarDefensa());
        registrarMovimiento(new SubirVelocidad());
        registrarMovimiento(new BajarVelocidad());
        registrarMovimiento(new SubirAtaqueEspecial());
        registrarMovimiento(new BajarAtaqueEspecial());
        registrarMovimiento(new SubirDefensaEspecial());
        registrarMovimiento(new BajarDefensaEspecial()); 
    }

    public void registrarPokemon(String nombre, Class<? extends Pokemon> clasePokemon) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (clasePokemon == null) {
            throw new IllegalArgumentException("La clase de Pokémon no puede ser nula");
        }
        // Guardar el nombre original para mantener la visualización correcta
        pokemonesDisponibles.put(nombre.toLowerCase(), clasePokemon);
    }

    public void registrarMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento no puede ser nulo");
        }
        // Guardar el nombre en minúsculas como clave
        movimientosDisponibles.put(movimiento.getNombre().toLowerCase(), movimiento);
    }

    public void eliminarPokemonDisponible(String nombre) {
        if (nombre != null) {
            pokemonesDisponibles.remove(nombre.toLowerCase());
        }
    }

    public void eliminarMovimientoDisponible(String nombre) {
        if (nombre != null) {
            movimientosDisponibles.remove(nombre.toLowerCase());
        }
    }

    public Pokemon crearPokemon(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre del Pokémon no puede ser nulo");
        }
        
        // Buscar directamente usando el nombre en minúsculas
        Class<? extends Pokemon> pokemonClass = pokemonesDisponibles.get(nombre.toLowerCase());
        
        if (pokemonClass != null) {
            try {
                return pokemonClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el Pokémon: " + e.getMessage(), e);
            }
        }
        
        throw new IllegalArgumentException("El Pokémon " + nombre + " no existe en la Poquedex");
    }

    public Movimiento crearMovimiento(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre del movimiento no puede ser nulo");
        }
        
        // Buscar directamente usando el nombre en minúsculas
        Movimiento original = movimientosDisponibles.get(nombre.toLowerCase());
        
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
        if (nombre == null) {
            return false;
        }
        return pokemonesDisponibles.containsKey(nombre.toLowerCase());
    }

    public boolean existeMovimiento(String nombre) {
        if (nombre == null) {
            return false;
        }
        return movimientosDisponibles.containsKey(nombre.toLowerCase());
    }
}