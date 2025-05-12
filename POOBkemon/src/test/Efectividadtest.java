package test;

import static org.junit.jupiter.api.Assertions.*;

import domain.Efectividad;

import org.junit.jupiter.api.Test;

class Efectividadtest {

    @Test
    void felizEfectividadSuperefectivo() {
        assertEquals(2.0, Efectividad.calcular("fuego", "planta"));
    }

    @Test
    void felizEfectividadEfectivo() {
        assertEquals(0.5, Efectividad.calcular("fuego", "agua"));
    }

    @Test
    void felizEfectividadNeutral() {
        assertEquals(1.0, Efectividad.calcular("fuego", "psiquico"));
    }

    @Test
    void felizEfectividadInefectivo() {
        assertEquals(0.0, Efectividad.calcular("normal", "fantasma"));
    }

    @Test
    void noFelizTipoInexistente() {
        assertEquals(1.0, Efectividad.calcular("tipoinexistente", "fuego"));
    }

    @Test
    void noFelizDefensaInexistente() {
        assertEquals(1.0, Efectividad.calcular("fuego", "defensainexistente"));
    }
} 