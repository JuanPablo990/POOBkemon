package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileNameExtensionFilter;

public class POOBkemonGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Configuración básica del frame
                JFrame frame = new JFrame("POOBkemon");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                // 2. Tamaño inicial (1/4 de pantalla centrado)
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int width = screenSize.width / 2;
                int height = screenSize.height / 2;
                frame.setSize(width, height);
                frame.setLocationRelativeTo(null);
                
                // 3. Panel principal con BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout());
                frame.setContentPane(mainPanel);
                
                // 4. Panel de fondo con GIF animado
                FondoPanel fondoPanel = new FondoPanel("/resources/inicio.gif");
                mainPanel.add(fondoPanel, BorderLayout.CENTER);
                
                // 5. Cargar imagen del título
                URL tituloUrl = POOBkemonGUI.class.getResource("/resources/titulo.png");
                if (tituloUrl == null) {
                    throw new RuntimeException("No se encontró el título: /resources/titulo.png");
                }
                BufferedImage tituloImage = ImageIO.read(tituloUrl);
                
                // 6. Panel para el título (transparente)
                JPanel tituloPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if (tituloImage != null) {
                            // Calcular escala (60% del ancho disponible)
                            double scale = (double)getWidth() / tituloImage.getWidth() * 0.6;
                            int newWidth = (int)(tituloImage.getWidth() * scale);
                            int newHeight = (int)(tituloImage.getHeight() * scale);
                            
                            // Dibujar título centrado y a 1/3 de altura desde arriba
                            g.drawImage(tituloImage, 
                                (getWidth() - newWidth)/2, 
                                getHeight()/3,
                                newWidth, newHeight, null);
                        }
                    }
                };
                tituloPanel.setOpaque(false);
                
                // 7. Usar layered pane para superposición
                JLayeredPane layeredPane = new JLayeredPane();
                layeredPane.setPreferredSize(new Dimension(width, height));
                
                // Añadir fondo
                fondoPanel.setBounds(0, 0, width, height);
                layeredPane.add(fondoPanel, JLayeredPane.DEFAULT_LAYER);
                
                // Añadir título
                tituloPanel.setBounds(0, 0, width, height);
                layeredPane.add(tituloPanel, JLayeredPane.PALETTE_LAYER);
                
                mainPanel.add(layeredPane, BorderLayout.CENTER);
                
                // 8. Barra de menú con todas las opciones
                JMenuBar menuBar = new JMenuBar();
                JMenu fileMenu = new JMenu("Menú");
                
                // Opción Nuevo
                JMenuItem nuevoItem = new JMenuItem("Nuevo");
                nuevoItem.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame, "Función Nuevo seleccionada");
                });
                
                // Opción Abrir (con JFileChooser)
                JMenuItem abrirItem = new JMenuItem("Abrir");
                abrirItem.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Abrir archivo POOBkemon");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos POOBkemon (*.pkm)", "pkm"));
                    
                    int returnValue = fileChooser.showOpenDialog(frame);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Archivo seleccionado: " + fileChooser.getSelectedFile().getName());
                    }
                });
                
                // Opción Guardar (con JFileChooser)
                JMenuItem guardarItem = new JMenuItem("Guardar");
                guardarItem.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Guardar archivo POOBkemon");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos POOBkemon (*.pkm)", "pkm"));
                    
                    int returnValue = fileChooser.showSaveDialog(frame);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Archivo guardado como: " + fileChooser.getSelectedFile().getName());
                    }
                });
                
                // Opción Exportar (con JFileChooser)
                JMenuItem exportarItem = new JMenuItem("Exportar");
                exportarItem.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Exportar archivo");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PNG (*.png)", "png"));
                    
                    int returnValue = fileChooser.showSaveDialog(frame);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Exportado como: " + fileChooser.getSelectedFile().getName());
                    }
                });
                
                // Opción Importar (con JFileChooser)
                JMenuItem importarItem = new JMenuItem("Importar");
                importarItem.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Importar archivo");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de imagen (*.png, *.jpg)", "png", "jpg"));
                    
                    int returnValue = fileChooser.showOpenDialog(frame);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Importado: " + fileChooser.getSelectedFile().getName());
                    }
                });
                
                // Opción Salir
                JMenuItem salirItem = new JMenuItem("Salir");
                salirItem.addActionListener(e -> System.exit(0));
                
                // Separadores
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
                
                // 9. Listener para redimensionamiento
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
                
                // 10. Timer para animación del GIF
                new Timer(100, e -> fondoPanel.repaint()).start();
                
                frame.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación:\n" + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}