package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import domain.*;

public class VentanaBatalla extends Ventana {

    private JPanel panelImagenPokemon;
    private JPanel panelImagenPokemon2;
    private final int ANCHO_PANEL = 200;
    private final int ALTO_PANEL = 200;
    private FondoPanel panelGif;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JLabel labelJugador;
    private boolean turnoJugador1 = true;

    public VentanaBatalla(List<String> nombresPokemonSeleccionados) {
        super("Batalla POOBkemon");
        configurarVentana();
        inicializarComponentes();
        cargarPokemonesIniciales();
        actualizarVistaJugador();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelGif = new FondoPanel("/resources/pelea.gif");
        panelGif.setLayout(null);

        panelImagenPokemon = new JPanel(new BorderLayout());
        panelImagenPokemon.setSize(ANCHO_PANEL, ALTO_PANEL);
        panelImagenPokemon2 = new JPanel(new BorderLayout());
        panelImagenPokemon2.setSize(ANCHO_PANEL, ALTO_PANEL);

        progressBar1 = new JProgressBar(0, 100);
        progressBar1.setStringPainted(true);
        progressBar1.setForeground(Color.YELLOW);
        progressBar1.setBackground(new Color(0, 0, 0, 0));
        progressBar1.setBorderPainted(false);

        progressBar2 = new JProgressBar(0, 100);
        progressBar2.setStringPainted(true);
        progressBar2.setForeground(Color.BLUE);
        progressBar2.setBackground(new Color(0, 0, 0, 0));
        progressBar2.setBorderPainted(false);

        panelImagenPokemon.add(progressBar1, BorderLayout.SOUTH);
        panelImagenPokemon2.add(progressBar2, BorderLayout.NORTH);

        panelImagenPokemon.setBorder(BorderFactory.createEmptyBorder());
        panelImagenPokemon.setOpaque(false);
        panelImagenPokemon2.setBorder(BorderFactory.createEmptyBorder());
        panelImagenPokemon2.setOpaque(false);

        panelGif.add(panelImagenPokemon);
        panelGif.add(panelImagenPokemon2);
        SwingUtilities.invokeLater(this::actualizarPosicionPanelImagen);

        JPanel panelInferior = new JPanel(new BorderLayout());
        FondoPanel panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new BorderLayout());

        labelJugador = new JLabel("Turno: " + POOBkemonGUI.getJugador1().getNombre(), SwingConstants.CENTER);
        labelJugador.setFont(new Font("Arial", Font.BOLD, 20));
        panelArena.add(labelJugador, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAtaque = crearBotonConImagen("/resources/ataque.png", "Ataque");
        JButton btnCambio = crearBotonConImagen("/resources/cambio.png", "Cambio");
        JButton btnItem = crearBotonConImagen("/resources/item.png", "Item");
        JButton btnHuida = crearBotonConImagen("/resources/huida.png", "Huida");

        btnHuida.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "¡Has perdido! Regresando al inicio...");
            POOBkemonGUI.reiniciarAplicacion();
        });

        btnAtaque.addActionListener(e -> mostrarVentanaAtaque());
        btnCambio.addActionListener(e -> mostrarVentanaCambioPokemon());
        btnItem.addActionListener(e -> mostrarVentanaItem());

        panelBotones.add(btnAtaque);
        panelBotones.add(btnCambio);
        panelBotones.add(btnItem);
        panelBotones.add(btnHuida);

        panelArena.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(panelArena, BorderLayout.CENTER);

        panelPrincipal.add(panelGif, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        setContentPane(panelPrincipal);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarPosicionPanelImagen();
            }
        });
    }

    private void actualizarVistaJugador() {
        Entrenador jugador = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        labelJugador.setText("Turno: " + jugador.getNombre());

        Pokemon activo = jugador.getPokemonActivo();
        String rutaGif = PoobkemonGifs.getPokemonImage(activo.getNombre());
        if (rutaGif != null) {
            ImageIcon gif = new ImageIcon(getClass().getResource(rutaGif));
            if (turnoJugador1) {
                setImagenPokemon(gif);
                actualizarVidaPokemon1(activo.getPs());
            } else {
                setImagenPokemon2(gif);
                actualizarVidaPokemon2(activo.getPs());
            }
        }
    }

    private void cargarPokemonesIniciales() {
        Entrenador j1 = POOBkemonGUI.getJugador1();
        Entrenador j2 = POOBkemonGUI.getJugador2();
        Pokemon p1 = j1.getPokemonActivo();
        Pokemon p2 = j2.getPokemonActivo();

        String gif1 = PoobkemonGifs.getPokemonImage(p1.getNombre());
        String gif2 = PoobkemonGifs.getPokemonImage(p2.getNombre());

        if (gif1 != null) setImagenPokemon(new ImageIcon(getClass().getResource(gif1)));
        if (gif2 != null) setImagenPokemon2(new ImageIcon(getClass().getResource(gif2)));

        actualizarVidaPokemon1(p1.getPs());
        actualizarVidaPokemon2(p2.getPs());
    }

    private void actualizarPosicionPanelImagen() {
        int panelWidth = panelGif.getWidth();
        int panelHeight = panelGif.getHeight();
        if (panelWidth == 0 || panelHeight == 0) return;

        int x2 = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
        int y2 = (int)(panelHeight * 0.2);

        int x1 = (int)(panelWidth * 0.3) - (ANCHO_PANEL / 2);
        int y1 = (int)(panelHeight * 0.47);

        x1 = Math.max(0, Math.min(x1, panelWidth - ANCHO_PANEL));
        y1 = Math.max(0, Math.min(y1, panelHeight - ALTO_PANEL));
        x2 = Math.max(0, Math.min(x2, panelWidth - ANCHO_PANEL));
        y2 = Math.max(0, Math.min(y2, panelHeight - ALTO_PANEL));

        panelImagenPokemon.setBounds(x1, y1, ANCHO_PANEL, ALTO_PANEL);
        panelImagenPokemon2.setBounds(x2, y2, ANCHO_PANEL, ALTO_PANEL);
        panelGif.repaint();
    }

    public void setImagenPokemon(ImageIcon imagen) {
        panelImagenPokemon.removeAll();
        JLabel label = new JLabel(imagen);
        panelImagenPokemon.add(label, BorderLayout.CENTER);
        panelImagenPokemon.add(progressBar1, BorderLayout.SOUTH);
        panelImagenPokemon.revalidate();
        panelImagenPokemon.repaint();
    }

    public void setImagenPokemon2(ImageIcon imagen) {
        panelImagenPokemon2.removeAll();
        JLabel label = new JLabel(imagen);
        panelImagenPokemon2.add(label, BorderLayout.CENTER);
        panelImagenPokemon2.add(progressBar2, BorderLayout.NORTH);
        panelImagenPokemon2.revalidate();
        panelImagenPokemon2.repaint();
    }

    public void actualizarVidaPokemon1(int vida) {
        progressBar1.setValue(vida);
    }

    public void actualizarVidaPokemon2(int vida) {
        progressBar2.setValue(vida);
    }

    private JButton crearBotonConImagen(String rutaImagen, String textoAlternativo) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
            Image img = icono.getImage().getScaledInstance(110, 60, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(img));
            boton.setPreferredSize(new Dimension(100, 90));
            boton.setToolTipText(textoAlternativo);
            boton.setBorder(BorderFactory.createEmptyBorder());
            boton.setContentAreaFilled(false);
            return boton;
        } catch (Exception e) {
            return new JButton(textoAlternativo);
        }
    }

    private void configurarVentana() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void mostrar() {
        setVisible(true);
    }

    @Override
    protected void accionNuevo() {}
    @Override
    protected void accionAbrir() {}
    @Override
    protected void accionGuardar() {}
    @Override
    protected void accionExportar() {}
    @Override
    protected void accionImportar() {}

    private void mostrarVentanaAtaque() {
        JDialog ventana = new JDialog(this, "Selecciona un Ataque", true);
        ventana.setLayout(new GridLayout(4, 1, 10, 10));
        ventana.setSize(300, 250);
        ventana.setLocationRelativeTo(this);
        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        List<Movimiento> movimientos = POOBkemonGUI.getPoobkemon().getMovimientosDisponibles(actual.getPokemonActivo());

        for (Movimiento m : movimientos) {
            JButton btn = new JButton(m.getNombre());
            btn.setBackground(verdeAguamarina);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(VentanaBatalla.this, actual.getNombre() + " usó " + m.getNombre());
                    turnoJugador1 = !turnoJugador1;
                    actualizarVistaJugador();
                    ventana.dispose();
                }
            });
            ventana.add(btn);
        }

        ventana.setVisible(true);
    }

    private void mostrarVentanaItem() {
        JOptionPane.showMessageDialog(this, "Sistema de ítems no implementado aún.");
    }

    private void mostrarVentanaCambioPokemon() {
        JOptionPane.showMessageDialog(this, "Sistema de cambio de Pokémon no implementado aún.");
    }
}
