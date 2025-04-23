package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaSeleccion extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JPanel panelDerecha; // Panel para la cuadrícula 6x6

    public VentanaSeleccion() {
        super("Selección de POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        fondoPanel = new FondoPanel("/resources/seleccion.jpg");
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);

        panelDerecha = new JPanel();
        panelDerecha.setLayout(new GridLayout(6, 6, 5, 5)); // Espacio de 5px entre celdas
        panelDerecha.setOpaque(false);
        panelDerecha.setBorder(BorderFactory.createEmptyBorder(78, 11, 20, 49)); // Ajustado: bajado 7px más

        for (int i = 1; i <= 36; i++) {
            JButton botonCelda = new JButton();
            botonCelda.setPreferredSize(new Dimension(80, 80));
            botonCelda.setBackground(new Color(70, 130, 180, 150)); // Azul semitransparente
            botonCelda.setForeground(Color.WHITE);
            botonCelda.setFocusPainted(false);
            botonCelda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            botonCelda.setText(String.valueOf(i));
            panelDerecha.add(botonCelda);
        }

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelInferior.setOpaque(false);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(70, 130, 180)); // SteelBlue
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        panelInferior.add(btnVolver);

        fondoPanel.add(panelDerecha, BorderLayout.EAST); // Cuadrícula a la derecha
        fondoPanel.add(panelInferior, BorderLayout.SOUTH); // Botón volver abajo
    }


    private void configurarListeners() {
        btnVolver.addActionListener(e -> {
            POOBkemonGUI.mostrarVentanaOpciones();
            this.dispose();
        });
    }

    // Implementación de los métodos abstractos requeridos por Ventana
    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nueva selección de Pokémon iniciada");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir selección de Pokémon", 
                         new String[]{"pok"}, 
                         "Archivos de Pokémon (*.pok)",
                         e -> JOptionPane.showMessageDialog(this, "Selección de Pokémon cargada"));
    }

    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar selección de Pokémon", 
                         new String[]{"pok"}, 
                         "Archivos de Pokémon (*.pok)",
                         e -> JOptionPane.showMessageDialog(this, "Selección de Pokémon guardada"));
    }

    @Override
    protected void accionExportar() {
        JOptionPane.showMessageDialog(this, "Exportando selección de Pokémon...");
    }

    @Override
    protected void accionImportar() {
        JOptionPane.showMessageDialog(this, "Importando selección de Pokémon...");
    }

    public void mostrar() {
        setVisible(true);
    }
}