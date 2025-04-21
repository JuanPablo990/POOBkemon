package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class VentanaOpciones extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnPvP, btnPvM, btnMvM, btnCreditos;

    // Constants for image paths
    private static final String PVP_IMAGE = "/resources/pvp.png";
    private static final String PVM_IMAGE = "/resources/pvm.png";
    private static final String MVM_IMAGE = "/resources/mvm.png";
    private static final String CREDITOS_IMAGE = "/resources/creditos.png";
    private static final String BACKGROUND_IMAGE = "/resources/opciones.gif";
    private static final String MODO_BACKGROUND = "/resources/modo.gif";
    private static final String NORMAL_IMAGE = "/resources/Normal.png";
    private static final String SUPERVIVENCIA_IMAGE = "/resources/Supervivencia.png";

    public VentanaOpciones() {
        super("Opciones de POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        // Configurar el fondo principal
        fondoPanel = new FondoPanel(BACKGROUND_IMAGE);
        setContentPane(fondoPanel);
        fondoPanel.setLayout(new BorderLayout());
        
        // Panel para los botones centrales (PvP, PvM, MvM)
        JPanel panelBotonesCentrales = new JPanel();
        panelBotonesCentrales.setOpaque(false);
        panelBotonesCentrales.setLayout(new GridLayout(3, 1, 0, 20));
        panelBotonesCentrales.setBorder(new EmptyBorder(50, 0, 50, 0));

        // Tamaño para los botones centrales
        Dimension tamanoBotonesCentrales = new Dimension(200, 80);
        
        // Crear botones centrales con imágenes
        btnPvP = crearBotonTransparente(PVP_IMAGE, tamanoBotonesCentrales, "PvP");
        btnPvM = crearBotonTransparente(PVM_IMAGE, tamanoBotonesCentrales, "PvM");
        btnMvM = crearBotonTransparente(MVM_IMAGE, tamanoBotonesCentrales, "MvM");
        
        panelBotonesCentrales.add(btnPvP);
        panelBotonesCentrales.add(btnPvM);
        panelBotonesCentrales.add(btnMvM);

        // Panel para centrar los botones centrales
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panelBotonesCentrales);

        // Panel para el botón de créditos (abajo a la izquierda)
        JPanel panelCreditos = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelCreditos.setOpaque(false);
        
        Dimension tamanoCreditos = new Dimension(100, 50);
        btnCreditos = crearBotonTransparente(CREDITOS_IMAGE, tamanoCreditos, "Créditos");
        panelCreditos.add(btnCreditos);

        // Añadir componentes al layout principal
        fondoPanel.add(panelCentro, BorderLayout.CENTER);
        fondoPanel.add(panelCreditos, BorderLayout.SOUTH);
    }

    private JButton crearBotonTransparente(String imagenPath, Dimension size, String textoAlternativo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.drawString(textoAlternativo, getWidth()/2 - 30, getHeight()/2);
                }
            }
        };
        
        boton.setPreferredSize(size);
        
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(
                size.width, 
                size.height, 
                Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + imagenPath);
            boton.setText(textoAlternativo);
        }
        
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.setMargin(new Insets(0, 0, 0, 0));
        
        return boton;
    }

    private void configurarListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int baseWidth = getWidth() / 6;
                Dimension nuevoTamanoCentral = new Dimension(baseWidth, baseWidth / 2);
                Dimension nuevoTamanoCreditos = new Dimension(baseWidth / 2, baseWidth / 4);
                
                btnPvP.setPreferredSize(nuevoTamanoCentral);
                btnPvM.setPreferredSize(nuevoTamanoCentral);
                btnMvM.setPreferredSize(nuevoTamanoCentral);
                btnCreditos.setPreferredSize(nuevoTamanoCreditos);
                
                escalarImagenBoton(btnPvP, PVP_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnPvM, PVM_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnMvM, MVM_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnCreditos, CREDITOS_IMAGE, nuevoTamanoCreditos);
                
                revalidate();
                repaint();
            }
        });
        
        btnPvP.addActionListener(e -> mostrarVentanaModo("Player vs Player"));
        btnPvM.addActionListener(e -> mostrarVentanaModo("Player vs Machine"));
        btnMvM.addActionListener(e -> mostrarVentanaModo("Machine vs Machine"));
        
        btnCreditos.addActionListener(e -> {
            this.setVisible(false);
            POOBkemonGUI.mostrarVentanaCreditos();
        });
    }

    private void mostrarVentanaModo(String titulo) {
        JDialog dialog = new JDialog(this, titulo, true);
        dialog.setSize(500, 300); // Tamaño más compacto del diálogo
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        FondoPanel fondoDialog = new FondoPanel(MODO_BACKGROUND);
        fondoDialog.setLayout(new BorderLayout());
        dialog.setContentPane(fondoDialog);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        fondoDialog.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setOpaque(false);

        // Tamaño más pequeño y rectangular
        Dimension tamanoBotones = new Dimension(150, 60);

        JButton btnNormal = crearBotonDialogo(NORMAL_IMAGE, tamanoBotones, "Normal");
        JButton btnSupervivencia = crearBotonDialogo(SUPERVIVENCIA_IMAGE, tamanoBotones, "Supervivencia");

        btnNormal.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Modo Normal seleccionado para " + titulo);
            dialog.dispose();
            // lógica para iniciar modo normal...
        });

        btnSupervivencia.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Modo Supervivencia seleccionado para " + titulo);
            dialog.dispose();
            // lógica para iniciar modo supervivencia...
        });

        panelBotones.add(btnNormal);
        panelBotones.add(btnSupervivencia);
        fondoDialog.add(panelBotones, BorderLayout.CENTER);

        dialog.setVisible(true);
    }


    private JButton crearBotonDialogo(String imagenPath, Dimension size, String textoAlternativo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.drawString(textoAlternativo, getWidth()/2 - 30, getHeight()/2);
                }
            }
        };
        
        boton.setPreferredSize(size);
        
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(
                size.width, 
                size.height, 
                Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + imagenPath);
            boton.setText(textoAlternativo);
        }
        
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return boton;
    }

    private void escalarImagenBoton(JButton boton, String imagenPath, Dimension size) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(
                size.width,
                size.height,
                Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al escalar imagen: " + imagenPath);
        }
    }

    private void volverAVentanaInicio() {
        this.dispose();
        POOBkemonGUI.mostrarVentanaInicio();
    }

    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Función Nuevo en Opciones");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir configuración", 
                         new String[]{"cfg"}, 
                         "Archivos de configuración (*.cfg)",
                         e -> JOptionPane.showMessageDialog(this, "Configuración cargada"));
    }

    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar configuración", 
                         new String[]{"cfg"}, 
                         "Archivos de configuración (*.cfg)",
                         e -> JOptionPane.showMessageDialog(this, "Configuración guardada"));
    }

    @Override
    protected void accionExportar() {
        mostrarFileChooser("Exportar opciones", 
                         new String[]{"opt"}, 
                         "Archivos de opciones (*.opt)",
                         e -> JOptionPane.showMessageDialog(this, "Opciones exportadas"));
    }

    @Override
    protected void accionImportar() {
        mostrarFileChooser("Importar opciones", 
                         new String[]{"opt"}, 
                         "Archivos de opciones (*.opt)",
                         e -> JOptionPane.showMessageDialog(this, "Opciones importadas"));
    }

    public void mostrar() {
        setVisible(true);
    }
}