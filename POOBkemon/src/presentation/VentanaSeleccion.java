package presentation;

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
    private JButton[] botonesSuperiores;
    private JButton[] botonesMatriz = new JButton[36];
    private JLabel lblEntrenador; // Nueva etiqueta para mostrar el nombre del entrenador actual
    
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
    private static final int MAX_POTIONS_SELECTED = 2;
    private static final int MAX_REVIVE_SELECTED = 1;
    
    // Contadores de selección
    private int pokemonSelectedCount = 0;
    private int potionsSelectedCount = 0;
    private int reviveSelectedCount = 0;
    private Set<JButton> selectedPokemonButtons = new HashSet<>();
    private Set<JButton> selectedItemButtons = new HashSet<>();
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
        
        // Etiqueta para mostrar el entrenador actual
        lblEntrenador = new JLabel();
        lblEntrenador.setFont(new Font("Arial", Font.BOLD, 18));
        lblEntrenador.setForeground(Color.WHITE);
        lblEntrenador.setHorizontalAlignment(SwingConstants.CENTER);
        lblEntrenador.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelIzquierdo.add(lblEntrenador, BorderLayout.NORTH);

        // Panel derecho (matriz)
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setOpaque(false);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        panelContenedorPrincipal.add(panelDerecho);

        // Configuración de la matriz
        JPanel panelMatriz = new JPanel(new GridLayout(NUM_FILAS_MATRIZ, NUM_COLUMNAS_MATRIZ, ESPACIO_MATRIZ, ESPACIO_MATRIZ));
        panelMatriz.setOpaque(false);
        panelDerecho.add(panelMatriz, BorderLayout.CENTER);

        // Obtener nombres de Pokémon ordenados
        String[] nombresPokemon = PoobkemonGifs.POKEMON_IMAGES.keySet().toArray(new String[0]);
        Arrays.sort(nombresPokemon);

        for (int i = 0; i < botonesMatriz.length; i++) {
            JButton boton = crearBotonPokemon(i < nombresPokemon.length ? nombresPokemon[i] : null);
            botonesMatriz[i] = boton;
            panelMatriz.add(boton);
        }

        // Configuración de los botones de items
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
        
        int anchoBoton = PREF_ANCHO_BOTON_ITEM - 10;
        int altoBoton = PREF_ALTO_BOTON_ITEM - 10;
        
        String[] textosBotones = {"Poción", "Hiperpoción", "Superpoción", "Revivir"};
        botonesSuperiores = new JButton[4];
        
        // Fila superior (3 botones)
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        for (int i = 0; i < 3; i++) {
            JButton boton = crearBotonItem(textosBotones[i]);
            boton.setPreferredSize(new Dimension(anchoBoton, altoBoton));
            botonesSuperiores[i] = boton;
            
            gbc.gridx = i;
            gbc.insets.left = (i == 0) ? 0 : 4;
            panelAbajoIzquierda.add(boton, gbc);
        }
        
        // Botón revivir (fila inferior)
        JButton btnRevivir = crearBotonItem(textosBotones[3]);
        btnRevivir.setPreferredSize(new Dimension(anchoBoton, altoBoton));
        botonesSuperiores[3] = btnRevivir;
        
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(-4, 0, 0, 0);
        panelAbajoIzquierda.add(btnRevivir, gbc);
        
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

    private JButton crearBotonItem(String texto) {
        try {
            String rutaImagen = PoobkemonGifs.getItemImage(texto);
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
            Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(PREF_ANCHO_BOTON_ITEM - 20, PREF_ALTO_BOTON_ITEM - 15, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            
            boton.setPreferredSize(new Dimension(PREF_ANCHO_BOTON_ITEM, PREF_ALTO_BOTON_ITEM));
            boton.setFocusPainted(false);
            boton.setToolTipText(texto);
            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            return boton;
        } catch (Exception e) {
            JButton boton = new JButton(texto);
            boton.setPreferredSize(new Dimension(PREF_ANCHO_BOTON_ITEM, PREF_ALTO_BOTON_ITEM));
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
            boton.setBorderPainted(false);
            return boton;
        }
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
        redimensionarBotonesItems();
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
    
    private void redimensionarBotonesItems() {
        if (panelIzquierdo != null && botonesSuperiores != null && botonesSuperiores.length > 0) {
            int anchoDisponible = panelIzquierdo.getWidth() - MARGEN_IZQUIERDO;
            int espacioEntreBotones = ESPACIO_BOTONES_ITEMS * (botonesSuperiores.length - 1);
            int anchoPorBoton = (anchoDisponible - espacioEntreBotones) / botonesSuperiores.length;
            anchoPorBoton = Math.max(MIN_ANCHO_BOTON, Math.min(anchoPorBoton, MAX_ANCHO_BOTON));
            int altoPorBoton = (anchoPorBoton * 3) / RELACION_ASPECTO;
            
            for (JButton boton : botonesSuperiores) {
                ImageIcon icono = (ImageIcon) boton.getIcon();
                if (icono != null) {
                    Image img = icono.getImage()
                        .getScaledInstance(anchoPorBoton-15, altoPorBoton-10, Image.SCALE_SMOOTH);
                    boton.setIcon(new ImageIcon(img));
                }
                boton.setPreferredSize(new Dimension(anchoPorBoton, altoPorBoton));
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
            if (potionsSelectedCount > MAX_POTIONS_SELECTED) {
                JOptionPane.showMessageDialog(this,
                    "Solo puedes seleccionar máximo " + MAX_POTIONS_SELECTED + " pociones",
                    "Límite excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (reviveSelectedCount > MAX_REVIVE_SELECTED) {
                JOptionPane.showMessageDialog(this,
                    "Solo puedes seleccionar máximo " + MAX_REVIVE_SELECTED + " revivir",
                    "Límite excedido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (esJugador1) {
                nombresPokemonJugador1.clear();
                nombresPokemonJugador1.addAll(nombresPokemonSeleccionados);

                if (POOBkemonGUI.getJugador2() != null && 
                    !POOBkemonGUI.getJugador2().getNombre().startsWith("Computer")) {
                    this.dispose();
                    SwingUtilities.invokeLater(() -> {
                        VentanaSeleccion ventanaJugador2 = new VentanaSeleccion(false);
                        ventanaJugador2.mostrar();
                    });
                } else {
                    this.dispose();
                    POOBkemonGUI.mostrarVentanaMovimientos(nombresPokemonSeleccionados);
                }
            } else {
                nombresPokemonJugador2.clear();
                nombresPokemonJugador2.addAll(nombresPokemonSeleccionados);
                List<String> seleccionCombinada = new ArrayList<>();
                seleccionCombinada.addAll(nombresPokemonJugador1);
                seleccionCombinada.addAll(nombresPokemonJugador2);
                this.dispose();
                POOBkemonGUI.mostrarVentanaMovimientos(seleccionCombinada);
            }
        });

        for (JButton boton : botonesSuperiores) {
            boton.addActionListener(e -> manejarSeleccionItem((JButton) e.getSource()));
        }
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
    
    private void manejarSeleccionItem(JButton boton) {
        String tipoItem = boton.getToolTipText();
        boolean esRevivir = tipoItem.equals("Revivir");
        
        if (selectedItemButtons.contains(boton)) {
            // Deseleccionar
            boton.setBackground(null);
            boton.setOpaque(false);
            selectedItemButtons.remove(boton);
            
            if (esRevivir) {
                reviveSelectedCount--;
            } else {
                potionsSelectedCount--;
            }
        } else {
            // Verificar límites
            if (esRevivir && reviveSelectedCount >= MAX_REVIVE_SELECTED) {
                return;
            }
            
            if (!esRevivir && potionsSelectedCount >= MAX_POTIONS_SELECTED) {
                return;
            }
            
            // Seleccionar
            boton.setBackground(SELECTED_COLOR);
            boton.setOpaque(true);
            selectedItemButtons.add(boton);
            
            if (esRevivir) {
                reviveSelectedCount++;
            } else {
                potionsSelectedCount++;
            }
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
            redimensionarBotonesItems();
        });
    }
    
    
    
}