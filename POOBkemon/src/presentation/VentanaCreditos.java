package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.border.EmptyBorder;

public class VentanaCreditos extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JLabel lblCreditos;
    private Timer timer;
    private int yPos;
    private final int velocidad = 2;

    public VentanaCreditos() {
        super("Créditos de POOBkemon");
        detenerAnimacion();
        inicializarComponentes();
        configurarListeners();
        iniciarAnimacionCreditos();
    }

    private void detenerAnimacion() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

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

    private void volverAVentanaOpciones() {
        detenerAnimacion();
        dispose();
        POOBkemonGUI.mostrarVentanaOpciones();
    }

    @Override
    public void dispose() {
        detenerAnimacion();
        super.dispose();
    }

    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nuevo juego desde créditos");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir desde créditos",new String[]{"sav"}, "Archivos de guardado (*.sav)",e -> JOptionPane.showMessageDialog(this, "Partida cargada desde créditos"));
    }

    @Override
    protected void accionGuardar() {
        JOptionPane.showMessageDialog(this, "Guardar desde créditos");
    }

    @Override
    protected void accionExportar() {
        JOptionPane.showMessageDialog(this, "Exportar desde créditos");
    }

    @Override
    protected void accionImportar() {
        JOptionPane.showMessageDialog(this, "Importar desde créditos");
    }

    public void mostrar() {
        setVisible(true);
        iniciarAnimacionCreditos();
    }
}