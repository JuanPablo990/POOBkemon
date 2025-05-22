package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.Pokemon;
import presentation.POOBkemonGUI;
import domain.Movimiento;

public class VentanaMovimientos extends Ventana {
    private FondoPanel fondoPanel;
    private List<String> nombresPokemonSeleccionados;
    private Map<String, List<JComboBox<String>>> combosPorPokemon;
    private JLabel[] etiquetasImagenes;
    private boolean esJugador1;
    private JLabel lblJugador;
    private static final int POKEMON_WIDTH = 100;
    private static final int POKEMON_HEIGHT = 100;
    private static final int MARGEN_SUPERIOR = 30;
    private static final int MARGEN_INFERIOR = 20;

    private static final Map<String, List<String>> COMPATIBILIDAD_MOVIMIENTOS = new HashMap<>();
    static {
        COMPATIBILIDAD_MOVIMIENTOS.put("Acero", List.of("Acero", "Roca", "Tierra", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Agua", List.of("Agua", "Hielo", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Bicho", List.of("Bicho", "Planta", "Veneno", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Dragón", List.of("Dragón", "Agua", "Eléctrico", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Eléctrico", List.of("Eléctrico", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fantasma", List.of("Fantasma", "Siniestro", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fuego", List.of("Fuego", "Volador", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hada", List.of("Hada", "Psíquico", "Planta", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hielo", List.of("Hielo", "Agua", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Lucha", List.of("Lucha", "Roca", "Siniestro", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Normal", List.of("Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Planta", List.of("Planta", "Bicho", "Hada", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Psíquico", List.of("Psíquico", "Hada", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Roca", List.of("Roca", "Tierra", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Siniestro", List.of("Siniestro", "Fantasma", "Lucha", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Tierra", List.of("Tierra", "Roca", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Veneno", List.of("Veneno", "Planta", "Bicho", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Volador", List.of("Volador", "Normal", "Lucha"));
    }

    public VentanaMovimientos(List<String> nombresPokemonSeleccionados, boolean esJugador1) {
        super("Movimientos de POOBkemon");
        this.nombresPokemonSeleccionados = nombresPokemonSeleccionados;
        this.esJugador1 = esJugador1;
        this.combosPorPokemon = new HashMap<>();
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
                if (pokemonIndex < nombresPokemonSeleccionados.size()) {
                    String nombrePokemon = nombresPokemonSeleccionados.get(pokemonIndex);
                    String rutaImagen = PoobkemonGifs.getPokemonImage(nombrePokemon);
                    try {
                        URL url = getClass().getResource(rutaImagen);
                        if (url != null) {
                            ImageIcon iconoOriginal = new ImageIcon(url);
                            Image imagenOriginal = iconoOriginal.getImage();
                            float escala = Math.min((float) POKEMON_WIDTH / iconoOriginal.getIconWidth(),(float) POKEMON_HEIGHT / iconoOriginal.getIconHeight());
                            int nuevoAncho = Math.round(iconoOriginal.getIconWidth() * escala);
                            int nuevoAlto = Math.round(iconoOriginal.getIconHeight() * escala);
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
                    Pokemon pokemon = POOBkemonGUI.getPoobkemon().crearPokemon(nombrePokemon);
                    List<String> movimientosCompatibles = obtenerMovimientosCompatibles(pokemon);
                    movimientosCompatibles.sort(String::compareToIgnoreCase);
                    List<JComboBox<String>> combos = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        JComboBox<String> comboBox = new JComboBox<>(movimientosCompatibles.toArray(new String[0]));
                        configurarComboBox(comboBox, nombrePokemon);
                        panelCombos.add(comboBox);
                        combos.add(comboBox);
                    }
                    combosPorPokemon.put(nombrePokemon, combos);
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
        btnSiguiente.addActionListener(e -> manejarBotonSiguiente());
        panelBoton.add(btnSiguiente);
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setOpaque(false);
        contenedorCentral.add(gridPrincipal, BorderLayout.CENTER);
        contenedorCentral.add(panelBoton, BorderLayout.SOUTH);
        fondoPanel.add(contenedorCentral, BorderLayout.CENTER);
        setContentPane(fondoPanel);
    }

    private List<String> obtenerMovimientosCompatibles(Pokemon pokemon) {
        List<String> tiposCompatibles = new ArrayList<>();
        String tipoPrincipal = pokemon.getTipoPrincipal();
        String tipoSecundario = pokemon.getTipoSecundario();
        if (COMPATIBILIDAD_MOVIMIENTOS.containsKey(tipoPrincipal)) {
            tiposCompatibles.addAll(COMPATIBILIDAD_MOVIMIENTOS.get(tipoPrincipal));
        }
        if (tipoSecundario != null && COMPATIBILIDAD_MOVIMIENTOS.containsKey(tipoSecundario)) {
            for (String t : COMPATIBILIDAD_MOVIMIENTOS.get(tipoSecundario)) {
                if (!tiposCompatibles.contains(t)) {
                    tiposCompatibles.add(t);
                }
            }
        }
        List<String> movimientosCompatibles = new ArrayList<>();
        for (String tipo : tiposCompatibles) {
            List<String> movimientosDelTipo = POOBkemonGUI.getPoobkemon().getPoquedex().obtenerMovimientosPorTipo(tipo);
            for (String movimiento : movimientosDelTipo) {
                if (!movimientosCompatibles.contains(movimiento)) {
                    movimientosCompatibles.add(movimiento);
                }
            }
        }
        return movimientosCompatibles;
    }

    private void configurarComboBox(JComboBox<String> comboBox, String nombrePokemon) {
        comboBox.setBackground(new Color(70, 180, 130, 150));
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setMaximumRowCount(8);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    Movimiento m = POOBkemonGUI.getPoobkemon().getPoquedex().crearMovimiento(value.toString());
                    if (m != null) {
                        setToolTipText(String.format("Tipo: %s | Potencia: %d | Precisión: %d%%",m.getTipo(), m.getPotencia(), m.getPrecision()));
                    }
                }
                return this;
            }
        });
        final String[] seleccionAnterior = {null};
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String seleccionActual = (String) e.getItem();
                List<JComboBox<String>> combos = combosPorPokemon.get(nombrePokemon);
                for (JComboBox<String> otroCombo : combos) {
                    if (otroCombo != comboBox && seleccionActual.equals(otroCombo.getSelectedItem())) {
                        JOptionPane.showMessageDialog(this,"Este ataque ya está seleccionado para este Pokémon","Aviso", JOptionPane.WARNING_MESSAGE);comboBox.setSelectedItem(seleccionAnterior[0]);
                        return;
                    }
                }
                seleccionAnterior[0] = seleccionActual;
            }
        });
    }

    private void manejarBotonSiguiente() {
        if (nombresPokemonSeleccionados != null && !nombresPokemonSeleccionados.isEmpty()) {
            for (String nombre : nombresPokemonSeleccionados) {
                List<JComboBox<String>> combos = combosPorPokemon.get(nombre);
                List<String> movimientosSeleccionados = new ArrayList<>();
                for (JComboBox<String> combo : combos) {
                    String seleccionado = (String) combo.getSelectedItem();
                    if (seleccionado != null) {
                        if (movimientosSeleccionados.contains(seleccionado)) {
                            JOptionPane.showMessageDialog(this,
                                "El Pokémon " + nombre + " tiene movimientos duplicados: " + seleccionado,
                                "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        movimientosSeleccionados.add(seleccionado);
                    }
                }
            }
            for (String nombre : nombresPokemonSeleccionados) {
                List<JComboBox<String>> combos = combosPorPokemon.get(nombre);
                List<String> movimientosSeleccionados = new ArrayList<>();
                for (JComboBox<String> combo : combos) {
                    String seleccionado = (String) combo.getSelectedItem();
                    if (seleccionado != null) {
                        movimientosSeleccionados.add(seleccionado);
                    }
                }
                POOBkemonGUI.setMovimientosDePokemon(nombre, movimientosSeleccionados, esJugador1);
                Pokemon pokemonReal = (esJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2()).getPokemonPorNombre(nombre);
                if (pokemonReal != null) {
                    try {
                        pokemonReal.setMovimientosDesdeNombres(movimientosSeleccionados);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this,"Error al asignar movimientos a " + nombre + ":\n" + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
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
    }

    private void actualizarEtiquetaJugador() {
        String nombre = esJugador1 ?
        (POOBkemonGUI.getJugador1() != null ? POOBkemonGUI.getJugador1().getNombre() : "Jugador 1") :(POOBkemonGUI.getJugador2() != null ? POOBkemonGUI.getJugador2().getNombre() : "Jugador 2");
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