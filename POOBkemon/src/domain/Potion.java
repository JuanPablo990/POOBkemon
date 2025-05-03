package domain;

public class Potion extends Item {
    private static final int PS_RECUPERADOS = 20;
    
    public Potion() {
        super("Potion");
    }
    
    @Override
    public void usar(Pokemon pokemon) {
        if (pokemon.estaDebilitado()) {
            throw new IllegalStateException("No se puede usar Potion en un Pokémon debilitado");
        }
        
        if (pokemon.getPs() == pokemon.getPsMaximos()) {
            throw new IllegalStateException("El Pokémon ya tiene todos sus PS al máximo");
        }
        
        pokemon.aumentarPS(PS_RECUPERADOS);
    }
}