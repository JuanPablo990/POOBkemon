package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;
    private List<String> rutasPokemonSeleccionados;
    private JLabel[] etiquetasImagenes; // Para mantener referencia a las imágenes
    
    private static final int IMG_WIDTH_DEFAULT = 50;  // Reducido de 80 a 60
    private static final int IMG_HEIGHT_DEFAULT = 50; // Reducido de 80 a 60
    private static final int MAX_IMG_SIZE = 60;       // Tamaño máximo permitido al redimensionar
    private static final int MIN_IMG_SIZE = 30;     

    public VentanaMovimientos(List<String> rutasPokemonSeleccionados) {
        super("Movimientos de POOBkemon");
        this.rutasPokemonSeleccionados = rutasPokemonSeleccionados;
        if (rutasPokemonSeleccionados != null) {
            etiquetasImagenes = new JLabel[rutasPokemonSeleccionados.size()];
        }
        inicializarComponentes();
        configurarVentana();
        agregarResizeListener();
    }

    private void inicializarComponentes() {
    	fondoPanel = new FondoPanel("/resources/movimientos.gif");
        fondoPanel.setLayout(new BorderLayout());

        JPanel gridPrincipal = new JPanel(new GridLayout(2, 3, 5, 5)); // Espaciado reducido
        gridPrincipal.setOpaque(false);

        int numPokemonesSeleccionados = rutasPokemonSeleccionados != null ? rutasPokemonSeleccionados.size() : 0;

        int pokemonIndex = 0;
        for (int fila = 0; fila < 2; fila++) {
            for (int col = 0; col < 3; col++) {
                JPanel celda = new JPanel(new BorderLayout());
                celda.setOpaque(false);
                celda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Borde más delgado

                JPanel contenidoCelda = new JPanel();
                contenidoCelda.setLayout(new BorderLayout());
                contenidoCelda.setOpaque(false);

                // Panel para la imagen (más compacto)
                JPanel panelImagen = new JPanel(new BorderLayout());
                panelImagen.setOpaque(false);
                panelImagen.setPreferredSize(new Dimension(0, 60)); // Altura reducida de 100 a 80
                
                if (rutasPokemonSeleccionados != null && pokemonIndex < numPokemonesSeleccionados) {
                    try {
                        String rutaImagen = rutasPokemonSeleccionados.get(pokemonIndex);
                        URL url = getClass().getResource(rutaImagen);
                        if (url != null) {
                            ImageIcon iconoOriginal = new ImageIcon(url);
                            
                            JLabel labelImagen = new JLabel(iconoOriginal, JLabel.CENTER);
                            labelImagen.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Padding reducido
                            
                            // Configurar tamaño inicial más pequeño
                            labelImagen.setPreferredSize(new Dimension(IMG_WIDTH_DEFAULT, IMG_HEIGHT_DEFAULT));
                            etiquetasImagenes[pokemonIndex] = labelImagen;
                            
                            panelImagen.add(labelImagen, BorderLayout.CENTER);
                            
                            // Nombre más compacto
                            String nombrePokemon = getNombrePokemonFromPath(rutaImagen);
                            JLabel labelNombre = new JLabel("<html><center>" + nombrePokemon + "</center></html>", JLabel.CENTER);
                            labelNombre.setForeground(Color.WHITE);
                            labelNombre.setFont(labelNombre.getFont().deriveFont(10f)); // Fuente más pequeña
                            panelImagen.add(labelNombre, BorderLayout.SOUTH);
                        }
                    } catch (Exception e) {
                        // Manejo de errores...
                    }
                }
                
                contenidoCelda.add(panelImagen, BorderLayout.NORTH);

                // Panel de botones más compacto
                JPanel panelBotones = new JPanel(new GridBagLayout());
                panelBotones.setOpaque(false);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(2, 5, 2, 5); // Espaciado reducido
                gbc.weightx = 1;

                if (rutasPokemonSeleccionados != null && pokemonIndex < numPokemonesSeleccionados) {
                    for (int i = 0; i < 4; i++) {
                        JButton boton = new JButton("Mov " + (i+1)); // Texto más corto
                        boton.setBackground(new Color(255, 255, 255, 100));
                        boton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                        boton.setPreferredSize(new Dimension(0, 25)); // Botones más delgados
                        boton.setFont(boton.getFont().deriveFont(10f)); // Fuente más pequeña
                        panelBotones.add(boton, gbc);
                    }
                }

                contenidoCelda.add(panelBotones, BorderLayout.CENTER);
                celda.add(contenidoCelda, BorderLayout.CENTER);
                gridPrincipal.add(celda);
                pokemonIndex++;
            }
        }

        // Panel para el botón Siguiente
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setPreferredSize(new Dimension(100, 30));
        btnSiguiente.setFont(btnSiguiente.getFont().deriveFont(12f));
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se podría navegar a la siguiente ventana
                JOptionPane.showMessageDialog(VentanaMovimientos.this, 
                    "¡Configuración completada!\nPokémon seleccionados: " + numPokemonesSeleccionados);
            }
        });
        panelBoton.add(btnSiguiente);
        fondoPanel.add(gridPrincipal, BorderLayout.CENTER);
        fondoPanel.add(panelBoton, BorderLayout.SOUTH);
        setContentPane(fondoPanel);
    }

    private String getNombrePokemonFromPath(String path) {
        String[] partes = path.split("/");
        String nombreConExtension = partes[partes.length - 1];
        return nombreConExtension.substring(0, nombreConExtension.lastIndexOf('.'));
    }

    private void configurarVentana() {
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    // Agregar listener para redimensionar imágenes cuando cambia el tamaño de la ventana
    private void agregarResizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarImagenes();
            }
        });
    }
    
    private void redimensionarImagenes() {
    	if (etiquetasImagenes == null) return;
        int anchoImagen = Math.min(getWidth() / 15, MAX_IMG_SIZE);
        anchoImagen = Math.max(MIN_IMG_SIZE, anchoImagen);
        for (int i = 0; i < etiquetasImagenes.length; i++) {
            if (etiquetasImagenes[i] != null) {
                etiquetasImagenes[i].setPreferredSize(new Dimension(anchoImagen, anchoImagen));
                etiquetasImagenes[i].revalidate();
            }
        }
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
        // Redimensionar imágenes al mostrar
        SwingUtilities.invokeLater(this::redimensionarImagenes);
    }
}