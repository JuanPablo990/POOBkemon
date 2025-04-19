package presentation; // Esta línea es crucial

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class POOBkemonGUI {

    private static Point mouseDownCompCoords;
    private static boolean isMaximized = false;
    private static Rectangle previousBounds;
    private static JLabel tituloLabel;
    private static ImageIcon tituloIcon;
    private static JPanel backgroundPanel;
    private static URL currentBackgroundUrl;
    private static boolean isInOptionsScreen = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        
        // Obtenemos las dimensiones de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        // Calculamos un cuarto del tamaño de la pantalla
        int frameWidth = screenWidth / 2;  // Mitad del ancho
        int frameHeight = screenHeight / 2; // Mitad del alto
        
        // Calculamos la posición para centrar la ventana
        int posX = (screenWidth - frameWidth) / 2;
        int posY = (screenHeight - frameHeight) / 2;
        
        // Establecemos el tamaño y posición
        frame.setBounds(posX, posY, frameWidth, frameHeight);
        previousBounds = new Rectangle(posX, posY, frameWidth, frameHeight);

        try {
            // URLs de los recursos
            final URL inicioUrl = POOBkemonGUI.class.getResource("/resources/inicio.gif");
            final URL opcionesUrl = POOBkemonGUI.class.getResource("/resources/opciones.gif");
            
            if(inicioUrl == null) {
                throw new Exception("No se encontró el GIF en /resources/inicio.gif");
            }
            if(opcionesUrl == null) {
                throw new Exception("No se encontró el GIF en /resources/opciones.gif");
            }
            
            // Inicialmente usamos la pantalla de inicio
            currentBackgroundUrl = inicioUrl;
            
            // Creamos un panel principal con BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setOpaque(false);
            
            // Panel para el fondo con la imagen
            backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Cargar la imagen de fondo actual
                    ImageIcon icon = new ImageIcon(currentBackgroundUrl);
                    Image img = icon.getImage();
                    // Dibujar la imagen escalada para ajustarse al panel
                    g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new BorderLayout());
            
            // Creamos la barra de título personalizada
            JPanel titleBar = new JPanel(new BorderLayout());
            titleBar.setBackground(new Color(50, 50, 50, 200)); // Semi-transparente
            titleBar.setPreferredSize(new Dimension(frameWidth, 30));
            
            // Título
            JLabel titleLabel = new JLabel("POOBkemon");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            titleBar.add(titleLabel, BorderLayout.WEST);
            
            // Panel para los botones
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            buttonPanel.setOpaque(false);
            
            // Botón minimizar
            JButton minimizeButton = new JButton("_");
            minimizeButton.setForeground(Color.WHITE);
            minimizeButton.setFont(new Font("Arial", Font.BOLD, 14));
            minimizeButton.setFocusPainted(false);
            minimizeButton.setBorderPainted(false);
            minimizeButton.setContentAreaFilled(false);
            minimizeButton.setPreferredSize(new Dimension(45, 30));
            minimizeButton.addActionListener(e -> frame.setState(Frame.ICONIFIED));
            
            // Botón maximizar
            JButton maximizeButton = new JButton("□");
            maximizeButton.setForeground(Color.WHITE);
            maximizeButton.setFont(new Font("Arial", Font.BOLD, 14));
            maximizeButton.setFocusPainted(false);
            maximizeButton.setBorderPainted(false);
            maximizeButton.setContentAreaFilled(false);
            maximizeButton.setPreferredSize(new Dimension(45, 30));
            maximizeButton.addActionListener(e -> {
                if (isMaximized) {
                    // Restaurar al tamaño anterior
                    frame.setBounds(previousBounds);
                    maximizeButton.setText("□");
                    isMaximized = false;
                    
                    // Redimensionar el título al tamaño normal
                    if (!isInOptionsScreen) {
                        resizeTitleImage(previousBounds.width);
                    }
                } else {
                    // Guardar el tamaño actual
                    previousBounds = frame.getBounds();
                    // Maximizar
                    frame.setBounds(0, 0, screenWidth, screenHeight);
                    maximizeButton.setText("❐");
                    isMaximized = true;
                    
                    // Redimensionar el título para pantalla completa (con moderación)
                    if (!isInOptionsScreen) {
                        resizeTitleImage(screenWidth);
                    }
                }
                // Repintar el fondo para ajustar la imagen
                backgroundPanel.repaint();
            });
            
            // Botón cerrar
            JButton closeButton = new JButton("X");
            closeButton.setForeground(Color.WHITE);
            closeButton.setFont(new Font("Arial", Font.BOLD, 14));
            closeButton.setFocusPainted(false);
            closeButton.setBorderPainted(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setPreferredSize(new Dimension(45, 30));
            closeButton.addActionListener(e -> System.exit(0));
            
            // Efecto hover para los botones
            minimizeButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    minimizeButton.setBackground(new Color(80, 80, 80));
                    minimizeButton.setContentAreaFilled(true);
                }
                public void mouseExited(MouseEvent e) {
                    minimizeButton.setContentAreaFilled(false);
                }
            });
            
            maximizeButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    maximizeButton.setBackground(new Color(80, 80, 80));
                    maximizeButton.setContentAreaFilled(true);
                }
                public void mouseExited(MouseEvent e) {
                    maximizeButton.setContentAreaFilled(false);
                }
            });
            
            closeButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    closeButton.setBackground(new Color(232, 17, 35));
                    closeButton.setContentAreaFilled(true);
                }
                public void mouseExited(MouseEvent e) {
                    closeButton.setContentAreaFilled(false);
                }
            });
            
            // Añadir botones al panel
            buttonPanel.add(minimizeButton);
            buttonPanel.add(maximizeButton);
            buttonPanel.add(closeButton);
            titleBar.add(buttonPanel, BorderLayout.EAST);
            
            // Hacer que la ventana sea arrastrable
            titleBar.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!isMaximized) {
                        mouseDownCompCoords = e.getPoint();
                    }
                }
                
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        // Doble clic en la barra de título para maximizar/restaurar
                        maximizeButton.doClick();
                    }
                }
            });
            
            titleBar.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (!isMaximized && mouseDownCompCoords != null) {
                        Point currCoords = e.getLocationOnScreen();
                        frame.setLocation(currCoords.x - mouseDownCompCoords.x, 
                                         currCoords.y - mouseDownCompCoords.y);
                    }
                }
            });
            
            // Panel central para la imagen del título
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            
            // Cargar imagen del título
            URL tituloUrl = POOBkemonGUI.class.getResource("/resources/titulo.png");
            if(tituloUrl == null) {
                throw new Exception("No se encontró la imagen en /resources/titulo.png");
            }
            
            tituloIcon = new ImageIcon(tituloUrl);
            tituloLabel = new JLabel();
            
            // Redimensionar el título inicialmente
            resizeTitleImage(frameWidth);
            
            centerPanel.add(tituloLabel);
            
            // Añadir componentes al panel principal
            mainPanel.add(titleBar, BorderLayout.NORTH);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            
            // Añadir panel principal al frame
            frame.setContentPane(backgroundPanel);
            backgroundPanel.add(mainPanel, BorderLayout.CENTER);
            
            // Listener para el redimensionamiento de la ventana
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int width = frame.getWidth();
                    if (!isMaximized && !isInOptionsScreen) {
                        // Solo redimensionar si no está maximizado
                        // (la maximización ya tiene su propio redimensionamiento)
                        resizeTitleImage(width);
                    }
                    // Repintar el fondo para ajustar la imagen
                    backgroundPanel.repaint();
                }
            });
            
            // Listener para clics en el panel central - cambia a la pantalla de opciones
            centerPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switchToOptionsScreen(mainPanel, centerPanel);
                }
            });
            
            // Listener para clics en el panel de fondo - también cambia a opciones
            backgroundPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Solo procesar el clic si no proviene del área de la barra de título
                    if (e.getY() > titleBar.getHeight()) {
                        switchToOptionsScreen(mainPanel, centerPanel);
                    }
                }
            });
            
            // Mostrar el frame
            frame.setVisible(true);

        } catch (Exception e) {
            System.err.println("Error crítico: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    // Método para redimensionar la imagen del título con moderación
    private static void resizeTitleImage(int frameWidth) {
        if (tituloIcon != null && tituloLabel != null) {
            // Imagen original
            Image originalImg = tituloIcon.getImage();
            
            // Para ventana normal: 1/3 del ancho
            // Para ventana maximizada: 1/4 del ancho (más moderado)
            float factor = isMaximized ? 4.0f : 3.0f;
            
            // Calcula el nuevo ancho, pero con límite para no hacer el título demasiado grande
            int maxWidth = 800; // Tamaño máximo del título
            int newWidth = Math.min((int)(frameWidth / factor), maxWidth);
            
            // Calcula el alto proporcional
            int newHeight = (newWidth * tituloIcon.getIconHeight()) / tituloIcon.getIconWidth();
            
            // Crea la imagen redimensionada
            Image scaledImage = originalImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            tituloLabel.setIcon(new ImageIcon(scaledImage));
        }
    }
    
    // Método para cambiar a la pantalla de opciones
    private static void switchToOptionsScreen(JPanel mainPanel, JPanel centerPanel) {
        // Solo cambiar si no estamos ya en la pantalla de opciones
        if (!isInOptionsScreen) {
            try {
                // Cambiar la imagen de fondo
                URL opcionesUrl = POOBkemonGUI.class.getResource("/resources/opciones.gif");
                if (opcionesUrl != null) {
                    currentBackgroundUrl = opcionesUrl;
                    
                    // Remover la imagen del título
                    if (tituloLabel != null) {
                        centerPanel.remove(tituloLabel);
                    }
                    
                    // Marcamos que estamos en la pantalla de opciones
                    isInOptionsScreen = true;
                    
                    // Repintar todo
                    centerPanel.revalidate();
                    centerPanel.repaint();
                    backgroundPanel.repaint();
                }
            } catch (Exception e) {
                System.err.println("Error al cambiar a la pantalla de opciones: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}