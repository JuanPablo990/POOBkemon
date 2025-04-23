package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaSeleccion extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JPanel panelContenedorPrincipal; // Panel principal que divide la ventana
    private JPanel panelIzquierdo; // Panel para contenido izquierdo
    private JPanel panelDerecho; // Panel para la matriz
    private JPanel panelAbajoIzquierda; // Para los botones de items
    private JButton[] botonesSuperiores;
    private JButton[] botonesMatriz = new JButton[36];

    // Constantes de diseño
    private static final int ESPACIO_MATRIZ = 2;
    private static final int NUM_COLUMNAS_MATRIZ = 6;
    private static final int NUM_FILAS_MATRIZ = 6;
    private static final int PREF_ANCHO_BOTON_ITEM = 100;
    private static final int PREF_ALTO_BOTON_ITEM = 60;
    private static final int ESPACIO_BOTONES_ITEMS = 5;
    private static final int ALTO_BOTON_VOLVER = 40;
    private static final int ANCHO_MIN_BOTON_VOLVER = 100;
    private static final int MARGEN_IZQUIERDO = 95;
    private static final int MIN_ANCHO_BOTON = 80;
    private static final int MAX_ANCHO_BOTON = 200;
    private static final int RELACION_ASPECTO = 5;

    public VentanaSeleccion() {
        super("Selección de POOBkemon");
        inicializarComponentes();
        configurarListeners();
        agregarResizeListener();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        fondoPanel = new FondoPanel("/resources/seleccion.jpg");
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);

        // Panel contenedor principal que divide la ventana en izquierda y derecha
        panelContenedorPrincipal = new JPanel(new GridLayout(1, 2));
        panelContenedorPrincipal.setOpaque(false);
        fondoPanel.add(panelContenedorPrincipal, BorderLayout.CENTER);
        fondoPanel.setBorder(BorderFactory.createEmptyBorder(30, -95, 0, 0));

        // Panel izquierdo (para otros componentes)
        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setOpaque(false);
        panelContenedorPrincipal.add(panelIzquierdo);

        // Panel derecho (para la matriz)
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setOpaque(false);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        panelContenedorPrincipal.add(panelDerecho);

        // Panel de la matriz (dentro del panel derecho)
        JPanel panelMatriz = new JPanel(new GridLayout(NUM_FILAS_MATRIZ, NUM_COLUMNAS_MATRIZ, ESPACIO_MATRIZ, ESPACIO_MATRIZ));
        panelMatriz.setOpaque(false);
        panelDerecho.add(panelMatriz, BorderLayout.CENTER);

        // Crear botones de la matriz
        for (int i = 0; i < botonesMatriz.length; i++) {
            JButton boton = crearBotonMatriz(i + 1);
            botonesMatriz[i] = boton;
            panelMatriz.add(boton);
        }

        // Panel para botones de items (Abajo - Izquierda)
        panelAbajoIzquierda = new JPanel(new GridBagLayout());
        panelAbajoIzquierda.setOpaque(false);
        panelAbajoIzquierda.setBorder(BorderFactory.createEmptyBorder(0, MARGEN_IZQUIERDO, 0, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        String[] textosBotonesItems = {"potion", "superpotion", "hiperpotion", "revivir"};
        botonesSuperiores = new JButton[textosBotonesItems.length];
        for (int i = 0; i < botonesSuperiores.length; i++) {
            JButton boton = crearBotonItem(textosBotonesItems[i]);
            botonesSuperiores[i] = boton;
            
            gbc.gridx = i;
            gbc.insets = new Insets(0, i == 0 ? 0 : ESPACIO_BOTONES_ITEMS, 0, 0);
            panelAbajoIzquierda.add(boton, gbc);
        }
        
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panelIzquierdo.add(panelAbajoIzquierda, BorderLayout.SOUTH);

        // Panel para organizar botones items y volver (Abajo)
        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.setOpaque(false);

        // Botón Volver (Abajo - Derecha)
        JPanel panelBotonVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonVolver.setOpaque(false);
        btnVolver = crearBotonVolver("Volver");
        panelBotonVolver.add(btnVolver);
        panelAbajo.add(panelBotonVolver, BorderLayout.EAST);

        fondoPanel.add(panelAbajo, BorderLayout.SOUTH);
    }

    // Métodos auxiliares para crear botones (sin cambios)
    private JButton crearBotonMatriz(int numero) {
        JButton boton = new JButton(String.valueOf(numero));
        boton.setBackground(new Color(70, 130, 180, 150));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return boton;
    }

    private JButton crearBotonItem(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(PREF_ANCHO_BOTON_ITEM, PREF_ALTO_BOTON_ITEM));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return boton;
    }

    private JButton crearBotonVolver(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(ANCHO_MIN_BOTON_VOLVER, ALTO_BOTON_VOLVER));
        return boton;
    }

    private void agregarResizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarComponentes();
            }
        });
    }

    private void redimensionarComponentes() {
        redimensionarBotonesMatriz();
        redimensionarBotonesItems();
        revalidate();
        repaint();
    }

    private void redimensionarBotonesMatriz() {
        if (panelDerecho != null) {
            int anchoPanel = panelDerecho.getWidth();
            int altoPanel = panelDerecho.getHeight();

            int anchoBoton = Math.min(anchoPanel / NUM_COLUMNAS_MATRIZ, altoPanel / NUM_FILAS_MATRIZ) - ESPACIO_MATRIZ * 2;
            anchoBoton = Math.max(40, anchoBoton); // Tamaño mínimo

            Dimension tamBoton = new Dimension(anchoBoton, anchoBoton);
            for (JButton boton : botonesMatriz) {
                boton.setPreferredSize(tamBoton);
            }
        }
    }
    
    private void redimensionarBotonesItems() {
        if (panelIzquierdo != null && botonesSuperiores != null && botonesSuperiores.length > 0) {
            // Calcular espacio disponible restando márgenes
            int anchoDisponible = panelIzquierdo.getWidth() - MARGEN_IZQUIERDO;
            
            // Calcular espacio total entre botones
            int espacioEntreBotones = ESPACIO_BOTONES_ITEMS * (botonesSuperiores.length - 1);
            
            // Calcular ancho por botón
            int anchoPorBoton = (anchoDisponible - espacioEntreBotones) / botonesSuperiores.length;
            
            // Aplicar límites
            anchoPorBoton = Math.max(MIN_ANCHO_BOTON, Math.min(anchoPorBoton, MAX_ANCHO_BOTON));
            
            // Calcular alto manteniendo relación de aspecto 5:3
            int altoPorBoton = (anchoPorBoton * 3) / RELACION_ASPECTO;
            
            Dimension nuevoTamano = new Dimension(anchoPorBoton, altoPorBoton);
            
            // Aplicar a todos los botones
            for (JButton boton : botonesSuperiores) {
                boton.setPreferredSize(nuevoTamano);
                boton.setMinimumSize(nuevoTamano);
                boton.setMaximumSize(nuevoTamano);
            }
            
            // Forzar actualización
            panelAbajoIzquierda.revalidate();
            panelAbajoIzquierda.repaint();
        }
    }



    private void configurarListeners() {
        btnVolver.addActionListener(e -> {
            POOBkemonGUI.mostrarVentanaOpciones();
            this.dispose();
        });

        for (int i = 0; i < botonesSuperiores.length; i++) {
            final int index = i;
            botonesSuperiores[i].addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Botón " + botonesSuperiores[index].getText() + " presionado");
            });
        }
    }

    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nueva selección de Pokémon iniciada");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir selección de Pokémon", new String[]{"pok"},
                "Archivos de Pokémon (*.pok)",
                e -> JOptionPane.showMessageDialog(this, "Selección de Pokémon cargada"));
    }

    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar selección de Pokémon", new String[]{"pok"},
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
        SwingUtilities.invokeLater(() -> {
            redimensionarBotonesMatriz();
            redimensionarBotonesItems();
        });
    }
}