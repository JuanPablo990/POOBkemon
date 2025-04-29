package presentation;

import java.util.HashMap;
import java.util.Map;

public class PoobkemonGifs {
    // Mapa de nombres de Pokémon a sus rutas de imagen
    public static final Map<String, String> POKEMON_IMAGES = new HashMap<>();
    
    // Mapa de nombres de ítems a sus rutas de imagen
    public static final Map<String, String> ITEM_IMAGES = new HashMap<>();
    
    // Rutas de fondos
    public static final String FONDO_SELECCION = "/resources/seleccion.jpg";
    public static final String FONDO_MOVIMIENTOS = "/resources/movimientos.gif";

    static {
        // Pokémon
        POKEMON_IMAGES.put("Aron", "/resources/Pokemones/Acero/Aron.gif");
        POKEMON_IMAGES.put("Metangross", "/resources/Pokemones/Acero/Metangross.gif");
        POKEMON_IMAGES.put("corphish", "/resources/Pokemones/Agua/corphish.gif");
        POKEMON_IMAGES.put("magikarp", "/resources/Pokemones/Agua/magikarp.gif");
        POKEMON_IMAGES.put("Scizor", "/resources/Pokemones/Bicho/Scizor.gif");
        POKEMON_IMAGES.put("Shedinja", "/resources/Pokemones/Bicho/Shedinja.gif");
        POKEMON_IMAGES.put("Dratini", "/resources/Pokemones/Dragon/Dratini.gif");
        POKEMON_IMAGES.put("Trapinch", "/resources/Pokemones/Dragon/Trapinch.gif");
        POKEMON_IMAGES.put("Magnemite", "/resources/Pokemones/Electrico/Magnemite.gif");
        POKEMON_IMAGES.put("Voltorb", "/resources/Pokemones/Electrico/Voltorb.gif");
        POKEMON_IMAGES.put("Duskull", "/resources/Pokemones/Fantasma/Duskull.gif");
        POKEMON_IMAGES.put("Shuppet", "/resources/Pokemones/Fantasma/Shuppet.gif");
        POKEMON_IMAGES.put("Torchic", "/resources/Pokemones/Fuego/Torchic.gif");
        POKEMON_IMAGES.put("Torjoal", "/resources/Pokemones/Fuego/Torjoal.gif");
        POKEMON_IMAGES.put("Azurill", "/resources/Pokemones/Hada/Azurill.gif");
        POKEMON_IMAGES.put("Gardevoir", "/resources/Pokemones/Hada/Gardevoir.gif");
        POKEMON_IMAGES.put("Snorunt", "/resources/Pokemones/Hielo/Snorunt.gif");
        POKEMON_IMAGES.put("Spheal", "/resources/Pokemones/Hielo/Spheal.gif");
        POKEMON_IMAGES.put("Heracross", "/resources/Pokemones/Lucha/Heracross.gif");
        POKEMON_IMAGES.put("Hitmonlee", "/resources/Pokemones/Lucha/Hitmonlee.gif");
        POKEMON_IMAGES.put("Skitty", "/resources/Pokemones/Normal/Skitty.gif");
        POKEMON_IMAGES.put("Zangoose", "/resources/Pokemones/Normal/Zangoose.gif");
        POKEMON_IMAGES.put("shroomish", "/resources/Pokemones/Planta/shroomish.gif");
        POKEMON_IMAGES.put("tropius", "/resources/Pokemones/Planta/tropius.gif");
        POKEMON_IMAGES.put("Spoink", "/resources/Pokemones/Psiquico/Spoink.gif");
        POKEMON_IMAGES.put("Wobbuffet", "/resources/Pokemones/Psiquico/Wobbuffet.gif");
        POKEMON_IMAGES.put("Geodude", "/resources/Pokemones/Roca/Geodude.gif");
        POKEMON_IMAGES.put("Nosepass", "/resources/Pokemones/Roca/Nosepass.gif");
        POKEMON_IMAGES.put("Absol", "/resources/Pokemones/Siniestro/Absol.gif");
        POKEMON_IMAGES.put("Poochyena", "/resources/Pokemones/Siniestro/Poochyena.gif");
        POKEMON_IMAGES.put("Numel", "/resources/Pokemones/Tierra/Numel.gif");
        POKEMON_IMAGES.put("Sandshrew", "/resources/Pokemones/Tierra/Sandshrew.gif");
        POKEMON_IMAGES.put("Roselia", "/resources/Pokemones/Veneno/Roselia.gif");
        POKEMON_IMAGES.put("Tentacool", "/resources/Pokemones/Veneno/Tentacool.gif");
        POKEMON_IMAGES.put("Skarmory", "/resources/Pokemones/Volador/Skarmory.gif");
        POKEMON_IMAGES.put("Zubat", "/resources/Pokemones/Volador/Zubat.gif");

        // Ítems
        ITEM_IMAGES.put("Poción", "/resources/potion.png");
        ITEM_IMAGES.put("Hiperpoción", "/resources/hyperpotion.png");
        ITEM_IMAGES.put("Superpoción", "/resources/superpotion.png");
        ITEM_IMAGES.put("Revivir", "/resources/revivir.png");
    }

    public static String getPokemonImage(String pokemonName) {
        return POKEMON_IMAGES.get(pokemonName);
    }

    public static String getItemImage(String itemName) {
        return ITEM_IMAGES.get(itemName);
    }
}