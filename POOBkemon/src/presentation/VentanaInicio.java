package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Ventana de inicio de la aplicación POOBkemon.
 * Muestra la pantalla principal con fondo animado y el título.
 * Permite acceder a las opciones y manejar acciones de archivo.
 */
public class VentanaInicio extends Ventana implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Panel de fondo animado */
    private FondoPanel fondoPanel;
    /** Panel que contiene la imagen del título */
    private JPanel tituloPanel;
    /** Imagen del título */
    private BufferedImage tituloImage;

    /**
     * Crea la ventana de inicio e inicializa sus componentes.
     */
    public VentanaInicio() {
        super("POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    /**
     * Inicializa y organiza los componentes gráficos de la ventana.
     */
    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        fondoPanel = new FondoPanel("/resources/inicio.gif");
        cargarImagenTitulo();
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(getSize());
        fondoPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(fondoPanel, JLayeredPane.DEFAULT_LAYER);
        tituloPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(tituloPanel, JLayeredPane.PALETTE_LAYER);
        mainPanel.add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Carga la imagen del título y la prepara para ser dibujada en el panel correspondiente.
     */
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
                        g.drawImage(tituloImage,(getWidth() - newWidth)/2, getHeight()/3, newWidth, newHeight, null);
                    }
                }
            };
            tituloPanel.setOpaque(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar recursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura los listeners para manejar eventos de la ventana y del usuario.
     */
    private void configurarListeners() {
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
        fondoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirVentanaOpciones();
            }
        });
        new Timer(100, e -> fondoPanel.repaint()).start();
    }

    /**
     * Abre la ventana de opciones y oculta la ventana de inicio.
     */
    private void abrirVentanaOpciones() {
        this.setVisible(false);
        VentanaOpciones ventanaOpciones = new VentanaOpciones();
        ventanaOpciones.mostrar();
    }

    /**
     * Reinicia la aplicación al seleccionar la opción "Nuevo".
     */
    @Override
    protected void accionNuevo() {
        this.dispose();
        POOBkemonGUI.reiniciarAplicacion();
    }

    /**
     * Muestra el diálogo para abrir una partida guardada.
     */
    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir partida",new String[]{"pkm"},"Archivos POOBkemon (*.pkm)",e -> JOptionPane.showMessageDialog(this, "Partida cargada"));
    }

    /**
     * Muestra el diálogo para guardar la partida actual.
     */
    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar partida",new String[]{"pkm"},"Archivos POOBkemon (*.pkm)",e -> JOptionPane.showMessageDialog(this, "Partida guardada"));
    }

    /**
     * Muestra el diálogo para exportar una imagen.
     */
    @Override
    protected void accionExportar() {
        mostrarFileChooser("Exportar imagen",new String[]{"png"},"Imágenes PNG (*.png)",e -> JOptionPane.showMessageDialog(this, "Imagen exportada"));
    }

    /**
     * Muestra el diálogo para importar una imagen.
     */
    @Override
    protected void accionImportar() {
        mostrarFileChooser("Importar imagen",new String[]{"png", "jpg"},"Imágenes (*.png, *.jpg)",e -> JOptionPane.showMessageDialog(this, "Imagen importada"));
    }

    /**
     * Hace visible la ventana de inicio.
     */
    public void mostrar() {
        setVisible(true);
    }
}
