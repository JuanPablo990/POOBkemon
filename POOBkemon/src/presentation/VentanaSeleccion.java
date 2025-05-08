package presentation;

import domain.POOBkemon;
import domain.Entrenador;
import domain.Potion;
import domain.SuperPotion;
import domain.HyperPotion;
import domain.Revive;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VentanaSeleccion extends Ventana {
    // Componentes de la interfaz
    private FondoPanel fondoPanel;
    private JButton btnSiguiente;
    private JPanel panelContenedorPrincipal;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JPanel panelAbajoIzquierda;
    private JSpinner[] spinnersItems;
    private JButton[] botonesMatriz = new JButton[36];
    private JLabel lblEntrenador;
    
    // Flag para saber qué jugador está seleccionando
    private boolean esJugador1 = true;

    // Constantes de diseño
    private static final int ESPACIO_MATRIZ = 2;
    private static final int NUM_COLUMNAS_MATRIZ = 6;
    private static final int NUM_FILAS_MATRIZ = 6;
    private static final int PREF_ANCHO_BOTON_ITEM = 80;
    private static final int PREF_ALTO_BOTON_ITEM = 45;
    private static final int ESPACIO_BOTONES_ITEMS = 5;
    private static final int ALTO_BOTON_VOLVER = 35;    
    private static final int ANCHO_MIN_BOTON_VOLVER = 90;
    private static final int MARGEN_IZQUIERDO = 95;
    private static final int MIN_ANCHO_BOTON = 60;
    private static final int MAX_ANCHO_BOTON = 150;
    private static final int RELACION_ASPECTO = 5;
    
    // Constantes para selección
    private static final Color SELECTED_COLOR = new Color(0, 255, 0, 100);
    private static final int MAX_POKEMON_SELECTED = 6;
    private static final int MAX_POR_TIPO_POCION = 2;  // Máximo 2 de cada tipo de poción
    private static final int MAX_REVIVE_SELECTED = 1;  // Máximo 1 Revivir
    
    // Contadores de selección
    private int pokemonSelectedCount = 0;
    private Set<JButton> selectedPokemonButtons = new HashSet<>();
    private List<String> nombresPokemonSeleccionados = new ArrayList<>();
    
    // Almacenamiento para las selecciones de ambos jugadores
    private List<String> nombresPokemonJugador1 = new ArrayList<>();
    private List<String> nombresPokemonJugador2 = new ArrayList<>();
    
    // Constructor principal
    public VentanaSeleccion() {
        super("Selección de POOBkemon");
        this.esJugador1 = true;
        inicializarComponentes();
        configurarListeners();
        agregarResizeListener();
        actualizarEtiquetaEntrenador();
    }
    
    // Constructor con parámetro para indicar el jugador
    public VentanaSeleccion(boolean esJugador1) {
        super("Selección de POOBkemon");
        this.esJugador1 = esJugador1;
        inicializarComponentes();
        configurarListeners();
        agregarResizeListener();
        actualizarEtiquetaEntrenador();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        fondoPanel = new FondoPanel(PoobkemonGifs.FONDO_SELECCION);
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);

        // Panel contenedor principal
        panelContenedorPrincipal = new JPanel(new GridLayout(1, 2));
        panelContenedorPrincipal.setOpaque(false);
        fondoPanel.add(panelContenedorPrincipal, BorderLayout.CENTER);
        fondoPanel.setBorder(BorderFactory.createEmptyBorder(30, -95, 0, 0));

        // Panel izquierdo
        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setOpaque(false);
        panelContenedorPrincipal.add(panelIzquierdo);

        // Panel derecho (matriz y etiqueta)
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setOpaque(false);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        panelContenedorPrincipal.add(panelDerecho);

        // Etiqueta del entrenador en la parte superior derecha
        JPanel panelEtiquetaSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelEtiquetaSuperior.setOpaque(false);
        panelEtiquetaSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        lblEntrenador = new JLabel();
        lblEntrenador.setFont(new Font("Arial", Font.BOLD, 18));
        lblEntrenador.setForeground(Color.BLACK);
        panelEtiquetaSuperior.add(lblEntrenador);

        panelDerecho.add(panelEtiquetaSuperior, BorderLayout.NORTH);

        // Configuración de la matriz
        JPanel panelMatriz = new JPanel(new GridLayout(NUM_FILAS_MATRIZ, NUM_COLUMNAS_MATRIZ, ESPACIO_MATRIZ, ESPACIO_MATRIZ));
        panelMatriz.setOpaque(false);
        panelDerecho.add(panelMatriz, BorderLayout.CENTER);

        String[] nombresPokemon = PoobkemonGifs.POKEMON_IMAGES.keySet().toArray(new String[0]);
        Arrays.sort(nombresPokemon);

        for (int i = 0; i < botonesMatriz.length; i++) {
            JButton boton = crearBotonPokemon(i < nombresPokemon.length ? nombresPokemon[i] : null);
            botonesMatriz[i] = boton;
            panelMatriz.add(boton);
        }

        // Configuración de los spinners de items
        JPanel contenedorBotones = new JPanel(new BorderLayout());
        contenedorBotones.setOpaque(false);
        contenedorBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        panelAbajoIzquierda = new JPanel(new GridBagLayout());
        panelAbajoIzquierda.setOpaque(false);
        panelAbajoIzquierda.setBorder(BorderFactory.createEmptyBorder(20, MARGEN_IZQUIERDO, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        String[] textosItems = {"Poción", "Hiperpoción", "Superpoción", "Revivir"};
        spinnersItems = new JSpinner[4];

        // Crear spinners para cada tipo de ítem
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        for (int i = 0; i < 3; i++) {
            JPanel itemPanel = crearPanelItem(textosItems[i]);
            spinnersItems[i] = (JSpinner) itemPanel.getClientProperty("spinner");
            
            gbc.gridx = i;
            gbc.insets.left = (i == 0) ? 0 : 4;
            panelAbajoIzquierda.add(itemPanel, gbc);
        }

        // Panel para Revivir (fila inferior)
        JPanel revivePanel = crearPanelItem(textosItems[3]);
        spinnersItems[3] = (JSpinner) revivePanel.getClientProperty("spinner");
        
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(-4, 0, 0, 0);
        panelAbajoIzquierda.add(revivePanel, gbc);

        contenedorBotones.add(panelAbajoIzquierda, BorderLayout.CENTER);
        panelIzquierdo.add(contenedorBotones, BorderLayout.SOUTH);

        // Panel inferior con botón Siguiente
        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.setOpaque(false);

        JPanel panelBotonSiguiente = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonSiguiente.setOpaque(false);
        btnSiguiente = crearBotonSiguiente("Siguiente");
        panelBotonSiguiente.add(btnSiguiente);
        panelAbajo.add(panelBotonSiguiente, BorderLayout.EAST);

        fondoPanel.add(panelAbajo, BorderLayout.SOUTH);
        
        // Configurar listeners para los spinners
        configurarListenersSpinners();
    }

private void configurarListenersSpinners() {
    for (JSpinner spinner : spinnersItems) {
        spinner.addChangeListener(e -> {
            JPanel itemPanel = (JPanel) spinner.getParent();
            String tipoItem = (String) itemPanel.getClientProperty("tipoItem");
            int nuevoValor = (int) spinner.getValue();

            // 1. Primero validar Revivir
            if (tipoItem.equals("Revivir") && nuevoValor > 1) {
                spinner.setValue(0);
                JOptionPane.showMessageDialog(this,
                    "Solo puedes llevar 1 Revivir como máximo",
                    "Límite excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Validar máximo 2 por tipo de poción
            if (!tipoItem.equals("Revivir") && nuevoValor > 2) {
                spinner.setValue(0);
                JOptionPane.showMessageDialog(this,
                    "Máximo 2 " + tipoItem + " por tipo",
                    "Límite excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Validar máximo 2 TIPOS de pociones diferentes
            int tiposPocionesUsados = contarTiposPocionesSeleccionados();
            if (!tipoItem.equals("Revivir") && nuevoValor > 0 && tiposPocionesUsados > 2) {
                spinner.setValue(0);
                JOptionPane.showMessageDialog(this,
                    "Solo puedes seleccionar 2 tipos diferentes de pociones\n" +
                    "(Ej: Poción y Superpoción, o Hiperpoción y Poción, etc.)",
                    "Límite de tipos", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}

// Método auxiliar para contar tipos de pociones seleccionados
private int contarTiposPocionesSeleccionados() {
    int count = 0;
    for (JSpinner spinner : spinnersItems) {
        JPanel panel = (JPanel) spinner.getParent();
        String tipoItem = (String) panel.getClientProperty("tipoItem");
        if (!tipoItem.equals("Revivir") && (int) spinner.getValue() > 0) {
            count++;
        }
    }
    return count;
}

    private JPanel crearPanelItem(String nombreItem) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        try {
            // Crear el icono del item
            String rutaImagen = PoobkemonGifs.getItemImage(nombreItem);
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
            Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(PREF_ANCHO_BOTON_ITEM - 20, PREF_ALTO_BOTON_ITEM - 15, Image.SCALE_SMOOTH);
            JLabel iconoLabel = new JLabel(new ImageIcon(imagenEscalada));
            iconoLabel.setToolTipText(nombreItem);
            
            // Crear el spinner con el límite correspondiente
            int maximo = nombreItem.equals("Revivir") ? MAX_REVIVE_SELECTED : MAX_POR_TIPO_POCION;
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, maximo, 1);
            JSpinner spinner = new JSpinner(spinnerModel);
            
            // Configurar el editor del spinner para que sea más compacto
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            editor.getTextField().setColumns(2);
            editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
            
            // Añadir componentes al panel
            panel.add(iconoLabel, BorderLayout.CENTER);
            panel.add(spinner, BorderLayout.SOUTH);
            
            // Guardar referencia al spinner en el panel
            panel.putClientProperty("spinner", spinner);
            panel.putClientProperty("tipoItem", nombreItem);
            
            return panel;
        } catch (Exception e) {
            // En caso de error, mostrar una versión simplificada
            JPanel errorPanel = new JPanel(new BorderLayout());
            errorPanel.setOpaque(false);
            
            JLabel label = new JLabel(nombreItem);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            
            int maximo = nombreItem.equals("Revivir") ? MAX_REVIVE_SELECTED : MAX_POR_TIPO_POCION;
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, maximo, 1);
            JSpinner spinner = new JSpinner(spinnerModel);
            
            errorPanel.add(label, BorderLayout.CENTER);
            errorPanel.add(spinner, BorderLayout.SOUTH);
            
            errorPanel.putClientProperty("spinner", spinner);
            errorPanel.putClientProperty("tipoItem", nombreItem);
            
            return errorPanel;
        }
    }

    private void actualizarEtiquetaEntrenador() {
        if (esJugador1) {
            String nombreJugador1 = POOBkemonGUI.getJugador1() != null ? POOBkemonGUI.getJugador1().getNombre() : "Jugador 1";
            lblEntrenador.setText("Selección para: " + nombreJugador1);
        } else {
            String nombreJugador2 = POOBkemonGUI.getJugador2() != null ? POOBkemonGUI.getJugador2().getNombre() : "Jugador 2";
            lblEntrenador.setText("Selección para: " + nombreJugador2);
        }
    }

    private JButton crearBotonPokemon(String nombrePokemon) {
        JButton boton = new JButton();
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        
        if (nombrePokemon != null) {
            try {
                String rutaImagen = PoobkemonGifs.getPokemonImage(nombrePokemon);
                URL url = getClass().getResource(rutaImagen);
                if (url != null) {
                    ImageIcon icono = new ImageIcon(url);
                    boton.setIcon(icono);
                    boton.setToolTipText(nombrePokemon);
                } else {
                    mostrarBotonError(boton, "No encontrado");
                }
            } catch (Exception e) {
                mostrarBotonError(boton, "Error");
            }
        } else {
            mostrarBotonError(boton, "No disponible");
        }
        
        return boton;
    }
    
    private void mostrarBotonError(JButton boton, String mensaje) {
        boton.setText(mensaje);
        boton.setForeground(Color.RED);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
    }

    private JButton crearBotonSiguiente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(70, 180, 130));
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
        redimensionarSpinnersItems();
        revalidate();
        repaint();
    }

    private void redimensionarBotonesMatriz() {
        if (panelDerecho != null) {
            int anchoPanel = panelDerecho.getWidth();
            int altoPanel = panelDerecho.getHeight();

            int anchoBoton = Math.min(anchoPanel / NUM_COLUMNAS_MATRIZ, altoPanel / NUM_FILAS_MATRIZ) - ESPACIO_MATRIZ * 2;
            anchoBoton = Math.max(40, anchoBoton);

            Dimension tamBoton = new Dimension(anchoBoton, anchoBoton);
            for (JButton boton : botonesMatriz) {
                boton.setPreferredSize(tamBoton);
                
                if (boton.getIcon() instanceof ImageIcon) {
                    ImageIcon originalIcon = (ImageIcon) boton.getIcon();
                    Image originalImage = originalIcon.getImage();
                    
                    Image scaledImage = originalImage.getScaledInstance(
                        anchoBoton - 4, anchoBoton - 4, Image.SCALE_DEFAULT);
                    
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    scaledIcon.setImageObserver(boton);
                    
                    boton.setIcon(scaledIcon);
                }
            }
        }
    }
    
    private void redimensionarSpinnersItems() {
        if (panelIzquierdo != null && spinnersItems != null && spinnersItems.length > 0) {
            int anchoDisponible = panelIzquierdo.getWidth() - MARGEN_IZQUIERDO;
            int espacioEntreItems = ESPACIO_BOTONES_ITEMS * (spinnersItems.length - 1);
            int anchoPorItem = (anchoDisponible - espacioEntreItems) / spinnersItems.length;
            anchoPorItem = Math.max(MIN_ANCHO_BOTON, Math.min(anchoPorItem, MAX_ANCHO_BOTON));
            int altoPorItem = (anchoPorItem * 3) / RELACION_ASPECTO;
            
            for (Component comp : panelAbajoIzquierda.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel itemPanel = (JPanel) comp;
                    for (Component subComp : itemPanel.getComponents()) {
                        if (subComp instanceof JLabel) {
                            // Redimensionar el icono
                            JLabel iconoLabel = (JLabel) subComp;
                            if (iconoLabel.getIcon() instanceof ImageIcon) {
                                ImageIcon icono = (ImageIcon) iconoLabel.getIcon();
                                Image img = icono.getImage()
                                    .getScaledInstance(anchoPorItem-15, altoPorItem-30, Image.SCALE_SMOOTH);
                                iconoLabel.setIcon(new ImageIcon(img));
                            }
                        }
                    }
                    itemPanel.setPreferredSize(new Dimension(anchoPorItem, altoPorItem));
                }
            }
        }
    }

    private void configurarListeners() {
        btnSiguiente.addActionListener(e -> {
            if (pokemonSelectedCount < 1) {
                JOptionPane.showMessageDialog(this,
                    "Debes seleccionar al menos 1 Pokémon para continuar",
                    "Selección incompleta", JOptionPane.WARNING_MESSAGE);
                return;
            }

            POOBkemon juego = POOBkemonGUI.getPoobkemon();

            if (esJugador1) {
                nombresPokemonJugador1.clear();
                nombresPokemonJugador1.addAll(nombresPokemonSeleccionados);

                Entrenador entrenador1 = POOBkemonGUI.getJugador1();

                for (String nombre : nombresPokemonSeleccionados) {
                    entrenador1.agregarPokemonPorNombre(nombre);
                }

                // Procesar los ítems seleccionados
                for (JSpinner spinner : spinnersItems) {
                    int cantidad = (int) spinner.getValue();
                    if (cantidad > 0) {
                        String tipoItem = (String) ((JPanel) spinner.getParent()).getClientProperty("tipoItem");
                        for (int i = 0; i < cantidad; i++) {
                            switch (tipoItem) {
                                case "Poción" -> entrenador1.agregarItem(new Potion());
                                case "Superpoción" -> entrenador1.agregarItem(new SuperPotion());
                                case "Hiperpoción" -> entrenador1.agregarItem(new HyperPotion());
                                case "Revivir" -> entrenador1.agregarItem(new Revive());
                            }
                        }
                    }
                }

                this.dispose();
                POOBkemonGUI.mostrarVentanaMovimientos(nombresPokemonSeleccionados);
            } else {
                nombresPokemonJugador2.clear();
                nombresPokemonJugador2.addAll(nombresPokemonSeleccionados);

                Entrenador entrenador2 = POOBkemonGUI.getJugador2();

                for (String nombre : nombresPokemonSeleccionados) {
                    entrenador2.agregarPokemonPorNombre(nombre);
                }

                // Procesar los ítems seleccionados
                for (JSpinner spinner : spinnersItems) {
                    int cantidad = (int) spinner.getValue();
                    if (cantidad > 0) {
                        String tipoItem = (String) ((JPanel) spinner.getParent()).getClientProperty("tipoItem");
                        for (int i = 0; i < cantidad; i++) {
                            switch (tipoItem) {
                                case "Poción" -> entrenador2.agregarItem(new Potion());
                                case "Superpoción" -> entrenador2.agregarItem(new SuperPotion());
                                case "Hiperpoción" -> entrenador2.agregarItem(new HyperPotion());
                                case "Revivir" -> entrenador2.agregarItem(new Revive());
                            }
                        }
                    }
                }

                this.dispose();
                POOBkemonGUI.mostrarVentanaMovimientos(nombresPokemonSeleccionados);
            }
        });

        for (JButton boton : botonesMatriz) {
            boton.addActionListener(e -> manejarSeleccionPokemon((JButton) e.getSource()));
        }
    }
    
    private void manejarSeleccionPokemon(JButton boton) {
        String nombrePokemon = boton.getToolTipText();
        if (nombrePokemon == null || nombrePokemon.equals("No disponible") || 
            nombrePokemon.equals("No encontrado") || nombrePokemon.equals("Error")) {
            return;
        }
        
        if (selectedPokemonButtons.contains(boton)) {
            // Deseleccionar
            boton.setBackground(null);
            boton.setOpaque(false);
            selectedPokemonButtons.remove(boton);
            pokemonSelectedCount--;
            nombresPokemonSeleccionados.remove(nombrePokemon);
        } else {
            // Verificar límite máximo
            if (pokemonSelectedCount >= MAX_POKEMON_SELECTED) {
                JOptionPane.showMessageDialog(this,
                    "Puedes seleccionar máximo " + MAX_POKEMON_SELECTED + " Pokémon",
                    "Límite alcanzado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Seleccionar
            boton.setBackground(SELECTED_COLOR);
            boton.setOpaque(true);
            selectedPokemonButtons.add(boton);
            pokemonSelectedCount++;
            nombresPokemonSeleccionados.add(nombrePokemon);
        }
        boton.repaint();
    }

    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nueva selección de Pokémon iniciada");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir selección de Pokémon", new String[]{"pok"}, "Archivos de Pokémon (*.pok)",
                e -> JOptionPane.showMessageDialog(this, "Selección de Pokémon cargada"));
    }

    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar selección de Pokémon", new String[]{"pok"}, "Archivos de Pokémon (*.pok)",
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
            redimensionarSpinnersItems();
        });
    }
}