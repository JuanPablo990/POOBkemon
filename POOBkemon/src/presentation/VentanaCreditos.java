package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.border.EmptyBorder;
import java.io.Serializable;

/**
 * VentanaCreditos muestra una ventana con los créditos del juego POOBkemon,
 * incluyendo animación de desplazamiento vertical del texto y un botón para volver.
 */

public class VentanaCreditos extends Ventana implements Serializable {
    private static final long serialVersionUID = 1L;
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JLabel lblCreditos;
    private Timer timer;
    private int yPos;
    private final int velocidad = 2;

    /**
     * Constructor de la clase VentanaCreditos.
     */
    public VentanaCreditos() {
        super("Créditos de POOBkemon");
        detenerAnimacion();
        inicializarComponentes();
        configurarListeners();
        iniciarAnimacionCreditos();
    }

    /**
     * Detiene la animación de los créditos si está activa.
     */
    private void detenerAnimacion() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    /**
     * Inicializa y configura los componentes gráficos de la ventana.
     */
    private void inicializarComponentes() {
        fondoPanel = new FondoPanel("/resources/creditos.gif");
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);
        lblCreditos = new JLabel();
        lblCreditos.setForeground(Color.BLACK);
        lblCreditos.setFont(new Font("Arial", Font.BOLD, 24));
        lblCreditos.setHorizontalAlignment(SwingConstants.CENTER);
        lblCreditos.setDoubleBuffered(true);
        actualizarTextoCreditos();
        JPanel panelCreditos = new JPanel(null);
        panelCreditos.setOpaque(false);
        panelCreditos.add(lblCreditos);
        lblCreditos.setBounds(0, getHeight(), getWidth(), 400);
        btnVolver = new JButton();
        ImageIcon volverIcon = loadScaledIcon("/resources/volver.png", 120, 60);
        if (volverIcon != null) {
            btnVolver.setIcon(volverIcon);
        } else {
            btnVolver.setText("Volver");
            System.err.println("Error al cargar imagen de volver");
        }
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setOpaque(false);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        fondoPanel.add(panelCreditos, BorderLayout.CENTER);
        fondoPanel.add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Carga y escala un icono desde un recurso.
     * @param path Ruta del recurso de la imagen.
     * @param width Ancho deseado.
     * @param height Alto deseado.
     * @return ImageIcon escalado o null si falla la carga.
     */
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            ImageIcon original = new ImageIcon(resource);
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            return null;
        }
    }

    /**
     * Actualiza el texto HTML de los créditos en la etiqueta.
     */
    private void actualizarTextoCreditos() {
        String htmlText = "<html><center>" +
                "<h1 style='color:black;text-shadow:1px 1px 3px white;'>POOBkemon</h1><br><br>" +
                "<p style='font-size:20px;'>Creado por:</p>" +
                "<p style='font-size:18px;'>Tomás Felipe Ramírez Álvarez</p>" +
                "<p style='font-size:18px;'>Juan Pablo Nieto Cortés</p><br>" +
                "<p style='font-size:20px;'>Asignatura:</p>" +
                "<p style='font-size:20px;'>Programación Orientada a Objetos</p>" +
                "<p style='font-size:20px;'>Grupo 3</p>" +
                "</center></html>";
        lblCreditos.setText(htmlText);
    }

    /**
     * Inicia la animación de desplazamiento vertical de los créditos.
     */
    private void iniciarAnimacionCreditos() {
        detenerAnimacion();
        yPos = getHeight();
        lblCreditos.setLocation(0, yPos);
        timer = new Timer(30, e -> {
            yPos -= velocidad;
            lblCreditos.setLocation(0, yPos);
            if (yPos < -lblCreditos.getHeight()) {
                yPos = getHeight();
            }
            lblCreditos.repaint();
        });
        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Configura los listeners de los componentes de la ventana.
     */
    private void configurarListeners() {
        btnVolver.addActionListener(e -> {
            volverAVentanaOpciones();
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                lblCreditos.setSize(getWidth(), 400);
                lblCreditos.setFont(new Font("Arial", Font.BOLD, getWidth()/25));
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detenerAnimacion();
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
                iniciarAnimacionCreditos();
            }
            @Override
            public void windowIconified(WindowEvent e) {
                detenerAnimacion();
            }
        });
    }

    /**
     * Vuelve a la ventana de opciones principal.
     */
    private void volverAVentanaOpciones() {
        detenerAnimacion();
        dispose();
        POOBkemonGUI.mostrarVentanaOpciones();
    }

    /**
     * Libera los recursos y detiene la animación al cerrar la ventana.
     */
    @Override
    public void dispose() {
        detenerAnimacion();
        super.dispose();
    }

    /**
     * Acción para nuevo juego desde la ventana de créditos.
     */
    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nuevo juego desde créditos");
    }

    /**
     * Acción para abrir archivo desde la ventana de créditos.
     */
    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir desde créditos",new String[]{"sav"}, "Archivos de guardado (*.sav)",e -> JOptionPane.showMessageDialog(this, "Partida cargada desde créditos"));
    }

    /**
     * Acción para guardar desde la ventana de créditos.
     */
    @Override
    protected void accionGuardar() {
        JOptionPane.showMessageDialog(this, "Guardar desde créditos");
    }

    /**
     * Acción para exportar desde la ventana de créditos.
     */
    @Override
    protected void accionExportar() {
        JOptionPane.showMessageDialog(this, "Exportar desde créditos");
    }

    /**
     * Acción para importar desde la ventana de créditos.
     */
    @Override
    protected void accionImportar() {
        JOptionPane.showMessageDialog(this, "Importar desde créditos");
    }

    /**
     * Muestra la ventana de créditos y reinicia la animación.
     */
    public void mostrar() {
        setVisible(true);
        iniciarAnimacionCreditos();
    }
}
