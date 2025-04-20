package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class VentanaOpciones extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnPvP, btnPvM, btnMvM, btnCreditos;

    public VentanaOpciones() {
        super("Opciones de POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        // Configurar el fondo principal
        fondoPanel = new FondoPanel("/resources/opciones.gif");
        setContentPane(fondoPanel);
        fondoPanel.setLayout(new BorderLayout());
        
        // Panel para los botones centrales (PvP, PvM, MvM)
        JPanel panelBotonesCentrales = new JPanel();
        panelBotonesCentrales.setOpaque(false);
        panelBotonesCentrales.setLayout(new GridLayout(3, 1, 0, 20));
        panelBotonesCentrales.setBorder(new EmptyBorder(50, 0, 50, 0)); // Espacio arriba y abajo

        // Tamaño para los botones centrales
        Dimension tamanoBotonesCentrales = new Dimension(200, 80);
        
        // Crear botones centrales con imágenes
        btnPvP = crearBotonTransparente("/resources/pvp.png", tamanoBotonesCentrales, "PvP");
        btnPvM = crearBotonTransparente("/resources/pvm.png", tamanoBotonesCentrales, "PvM");
        btnMvM = crearBotonTransparente("/resources/mvm.png", tamanoBotonesCentrales, "MvM");
        
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
        
        // Tamaño para el botón de créditos (más pequeño)
        Dimension tamanoCreditos = new Dimension(100, 50);
        btnCreditos = crearBotonTransparente("/resources/creditos.png", tamanoCreditos, "Créditos");
        panelCreditos.add(btnCreditos);

        // Añadir componentes al layout principal
        fondoPanel.add(panelCentro, BorderLayout.CENTER);
        fondoPanel.add(panelCreditos, BorderLayout.SOUTH);
    }

    private JButton crearBotonTransparente(String imagenPath, Dimension size, String textoAlternativo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                // Dibujar la imagen escalada en todo el área del botón
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    super.paintComponent(g);
                    setText(textoAlternativo);
                }
            }
        };
        
        boton.setPreferredSize(size);
        
        try {
            // Cargar y escalar la imagen exactamente al tamaño del botón
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(
                size.width, 
                size.height, 
                Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            boton.setText(textoAlternativo);
            System.err.println("Error al cargar imagen: " + imagenPath);
        }
        
        // Configuración para hacer el botón completamente transparente
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.setMargin(new Insets(0, 0, 0, 0));
        
        return boton;
    }

    private void configurarListeners() {
        // Listener para redimensionamiento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Calcular nuevos tamaños proporcionales
                int baseWidth = getWidth() / 6;
                Dimension nuevoTamanoCentral = new Dimension(baseWidth, baseWidth / 2);
                Dimension nuevoTamanoCreditos = new Dimension(baseWidth / 2, baseWidth / 4);
                
                // Aplicar nuevos tamaños
                btnPvP.setPreferredSize(nuevoTamanoCentral);
                btnPvM.setPreferredSize(nuevoTamanoCentral);
                btnMvM.setPreferredSize(nuevoTamanoCentral);
                btnCreditos.setPreferredSize(nuevoTamanoCreditos);
                
                // Re-escalar las imágenes
                escalarImagenBoton(btnPvP, "/resources/pvp.png", nuevoTamanoCentral);
                escalarImagenBoton(btnPvM, "/resources/pvm.png", nuevoTamanoCentral);
                escalarImagenBoton(btnMvM, "/resources/mvm.png", nuevoTamanoCentral);
                escalarImagenBoton(btnCreditos, "/resources/creditos.png", nuevoTamanoCreditos);
                
                revalidate();
                repaint();
            }
        });
        
        // Listeners para los botones
        btnPvP.addActionListener(e -> JOptionPane.showMessageDialog(this, "Player vs Player seleccionado"));
        btnPvM.addActionListener(e -> JOptionPane.showMessageDialog(this, "Player vs Machine seleccionado"));
        btnMvM.addActionListener(e -> JOptionPane.showMessageDialog(this, "Machine vs Machine seleccionado"));
        btnCreditos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Créditos del juego"));
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

    // Resto de métodos de la clase Ventana...
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