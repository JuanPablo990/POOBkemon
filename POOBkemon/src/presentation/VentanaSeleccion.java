package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

public class VentanaSeleccion extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JPanel panelContenedorPrincipal;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JPanel panelAbajoIzquierda;
    private JButton[] botonesSuperiores;
    private JButton[] botonesMatriz = new JButton[36];

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
    
    private static final String[] RUTAS_POKEMONES = {
        "/resources/Pokemones/Acero/Aron.gif",
        "/resources/Pokemones/Acero/Metangross.gif",
        "/resources/Pokemones/Agua/corphish.gif",
        "/resources/Pokemones/Agua/magikarp.gif",
        "/resources/Pokemones/Bicho/Scizor.gif",
        "/resources/Pokemones/Bicho/Shedinja.gif",
        "/resources/Pokemones/Dragon/Dratini.gif",
        "/resources/Pokemones/Dragon/Trapinch.gif",
        "/resources/Pokemones/Electrico/Magnemite.gif",
        "/resources/Pokemones/Electrico/Voltorb.gif",
        "/resources/Pokemones/Fantasma/Duskull.gif",
        "/resources/Pokemones/Fantasma/Shuppet.gif",
        "/resources/Pokemones/Fuego/Torchic.gif",
        "/resources/Pokemones/Fuego/Torjoal.gif",
        "/resources/Pokemones/Hada/Azurill.gif",
        "/resources/Pokemones/Hada/Gardevoir.gif",
        "/resources/Pokemones/Hielo/Snorunt.gif",
        "/resources/Pokemones/Hielo/Spheal.gif",
        "/resources/Pokemones/Lucha/Heracross.gif",
        "/resources/Pokemones/Lucha/Hitmonlee.gif",
        "/resources/Pokemones/Normal/Skitty.gif",
        "/resources/Pokemones/Normal/Zangoose.gif",
        "/resources/Pokemones/Planta/shroomish.gif",
        "/resources/Pokemones/Planta/tropius.gif",
        "/resources/Pokemones/Psiquico/Spoink.gif",
        "/resources/Pokemones/Psiquico/Wobbuffet.gif",
        "/resources/Pokemones/Roca/Geodude.gif",
        "/resources/Pokemones/Roca/Nosepass.gif",
        "/resources/Pokemones/Siniestro/Absol.gif",
        "/resources/Pokemones/Siniestro/Poochyena.gif",
        "/resources/Pokemones/Tierra/Numel.gif",
        "/resources/Pokemones/Tierra/Sandshrew.gif",
        "/resources/Pokemones/Veneno/Roselia.gif",
        "/resources/Pokemones/Veneno/Tentacool.gif",
        "/resources/Pokemones/Volador/Skarmory.gif",
        "/resources/Pokemones/Volador/Zubat.gif",
    };
    
    public VentanaSeleccion() {
        super("Selección de POOBkemon");
        inicializarComponentes();
        configurarListeners();
        agregarResizeListener();
        verificarCargaImagenes(); // Método de diagnóstico
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        fondoPanel = new FondoPanel("/resources/seleccion.jpg");
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

        // Panel derecho (matriz)
        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setOpaque(false);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
        panelContenedorPrincipal.add(panelDerecho);

        // Configuración de la matriz
        JPanel panelMatriz = new JPanel(new GridLayout(NUM_FILAS_MATRIZ, NUM_COLUMNAS_MATRIZ, ESPACIO_MATRIZ, ESPACIO_MATRIZ));
        panelMatriz.setOpaque(false);
        panelDerecho.add(panelMatriz, BorderLayout.CENTER);

        for (int i = 0; i < botonesMatriz.length; i++) {
            JButton boton = crearBotonPokemon(i);
            botonesMatriz[i] = boton;
            panelMatriz.add(boton);
        }

        // Configuración de los botones de items con imágenes
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
        
        String[] rutasImagenes = {
            "/resources/potion.png",
            "/resources/hyperpotion.png",
            "/resources/superpotion.png",
            "/resources/revivir.png"
        };
        
        String[] textosBotones = {"Poción", "Hiperpoción", "Superpoción", "Revivir"};
        botonesSuperiores = new JButton[4];
        
        // Fila superior (3 botones)
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        for (int i = 0; i < 3; i++) {
            JButton boton = crearBotonItem(textosBotones[i], rutasImagenes[i]);
            boton.setPreferredSize(new Dimension(anchoBoton, altoBoton));
            botonesSuperiores[i] = boton;
            
            gbc.gridx = i;
            gbc.insets.left = (i == 0) ? 0 : 4;
            panelAbajoIzquierda.add(boton, gbc);
        }
        
        // Botón revivir (fila inferior)
        JButton btnRevivir = crearBotonItem(textosBotones[3], rutasImagenes[3]);
        btnRevivir.setPreferredSize(new Dimension(anchoBoton, altoBoton));
        botonesSuperiores[3] = btnRevivir;
        
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(-4, 0, 0, 0);
        panelAbajoIzquierda.add(btnRevivir, gbc);
        
        contenedorBotones.add(panelAbajoIzquierda, BorderLayout.CENTER);
        panelIzquierdo.add(contenedorBotones, BorderLayout.SOUTH);

        // Panel inferior con botón Volver
        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.setOpaque(false);

        JPanel panelBotonVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonVolver.setOpaque(false);
        btnVolver = crearBotonVolver("Volver");
        panelBotonVolver.add(btnVolver);
        panelAbajo.add(panelBotonVolver, BorderLayout.EAST);

        fondoPanel.add(panelAbajo, BorderLayout.SOUTH);
    }

    private JButton crearBotonPokemon(int index) {
        JButton boton = new JButton();
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        
        if (index < RUTAS_POKEMONES.length && RUTAS_POKEMONES[index] != null) {
            try {
                // Carga optimizada para GIFs animados
                URL url = getClass().getResource(RUTAS_POKEMONES[index]);
                if (url != null) {
                    ImageIcon icono = new ImageIcon(url);
                    boton.setIcon(icono);
                    boton.setToolTipText(getNombrePokemonFromPath(RUTAS_POKEMONES[index]));
                    
                    // Forzar la actualización del componente
                    boton.revalidate();
                    boton.repaint();
                } else {
                    System.err.println("No se encontró el recurso: " + RUTAS_POKEMONES[index]);
                    mostrarBotonError(boton, "No encontrado");
                }
            } catch (Exception e) {
                System.err.println("Error cargando imagen: " + RUTAS_POKEMONES[index]);
                e.printStackTrace();
                mostrarBotonError(boton, "Error");
            }
        } else {
            mostrarBotonError(boton, "No image");
        }
        
        return boton;
    }
    
    private void mostrarBotonError(JButton boton, String mensaje) {
        boton.setText(mensaje);
        boton.setForeground(Color.RED);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
    }
    
    private String getNombrePokemonFromPath(String path) {
        String[] partes = path.split("/");
        String nombreConExtension = partes[partes.length - 1];
        return nombreConExtension.substring(0, nombreConExtension.lastIndexOf('.'));
    }

    private JButton crearBotonItem(String texto, String rutaImagen) {
        try {
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
                    scaledIcon.setImageObserver(boton); // El botón será el ImageObserver
                    
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
                boton.setMinimumSize(new Dimension(anchoPorBoton, altoPorBoton));
                boton.setMaximumSize(new Dimension(anchoPorBoton, altoPorBoton));
            }
            
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
                JOptionPane.showMessageDialog(this, "Botón " + botonesSuperiores[index].getToolTipText() + " presionado");
            });
        }
        
        for (int i = 0; i < botonesMatriz.length; i++) {
            final int index = i;
            botonesMatriz[i].addActionListener(e -> {
                String nombrePokemon = "No disponible";
                if (index < RUTAS_POKEMONES.length && RUTAS_POKEMONES[index] != null) {
                    nombrePokemon = getNombrePokemonFromPath(RUTAS_POKEMONES[index]);
                }
                JOptionPane.showMessageDialog(this, "Pokémon seleccionado: " + nombrePokemon);
            });
        }
    }
    
    // Método de diagnóstico
    private void verificarCargaImagenes() {
        System.out.println("=== DIAGNÓSTICO DE CARGA DE IMÁGENES ===");
        for (int i = 0; i < Math.min(RUTAS_POKEMONES.length, botonesMatriz.length); i++) {
            if (RUTAS_POKEMONES[i] != null) {
                URL url = getClass().getResource(RUTAS_POKEMONES[i]);
                System.out.println("Índice " + i + ": " + RUTAS_POKEMONES[i]);
                System.out.println("  URL: " + url);
                
                if (url == null) {
                    System.err.println("  ¡El recurso no se encontró!");
                } else {
                    try {
                        ImageIcon icon = new ImageIcon(url);
                        System.out.println("  Dimensiones: " + icon.getIconWidth() + "x" + icon.getIconHeight());
                        System.out.println("  Cargado: " + (icon.getImageLoadStatus() == MediaTracker.COMPLETE ? "Éxito" : "Falló"));
                    } catch (Exception e) {
                        System.err.println("  Error al cargar: " + e.getMessage());
                    }
                }
            }
        }
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