package presentation;

import javax.swing.*;
import java.awt.*;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;

    public VentanaMovimientos() {
        super("Movimientos de POOBkemon");
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        fondoPanel = new FondoPanel("/resources/movimientos.gif");
        fondoPanel.setLayout(new BorderLayout());

        // Panel principal 2x3
        JPanel gridPrincipal = new JPanel(new GridLayout(2, 3, 10, 10));
        gridPrincipal.setOpaque(false);

        // Crear 6 celdas (2 filas x 3 columnas)
        for (int fila = 0; fila < 2; fila++) {
            for (int col = 0; col < 3; col++) {
                JPanel celda = new JPanel(new BorderLayout());
                celda.setOpaque(false);
                celda.setBorder(BorderFactory.createLineBorder(Color.WHITE));

                // --- Panel combinado: Imagen arriba + Botones abajo ---
                JPanel contenidoCelda = new JPanel();
                contenidoCelda.setLayout(new BorderLayout());
                contenidoCelda.setOpaque(false);

                // Parte de la imagen (arriba)
                JPanel panelImagen = new JPanel();
                panelImagen.setOpaque(false);
                panelImagen.setPreferredSize(new Dimension(0, 100)); // Altura reservada
                panelImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Para que veas el espacio reservado
                contenidoCelda.add(panelImagen, BorderLayout.NORTH);

                // Parte inferior: botones VERTICALES y GRANDES
                JPanel panelBotones = new JPanel(new GridBagLayout());
                panelBotones.setOpaque(false);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 10, 5, 10);
                gbc.weightx = 1;

                for (int i = 0; i < 4; i++) {
                    JButton boton = new JButton("Btn " + (i+1));
                    boton.setBackground(new Color(255, 255, 255, 100));
                    boton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    boton.setFocusable(false);
                    boton.setPreferredSize(new Dimension(0, 30));
                    panelBotones.add(boton, gbc);
                }

                contenidoCelda.add(panelBotones, BorderLayout.CENTER);

                celda.add(contenidoCelda, BorderLayout.CENTER);
                gridPrincipal.add(celda);
            }
        }

        // Panel para el botón Siguiente
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setPreferredSize(new Dimension(120, 40));
        panelBoton.add(btnSiguiente);

        // Agregar componentes al fondo
        fondoPanel.add(gridPrincipal, BorderLayout.CENTER);
        fondoPanel.add(panelBoton, BorderLayout.SOUTH);
        setContentPane(fondoPanel);
    }

    private void configurarVentana() {
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override protected void accionNuevo() {}
    @Override protected void accionAbrir() {}
    @Override protected void accionGuardar() {}
    @Override protected void accionExportar() {}
    @Override protected void accionImportar() {}

    public void mostrar() {
        revalidate();
        repaint();
        setVisible(true);
    }
}