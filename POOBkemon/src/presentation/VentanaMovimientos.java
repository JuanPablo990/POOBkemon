package presentation;

import javax.swing.*;
import java.awt.*;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;

    public VentanaMovimientos() {
        super("Movimientos de POOBkemon");
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        fondoPanel = new FondoPanel("/resources/movimientos.gif");
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);
    }

    @Override
    protected void accionNuevo() {
        // Implementación necesaria
    }

    @Override
    protected void accionAbrir() {
        // Implementación necesaria
    }

    @Override
    protected void accionGuardar() {
        // Implementación necesaria
    }

    @Override
    protected void accionExportar() {
        // Implementación necesaria
    }

    @Override
    protected void accionImportar() {
        // Implementación necesaria
    }

    public void mostrar() {
        setVisible(true);
    }
}