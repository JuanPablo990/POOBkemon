package domain;

public class SuperPotion extends Item {
    private static final int PS_RECUPERADOS = 50;
    
    public SuperPotion() {
        super("Super Potion");
    }
    
    @Override
    public void usar(Pokemon pokemon) {
        if (pokemon.estaDebilitado()) {
            throw new IllegalStateException("No se puede usar Super Potion en un Pokémon debilitado");
        }
        
        if (pokemon.getPs() == pokemon.getPsMaximos()) {
            throw new IllegalStateException("El Pokémon ya tiene todos sus PS al máximo");
        }
        
        pokemon.aumentarPS(PS_RECUPERADOS);
    }
}