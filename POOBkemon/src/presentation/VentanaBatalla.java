package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class VentanaBatalla extends Ventana {
    
    private JPanel panelImagenPokemon;
    private final int ANCHO_PANEL = 200;
    private final int ALTO_PANEL = 200;
    private FondoPanel panelGif; // Lo moví a nivel de clase para poder accederlo
    
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
        
        panelImagenPokemon = new JPanel();
        panelImagenPokemon.setSize(ANCHO_PANEL, ALTO_PANEL);
        actualizarPosicionPanelImagen();
        
        panelImagenPokemon.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        panelImagenPokemon.setOpaque(false);
        panelGif.add(panelImagenPokemon);
        
        // 2. Panel inferior con botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        FondoPanel panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botones (igual que antes)
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
        // Calculamos posición relativa al área del panelGif
        int panelWidth = panelGif.getWidth();
        int panelHeight = panelGif.getHeight();
        
        // Posición X: 70% del ancho del panel (para que no quede pegado a la derecha)
        int xPos = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
        // Posición Y: 20% del alto del panel
        int yPos = (int)(panelHeight * 0.2);
        
        // Aseguramos que no se salga de los bordes
        xPos = Math.min(xPos, panelWidth - ANCHO_PANEL - 20); // -20 para margen mínimo
        xPos = Math.max(xPos, 20); // Margen izquierdo mínimo
        yPos = Math.min(yPos, panelHeight - ALTO_PANEL - 20); // Margen inferior mínimo
        yPos = Math.max(yPos, 20); // Margen superior mínimo
        
        panelImagenPokemon.setLocation(xPos, yPos);
    }

    // ... (los demás métodos se mantienen igual que en tu versión original)
    public void setImagenPokemon(ImageIcon imagen) {
        panelImagenPokemon.removeAll();
        JLabel label = new JLabel(imagen);
        panelImagenPokemon.add(label);
        panelImagenPokemon.revalidate();
        panelImagenPokemon.repaint();
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