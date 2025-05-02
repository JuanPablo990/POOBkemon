package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class VentanaBatalla extends Ventana {
    
    private JPanel panelImagenPokemon;
    private JPanel panelImagenPokemon2; // Nuevo panel
    private final int ANCHO_PANEL = 200;
    private final int ALTO_PANEL = 200;
    private FondoPanel panelGif;
    
    public VentanaBatalla(List<String> nombresPokemonSeleccionados) {
        super("Batalla POOBkemon");
        configurarVentana();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // 1. Panel superior con el GIF
        panelGif = new FondoPanel("/resources/pelea.gif");
        panelGif.setLayout(null);
        
        // Primer panel (original)
        panelImagenPokemon = new JPanel();
        panelImagenPokemon.setSize(ANCHO_PANEL, ALTO_PANEL);
        
        // Segundo panel (nuevo)
        panelImagenPokemon2 = new JPanel();
        panelImagenPokemon2.setSize(ANCHO_PANEL, ALTO_PANEL);
        
        actualizarPosicionPanelImagen();
        
        // Configurar estilos de los paneles
        panelImagenPokemon.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        panelImagenPokemon.setOpaque(false);
        
        panelImagenPokemon2.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        panelImagenPokemon2.setOpaque(false);
        
        panelGif.add(panelImagenPokemon);
        panelGif.add(panelImagenPokemon2);
        SwingUtilities.invokeLater(() -> actualizarPosicionPanelImagen());
        
        
        // 2. Panel inferior con botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        FondoPanel panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botones
        JButton btnAtaque = crearBotonConImagen("/resources/ataque.png", "Ataque");
        JButton btnCambio = crearBotonConImagen("/resources/cambio.png", "Cambio");
        JButton btnItem = crearBotonConImagen("/resources/item.png", "Item");
        JButton btnHuida = crearBotonConImagen("/resources/huida.png", "Huida");

        panelArena.add(btnAtaque);
        panelArena.add(btnCambio);
        panelArena.add(btnItem);
        panelArena.add(btnHuida);

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

    private void actualizarPosicionPanelImagen() {
    	int panelWidth = panelGif.getWidth();
        int panelHeight = panelGif.getHeight();

        if (panelWidth == 0 || panelHeight == 0) return;

        // Panel 1 (derecha)
        int x1 = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
        int y1 = (int)(panelHeight * 0.2);

        // Panel 2 (izquierda y abajo)
        int x2 = (int)(panelWidth * 0.3) - (ANCHO_PANEL / 2);
        int y2 = (int)(panelHeight * 0.47);

        // Asegurar límites
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
        panelImagenPokemon.add(label);
        panelImagenPokemon.revalidate();
        panelImagenPokemon.repaint();
    }

    public void setImagenPokemon2(ImageIcon imagen) {
        panelImagenPokemon2.removeAll();
        JLabel label = new JLabel(imagen);
        panelImagenPokemon2.add(label);
        panelImagenPokemon2.revalidate();
        panelImagenPokemon2.repaint();
    }

    private JButton crearBotonConImagen(String rutaImagen, String textoAlternativo) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(img));
            boton.setPreferredSize(new Dimension(80, 80));
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

    @Override protected void accionNuevo() {}
    @Override protected void accionAbrir() {}
    @Override protected void accionGuardar() {}
    @Override protected void accionExportar() {}
    @Override protected void accionImportar() {}
}