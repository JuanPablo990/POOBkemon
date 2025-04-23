package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaSeleccion extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JPanel panelDerecha;
    private JButton[] botonesSuperiores; // Array para los 4 botones nuevos

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

    // Panel para la cuadrícula 6x6 (derecha) - EXACTAMENTE IGUAL
    panelDerecha = new JPanel();
    panelDerecha.setLayout(new GridLayout(6, 6, 5, 5));
    panelDerecha.setOpaque(false);
    panelDerecha.setBorder(BorderFactory.createEmptyBorder(78, 11, 20, 49));

    for (int i = 1; i <= 36; i++) {
        JButton botonCelda = new JButton();
        botonCelda.setPreferredSize(new Dimension(80, 80));
        botonCelda.setBackground(new Color(70, 130, 180, 150));
        botonCelda.setForeground(Color.WHITE);
        botonCelda.setFocusPainted(false);
        botonCelda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        botonCelda.setText(String.valueOf(i));
        panelDerecha.add(botonCelda);
    }

    // Panel para los botones de items (horizontal, abajo a la izquierda)
    JPanel panelBotonesItems = new JPanel();
    panelBotonesItems.setOpaque(false);
    panelBotonesItems.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0)); // 10px espacio horizontal
    panelBotonesItems.setBorder(BorderFactory.createEmptyBorder(450, 20, 0, 0)); // Margen superior 450px

    // Configuración de los 4 botones (horizontal, 100x80)
    String[] textosBotones = {"potion", "superpotion", "hiperpotion", "revivir"};
    botonesSuperiores = new JButton[4];
    for (int i = 0; i < botonesSuperiores.length; i++) {
        botonesSuperiores[i] = new JButton(textosBotones[i]);
        botonesSuperiores[i].setPreferredSize(new Dimension(100, 80)); // Tamaño exacto
        botonesSuperiores[i].setBackground(new Color(70, 130, 180));
        botonesSuperiores[i].setForeground(Color.WHITE);
        botonesSuperiores[i].setFocusPainted(false);
        botonesSuperiores[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panelBotonesItems.add(botonesSuperiores[i]);
    }

    // Panel para el botón Volver (sin cambios)
    JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
    panelVolver.setOpaque(false);
    btnVolver = new JButton("Volver");
    btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
    btnVolver.setBackground(new Color(70, 130, 180));
    btnVolver.setForeground(Color.WHITE);
    btnVolver.setFocusPainted(false);
    btnVolver.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    panelVolver.add(btnVolver);

    // Panel contenedor para organizar los elementos izquierdos
    JPanel panelIzquierdoContenedor = new JPanel(new BorderLayout());
    panelIzquierdoContenedor.setOpaque(false);
    panelIzquierdoContenedor.add(panelBotonesItems, BorderLayout.SOUTH);

    // Agregar todos los paneles principales
    fondoPanel.add(panelIzquierdoContenedor, BorderLayout.WEST); // Botones items abajo a la izquierda
    fondoPanel.add(panelDerecha, BorderLayout.EAST);             // Cuadrícula derecha (sin cambios)
    fondoPanel.add(panelVolver, BorderLayout.SOUTH);             // Botón volver (sin cambios)
}
    
    private void configurarListeners() {
        // Listener para el botón Volver (sin cambios)
        btnVolver.addActionListener(e -> {
            POOBkemonGUI.mostrarVentanaOpciones();
            this.dispose();
        });

        // Listeners para los botones superiores (conservando tu implementación)
        for (int i = 0; i < botonesSuperiores.length; i++) {
            final int index = i;
            botonesSuperiores[i].addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Botón " + botonesSuperiores[index].getText() + " presionado");
                // Manteniendo tu funcionalidad existente
            });
        }
    }

    // Implementación de los métodos abstractos (sin cambios)
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