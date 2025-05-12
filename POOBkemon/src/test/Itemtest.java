package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Itemtest {

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Zubat();
    }

    @Test
    void felizPotion() {
        pokemon.reducirPS(30);
        Item pocion = new Potion();
        assertDoesNotThrow(() -> pocion.usar(pokemon));
        assertTrue(pokemon.getPs() > 0);
    }

    @Test
    void noFelizPotionFullPs() {
        Item pocion = new Potion();
        assertThrows(IllegalStateException.class, () -> pocion.usar(pokemon));
    }

    @Test
    void noFelizPotionDebilitado() {
        pokemon.reducirPS(pokemon.getPs());
        Item pocion = new Potion();
        assertThrows(IllegalStateException.class, () -> pocion.usar(pokemon));
    }

    @Test
    void felizSuperPotion() {
        pokemon.reducirPS(60);
        Item superPocion = new SuperPotion();
        assertDoesNotThrow(() -> superPocion.usar(pokemon));
    }

    @Test
    void felizHyperPotion() {
        pokemon.reducirPS(100);
        Item hyperPocion = new HyperPotion();
        assertDoesNotThrow(() -> hyperPocion.usar(pokemon));
    }

    @Test
    void felizRevive() {
        pokemon.reducirPS(pokemon.getPs());
        Item revive = new Revive();
        assertDoesNotThrow(() -> revive.usar(pokemon));
        assertTrue(pokemon.getPs() > 0);
    }

    @Test
    void noFelizReviveNoDebilitado() {
        Item revive = new Revive();
        assertThrows(IllegalStateException.class, () -> revive.usar(pokemon));
    }
} 
