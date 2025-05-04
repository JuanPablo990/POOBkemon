package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;
    private List<String> nombresPokemonSeleccionados;
    private Map<String, List<String>> movimientosPorPokemon;
    private JLabel[] etiquetasImagenes;
    private boolean esJugador1;
    private JLabel lblJugador;

    private static final int POKEMON_WIDTH = 100;
    private static final int POKEMON_HEIGHT = 100;
    private static final int MARGEN_SUPERIOR = 30;
    private static final int MARGEN_INFERIOR = 20;

    public VentanaMovimientos(List<String> nombresPokemonSeleccionados, boolean esJugador1) {
        super("Movimientos de POOBkemon");
        this.nombresPokemonSeleccionados = nombresPokemonSeleccionados;
        this.esJugador1 = esJugador1;
        this.movimientosPorPokemon = new HashMap<>();
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

        lblJugador = new JLabel();
        lblJugador.setFont(new Font("Arial", Font.BOLD, 16));
        lblJugador.setForeground(Color.WHITE);
        lblJugador.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        actualizarEtiquetaJugador();
        fondoPanel.add(lblJugador, BorderLayout.NORTH);

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
                            Image imagenOriginal = iconoOriginal.getImage();
                            int widthOriginal = iconoOriginal.getIconWidth();
                            int heightOriginal = iconoOriginal.getIconHeight();
                            float escala = Math.min((float) POKEMON_WIDTH / widthOriginal, (float) POKEMON_HEIGHT / heightOriginal);
                            int nuevoAncho = Math.round(widthOriginal * escala);
                            int nuevoAlto = Math.round(heightOriginal * escala);

                            Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_DEFAULT);
                            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

                            JLabel labelImagen = new JLabel(iconoEscalado, JLabel.CENTER);
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JPanel panelCombos = new JPanel(new GridLayout(4, 1, 5, 5));
                    panelCombos.setOpaque(false);
                    panelCombos.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

                    List<String> movimientos = new ArrayList<>();
                    String[] opcionesMovimientos = {"Movimiento 1", "Movimiento 2", "Movimiento 3", "Movimiento 4"};

                    for (int i = 0; i < 4; i++) {
                        JComboBox<String> comboBox = new JComboBox<>(opcionesMovimientos);
                        comboBox.setBackground(new Color(70, 180, 130, 150));
                        comboBox.setForeground(Color.BLACK);
                        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
                        comboBox.setMaximumRowCount(4);
                        panelCombos.add(comboBox);
                        movimientos.add(opcionesMovimientos[i]);
                    }

                    movimientosPorPokemon.put(nombrePokemon, movimientos);
                    contenidoCelda.add(panelCombos, BorderLayout.CENTER);
                }

                contenidoCelda.add(panelImagen, BorderLayout.NORTH);
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
            if (nombresPokemonSeleccionados != null && !nombresPokemonSeleccionados.isEmpty()) {
                for (String nombre : nombresPokemonSeleccionados) {
                    List<String> movimientos = movimientosPorPokemon.get(nombre);
                    POOBkemonGUI.setMovimientosDePokemon(nombre, movimientos, esJugador1);
                }

                dispose();
                if (esJugador1) {
                    POOBkemonGUI.setMostrandoMovimientosJugador1(false);
                    POOBkemonGUI.mostrarVentanaSeleccion(false);
                } else {
                    List<String> combinados = new ArrayList<>();
                    combinados.addAll(POOBkemonGUI.getSeleccionPokemonJugador1());
                    combinados.addAll(POOBkemonGUI.getSeleccionPokemonJugador2());
                    POOBkemonGUI.mostrarVentanaBatalla(combinados);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay Pokémon seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void actualizarEtiquetaJugador() {
        String nombre = esJugador1 ?
            (POOBkemonGUI.getJugador1() != null ? POOBkemonGUI.getJugador1().getNombre() : "Jugador 1") :
            (POOBkemonGUI.getJugador2() != null ? POOBkemonGUI.getJugador2().getNombre() : "Jugador 2");
        lblJugador.setText("Movimientos de: " + nombre);
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