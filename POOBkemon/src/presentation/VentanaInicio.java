package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaInicio {
    private JFrame frame;
    private FondoPanel fondoPanel;
    private JPanel tituloPanel;
    private BufferedImage tituloImage;

    public VentanaInicio() {
        inicializarVentana();
        configurarComponentes();
        configurarMenu();
        configurarListeners();
    }

    private void inicializarVentana() {
        frame = new JFrame("POOBkemon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setLocationRelativeTo(null);
    }

    private void configurarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);

        // Panel de fondo
        fondoPanel = new FondoPanel("/resources/inicio.gif");
        mainPanel.add(fondoPanel, BorderLayout.CENTER);

        // Cargar imagen del título
        try {
            URL tituloUrl = getClass().getResource("/resources/titulo.png");
            if (tituloUrl == null) throw new RuntimeException("No se encontró el título");
            tituloImage = ImageIO.read(tituloUrl);

            tituloPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (tituloImage != null) {
                        double scale = (double)getWidth() / tituloImage.getWidth() * 0.6;
                        int newWidth = (int)(tituloImage.getWidth() * scale);
                        int newHeight = (int)(tituloImage.getHeight() * scale);
                        g.drawImage(tituloImage, (getWidth() - newWidth)/2, getHeight()/3, newWidth, newHeight, null);
                    }
                }
            };
            tituloPanel.setOpaque(false);

            // Usar layered pane
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(frame.getSize());
            fondoPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            layeredPane.add(fondoPanel, JLayeredPane.DEFAULT_LAYER);
            tituloPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            layeredPane.add(tituloPanel, JLayeredPane.PALETTE_LAYER);
            mainPanel.add(layeredPane, BorderLayout.CENTER);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menú");

        // Opciones del menú (igual que en tu código original)
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        nuevoItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Función Nuevo seleccionada"));

        JMenuItem abrirItem = new JMenuItem("Abrir");
        abrirItem.addActionListener(e -> abrirArchivo());

        JMenuItem guardarItem = new JMenuItem("Guardar");
        guardarItem.addActionListener(e -> guardarArchivo());

        JMenuItem exportarItem = new JMenuItem("Exportar");
        exportarItem.addActionListener(e -> exportarArchivo());

        JMenuItem importarItem = new JMenuItem("Importar");
        importarItem.addActionListener(e -> importarArchivo());

        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));

        fileMenu.add(nuevoItem);
        fileMenu.add(abrirItem);
        fileMenu.add(guardarItem);
        fileMenu.addSeparator();
        fileMenu.add(exportarItem);
        fileMenu.add(importarItem);
        fileMenu.addSeparator();
        fileMenu.add(salirItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    private void configurarListeners() {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = frame.getContentPane().getSize();
                fondoPanel.setBounds(0, 0, size.width, size.height);
                tituloPanel.setBounds(0, 0, size.width, size.height);
                fondoPanel.repaint();
                tituloPanel.repaint();
            }
        });

        new Timer(100, e -> fondoPanel.repaint()).start();
    }

    // Métodos para las acciones del menú (copiados de tu código original)
    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos POOBkemon (*.pkm)", "pkm"));
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, "Archivo seleccionado: " + fileChooser.getSelectedFile().getName());
        }
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos POOBkemon (*.pkm)", "pkm"));
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, "Archivo guardado como: " + fileChooser.getSelectedFile().getName());
        }
    }

    private void exportarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PNG (*.png)", "png"));
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, "Exportado como: " + fileChooser.getSelectedFile().getName());
        }
    }

    private void importarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de imagen (*.png, *.jpg)", "png", "jpg"));
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, "Importado: " + fileChooser.getSelectedFile().getName());
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}