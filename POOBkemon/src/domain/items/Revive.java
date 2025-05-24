package domain.items;

import domain.Item;
import domain.Pokemon;

public class Revive extends Item {
    public Revive() {
        super("Revive");
    }
    
    @Override
    public void usar(Pokemon pokemon) {
        if (!pokemon.estaDebilitado()) {
            throw new IllegalStateException("No se puede usar Revive en un Pokémon no debilitado");
        }
        
        int psRestaurados = pokemon.getPsMaximos() / 2;
        pokemon.aumentarPS(psRestaurados);
    }
}