package presentation;

import java.util.HashMap;
import java.io.Serializable;
import java.util.Map;

/**
 * Clase utilitaria para gestionar las rutas de imágenes de los Pokémons e ítems en la aplicación.
 * Proporciona mapas estáticos y métodos para obtener los recursos de imagen por nombre.
 */
public class PoobkemonGifs implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final Map<String, String> POKEMON_IMAGES = new HashMap<>();
    public static final Map<String, String> ITEM_IMAGES = new HashMap<>();
    public static final String FONDO_SELECCION = "/resources/seleccion.jpg";
    public static final String FONDO_MOVIMIENTOS = "/resources/movimientos.gif";
    static {
        POKEMON_IMAGES.put("Aron", "/resources/Pokemones/Acero/Aron.gif");
        POKEMON_IMAGES.put("Metang", "/resources/Pokemones/Acero/Metangross.gif");
        POKEMON_IMAGES.put("Corphish", "/resources/Pokemones/Agua/corphish.gif");
        POKEMON_IMAGES.put("Magikarp", "/resources/Pokemones/Agua/magikarp.gif");
        POKEMON_IMAGES.put("Scizor", "/resources/Pokemones/Bicho/Scizor.gif");
        POKEMON_IMAGES.put("Shedinja", "/resources/Pokemones/Bicho/Shedinja.gif");
        POKEMON_IMAGES.put("Dratini", "/resources/Pokemones/Dragon/Dratini.gif");
        POKEMON_IMAGES.put("Trapinch", "/resources/Pokemones/Dragon/Trapinch.gif");
        POKEMON_IMAGES.put("Magnemite", "/resources/Pokemones/Electrico/Magnemite.gif");
        POKEMON_IMAGES.put("Voltorb", "/resources/Pokemones/Electrico/Voltorb.gif");
        POKEMON_IMAGES.put("Duskull", "/resources/Pokemones/Fantasma/Duskull.gif");
        POKEMON_IMAGES.put("Shuppet", "/resources/Pokemones/Fantasma/Shuppet.gif");
        POKEMON_IMAGES.put("Torchic", "/resources/Pokemones/Fuego/Torchic.gif");
        POKEMON_IMAGES.put("Torkoal", "/resources/Pokemones/Fuego/Torjoal.gif");
        POKEMON_IMAGES.put("Azurill", "/resources/Pokemones/Hada/Azurill.gif");
        POKEMON_IMAGES.put("Gardevoir", "/resources/Pokemones/Hada/Gardevoir.gif");
        POKEMON_IMAGES.put("Snorunt", "/resources/Pokemones/Hielo/Snorunt.gif");
        POKEMON_IMAGES.put("Spheal", "/resources/Pokemones/Hielo/Spheal.gif");
        POKEMON_IMAGES.put("Heracross", "/resources/Pokemones/Lucha/Heracross.gif");
        POKEMON_IMAGES.put("Hitmonlee", "/resources/Pokemones/Lucha/Hitmonlee.gif");
        POKEMON_IMAGES.put("Skitty", "/resources/Pokemones/Normal/Skitty.gif");
        POKEMON_IMAGES.put("Zangoose", "/resources/Pokemones/Normal/Zangoose.gif");
        POKEMON_IMAGES.put("Shroomish", "/resources/Pokemones/Planta/shroomish.gif");
        POKEMON_IMAGES.put("Tropius", "/resources/Pokemones/Planta/tropius.gif");
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
        POKEMON_IMAGES.put("Pikachu","/resources/Pokemones/Pikachu.gif");
        ITEM_IMAGES.put("Poción", "/resources/potion.png");
        ITEM_IMAGES.put("Hiperpoción", "/resources/hyperpotion.png");
        ITEM_IMAGES.put("Superpoción", "/resources/superpotion.png");
        ITEM_IMAGES.put("Revivir", "/resources/revivir.png");
    }

    /**
     * Obtiene la ruta de la imagen para un Pokémon dado, ignorando mayúsculas y minúsculas.
     * @param pokemonName el nombre del Pokémon
     * @return la ruta de la imagen si se encuentra, de lo contrario null
     */
    public static String getPokemonImage(String pokemonName) {
        for (Map.Entry<String, String> entry : POKEMON_IMAGES.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(pokemonName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Obtiene la ruta de la imagen para un ítem dado.
     * @param itemName el nombre del ítem
     * @return la ruta de la imagen si se encuentra, de lo contrario null
     */
    public static String getItemImage(String itemName) {
        return ITEM_IMAGES.get(itemName);
    }
}
