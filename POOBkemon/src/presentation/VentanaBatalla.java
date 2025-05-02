package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaBatalla extends Ventana {
    
    private JPanel panelImagenPokemon; // Panel para tu imagen (200x200)
    
    public VentanaBatalla(List<String> nombresPokemonSeleccionados) {
        super("Batalla POOBkemon");
        configurarVentana();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // 1. Panel superior con el GIF de fondo y tu área de imagen
        FondoPanel panelGif = new FondoPanel("/resources/pelea.gif");
        panelGif.setLayout(null); // Usamos layout absoluto para posicionar libremente
        
        // Configurar el panel para tu imagen (200x200)
        panelImagenPokemon = new JPanel();
        panelImagenPokemon.setSize(200, 200);
        
        // Posición ajustada: 165px desde derecha (35px más a la derecha que la posición original)
        int xPos = getWidth() - 200 - 150; // 800 - 200 - 165 = 435 (35px más a la derecha que 400)
        int yPos = 30;
        panelImagenPokemon.setLocation(xPos, yPos);
        
        panelImagenPokemon.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        panelImagenPokemon.setOpaque(false);
        
        // Añadir el panel de imagen sobre el GIF
        panelGif.add(panelImagenPokemon);
        panelGif.setPreferredSize(new Dimension(getWidth(), (int)(getHeight() * 0.8)));
        
        // 2. Panel inferior con los botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        FondoPanel panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelArena.setPreferredSize(new Dimension(getWidth(), (int)(getHeight() * 0.2)));

        // Crear botones con imágenes
        JButton btnAtaque = crearBotonConImagen("/resources/ataque.png", "Ataque");
        JButton btnCambio = crearBotonConImagen("/resources/cambio.png", "Cambio");
        JButton btnItem = crearBotonConImagen("/resources/item.png", "Item");
        JButton btnHuida = crearBotonConImagen("/resources/huida.png", "Huida");

        // Agregar botones
        panelArena.add(btnAtaque);
        panelArena.add(btnCambio);
        panelArena.add(btnItem);
        panelArena.add(btnHuida);

        panelInferior.add(panelArena, BorderLayout.CENTER);

        // Ensamblar la ventana
        panelPrincipal.add(panelGif, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        setContentPane(panelPrincipal);
    }

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