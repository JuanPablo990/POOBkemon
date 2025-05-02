package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;
    private List<String> nombresPokemonSeleccionados;
    private JLabel[] etiquetasImagenes;

    private static final int POKEMON_WIDTH = 100;
    private static final int POKEMON_HEIGHT = 100;
    private static final int MARGEN_SUPERIOR = 30;
    private static final int MARGEN_INFERIOR = 20;

    public VentanaMovimientos(List<String> nombresPokemonSeleccionados) {
        super("Movimientos de POOBkemon");
        this.nombresPokemonSeleccionados = nombresPokemonSeleccionados;
        if (nombresPokemonSeleccionados != null) {
            etiquetasImagenes = new JLabel[nombresPokemonSeleccionados.size()];
        }
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        fondoPanel = new FondoPanel(PoobkemonGifs.FONDO_MOVIMIENTOS);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.setBorder(BorderFactory.createEmptyBorder(MARGEN_SUPERIOR, 0, MARGEN_INFERIOR, 0));

        JPanel gridPrincipal = new JPanel(new GridLayout(2, 3, 15, 15));
        gridPrincipal.setOpaque(false);

        int numPokemonesSeleccionados = nombresPokemonSeleccionados != null ? nombresPokemonSeleccionados.size() : 0;
        int pokemonIndex = 0;

        for (int fila = 0; fila < 2; fila++) {
            for (int col = 0; col < 3; col++) {
                JPanel celda = new JPanel(new BorderLayout());
                celda.setOpaque(false);
                celda.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JPanel contenidoCelda = new JPanel(new BorderLayout());
                contenidoCelda.setOpaque(false);

                JPanel panelImagen = new JPanel(new BorderLayout());
                panelImagen.setOpaque(false);
                panelImagen.setPreferredSize(new Dimension(POKEMON_WIDTH, POKEMON_HEIGHT + 30));

                if (nombresPokemonSeleccionados != null && pokemonIndex < numPokemonesSeleccionados) {
                    String nombrePokemon = nombresPokemonSeleccionados.get(pokemonIndex);
                    String rutaImagen = PoobkemonGifs.getPokemonImage(nombrePokemon);

                    try {
                        URL url = getClass().getResource(rutaImagen);
                        if (url != null) {
                            ImageIcon iconoOriginal = new ImageIcon(url);

                            // ESCALAR la imagen si es más grande que 100x100 respetando proporciones
                            Image imagenOriginal = iconoOriginal.getImage();
                            int widthOriginal = iconoOriginal.getIconWidth();
                            int heightOriginal = iconoOriginal.getIconHeight();

                            float escala = Math.min(
                                (float) POKEMON_WIDTH / widthOriginal,
                                (float) POKEMON_HEIGHT / heightOriginal
                            );

                            int nuevoAncho = Math.round(widthOriginal * escala);
                            int nuevoAlto = Math.round(heightOriginal * escala);

                            Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_DEFAULT);
                            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

                            JLabel labelImagen = new JLabel(iconoEscalado, JLabel.CENTER);
                            labelImagen.setHorizontalAlignment(JLabel.CENTER);
                            labelImagen.setVerticalAlignment(JLabel.CENTER);
                            etiquetasImagenes[pokemonIndex] = labelImagen;

                            JPanel contenedorImagen = new JPanel(new GridBagLayout());
                            contenedorImagen.setOpaque(false);
                            contenedorImagen.setPreferredSize(new Dimension(POKEMON_WIDTH, POKEMON_HEIGHT));
                            contenedorImagen.add(labelImagen);

                            panelImagen.add(contenedorImagen, BorderLayout.CENTER);

                            JLabel labelNombre = new JLabel(nombrePokemon, JLabel.CENTER);
                            labelNombre.setForeground(Color.BLACK);
                            labelNombre.setFont(new Font("Arial", Font.BOLD, 12));
                            panelImagen.add(labelNombre, BorderLayout.SOUTH);
                        } else {
                            System.err.println("No se encontró la imagen para: " + nombrePokemon);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                contenidoCelda.add(panelImagen, BorderLayout.NORTH);

                JPanel panelBotones = new JPanel(new GridBagLayout());
                panelBotones.setOpaque(false);
                panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(3, 5, 3, 5);
                gbc.weightx = 1;

                if (nombresPokemonSeleccionados != null && pokemonIndex < numPokemonesSeleccionados) {
                    for (int i = 0; i < 4; i++) {
                        JButton boton = new JButton("Mov " + (i + 1));
                        boton.setBackground(new Color(70, 180, 130, 150));
                        boton.setForeground(Color.BLACK);
                        boton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                        boton.setPreferredSize(new Dimension(0, 30));
                        boton.setFont(new Font("Arial", Font.BOLD, 12));
                        panelBotones.add(boton, gbc);
                    }
                }

                contenidoCelda.add(panelBotones, BorderLayout.CENTER);
                celda.add(contenidoCelda, BorderLayout.CENTER);
                gridPrincipal.add(celda);
                pokemonIndex++;
            }
        }

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setPreferredSize(new Dimension(150, 40));
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 14));
        btnSiguiente.setBackground(new Color(70, 180, 130));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.addActionListener(e -> {
            // Cambio realizado aquí: Ahora abre la VentanaBatalla
            if (nombresPokemonSeleccionados != null && !nombresPokemonSeleccionados.isEmpty()) {
                POOBkemonGUI.mostrarVentanaBatalla(nombresPokemonSeleccionados);
            } else {
                JOptionPane.showMessageDialog(VentanaMovimientos.this,
                    "No hay Pokémon seleccionados",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBoton.add(btnSiguiente);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setOpaque(false);
        contenedorCentral.add(gridPrincipal, BorderLayout.CENTER);
        contenedorCentral.add(panelBoton, BorderLayout.SOUTH);

        fondoPanel.add(contenedorCentral, BorderLayout.CENTER);
        setContentPane(fondoPanel);
    }

    private void configurarVentana() {
        setSize(1100, 700);
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