package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class VentanaInicio extends Ventana {
    private FondoPanel fondoPanel;
    private JPanel tituloPanel;
    private BufferedImage tituloImage;

    public VentanaInicio() {
        super("POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Panel de fondo animado
        fondoPanel = new FondoPanel("/resources/inicio.gif");
        
        // Cargar imagen del título
        cargarImagenTitulo();
        
        // Configurar layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(getSize());
        
        fondoPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(fondoPanel, JLayeredPane.DEFAULT_LAYER);
        
        tituloPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(tituloPanel, JLayeredPane.PALETTE_LAYER);
        
        mainPanel.add(layeredPane, BorderLayout.CENTER);
    }

    private void cargarImagenTitulo() {
        try {
            URL tituloUrl = getClass().getResource("/resources/titulo.png");
            if (tituloUrl == null) {
                throw new RuntimeException("No se encontró la imagen del título");
            }
            tituloImage = ImageIO.read(tituloUrl);

            tituloPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (tituloImage != null) {
                        double scale = (double)getWidth() / tituloImage.getWidth() * 0.6;
                        int newWidth = (int)(tituloImage.getWidth() * scale);
                        int newHeight = (int)(tituloImage.getHeight() * scale);
                        g.drawImage(tituloImage, 
                                   (getWidth() - newWidth)/2, 
                                   getHeight()/3, 
                                   newWidth, 
                                   newHeight, 
                                   null);
                    }
                }
            };
            tituloPanel.setOpaque(false);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar recursos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarListeners() {
        // Listener para redimensionamiento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getContentPane().getSize();
                fondoPanel.setBounds(0, 0, size.width, size.height);
                tituloPanel.setBounds(0, 0, size.width, size.height);
                fondoPanel.repaint();
                tituloPanel.repaint();
            }
        });

        // Listener para clic en cualquier parte del fondo
        fondoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirVentanaOpciones();
            }
        });

        // Timer para animación del fondo
        new Timer(100, e -> fondoPanel.repaint()).start();
    }

    private void abrirVentanaOpciones() {
        this.setVisible(false);
        VentanaOpciones ventanaOpciones = new VentanaOpciones();
        ventanaOpciones.mostrar();
    }

    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nuevo juego seleccionado");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir partida", 
                         new String[]{"pkm"}, 
                         "Archivos POOBkemon (*.pkm)",
                         e -> JOptionPane.showMessageDialog(this, "Partida cargada"));
    }

    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar partida", 
                         new String[]{"pkm"}, 
                         "Archivos POOBkemon (*.pkm)",
                         e -> JOptionPane.showMessageDialog(this, "Partida guardada"));
    }

    @Override
    protected void accionExportar() {
        mostrarFileChooser("Exportar imagen", 
                         new String[]{"png"}, 
                         "Imágenes PNG (*.png)",
                         e -> JOptionPane.showMessageDialog(this, "Imagen exportada"));
    }

    @Override
    protected void accionImportar() {
        mostrarFileChooser("Importar imagen", 
                         new String[]{"png", "jpg"}, 
                         "Imágenes (*.png, *.jpg)",
                         e -> JOptionPane.showMessageDialog(this, "Imagen importada"));
    }

    public void mostrar() {
        setVisible(true);
    }
}