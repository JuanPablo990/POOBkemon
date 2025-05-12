package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Movimientotest {

    private Movimiento movimiento;
    private Pokemon atacante;
    private Pokemon defensor;

    @BeforeEach
    void setUp() {
        movimiento = new AerialAce();
        atacante = new Zubat();
        defensor = new Aron();
    }

    @Test
    void testDatosBasicos() {
        assertEquals("Aerial Ace", movimiento.getNombre());
        assertEquals("volador", movimiento.getTipo().toLowerCase());
        assertTrue(movimiento.getPotencia() > 0);
        assertTrue(movimiento.getPrecision() > 0);
        assertTrue(movimiento.getPpMaximos() > 0);
    }

    @Test
    void testRestaurarPPCompleto() {
        movimiento.reducirPP(2);
        movimiento.restaurarPP();
        assertEquals(movimiento.getPpMaximos(), movimiento.getPp());
    }

    @Test
    void testRestaurarPPParcial() {
        movimiento.reducirPP(3);
        int restaurados = movimiento.restaurarPP(2);
        assertEquals(2, restaurados);
        assertEquals(movimiento.getPpMaximos() - 1, movimiento.getPp());
    }

    @Test
    void testReducirPP() {
        int inicial = movimiento.getPp();
        int reduccion = movimiento.reducirPP(2);
        assertEquals(2, reduccion);
        assertEquals(inicial - 2, movimiento.getPp());
    }

    @Test
    void testToStringNoEsNulo() {
        assertNotNull(movimiento.toString());
        assertTrue(movimiento.toString().contains(movimiento.getNombre()));
    }

    @Test
    void testPrioridadEsCorrecta() {
        int prioridad = movimiento.getPrioridad();
        assertTrue(prioridad >= -7 && prioridad <= 7);
    }

    @Test
    void testEjecutar_Feliz() {
        int psAntes = defensor.getPs();
        boolean resultado = movimiento.ejecutar(atacante, defensor, 1.0);
        assertTrue(resultado);
        assertTrue(defensor.getPs() < psAntes);
    }

    @Test
    void testEjecutar_NoFelizSinPP() {
        movimiento.reducirPP(movimiento.getPpMaximos());
        boolean resultado = movimiento.ejecutar(atacante, defensor, 1.0);
        assertFalse(resultado);
    }

    @Test
    void testEjecutar_NoFelizPorPrecision() {
        Movimiento fallo = new Movimiento("FalloSeguro", "normal", 10, 0, 5, 0) {
            @Override
            protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
            }
        };
        boolean resultado = fallo.ejecutar(atacante, defensor, 1.0);
        assertFalse(resultado);
    }
} 
