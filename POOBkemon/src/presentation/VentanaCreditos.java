package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class VentanaCreditos extends Ventana {
    private FondoPanel fondoPanel;
    private JButton btnVolver;
    private JLabel lblCreditos;
    private Timer timer;
    private int yPos;
    private final int velocidad = 2; // Velocidad aumentada

    public VentanaCreditos() {
        super("Créditos de POOBkemon");
        inicializarComponentes();
        configurarListeners();
        iniciarAnimacionCreditos(); // Comienza inmediatamente
    }

    private void inicializarComponentes() {
        fondoPanel = new FondoPanel("/resources/creditos.gif");
        fondoPanel.setLayout(new BorderLayout());
        setContentPane(fondoPanel);

        // Configurar el texto de créditos
        lblCreditos = new JLabel();
        lblCreditos.setForeground(Color.BLACK);
        lblCreditos.setFont(new Font("Arial", Font.BOLD, 24));
        lblCreditos.setHorizontalAlignment(SwingConstants.CENTER);
        actualizarTextoCreditos();

        JPanel panelCreditos = new JPanel(null);
        panelCreditos.setOpaque(false);
        panelCreditos.add(lblCreditos);
        lblCreditos.setBounds(0, getHeight(), getWidth(), 400);

        // Botón volver con imagen
        btnVolver = new JButton();
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/resources/volver.png"));
            Image img = iconoOriginal.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            btnVolver.setIcon(new ImageIcon(img));
        } catch (Exception e) {
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

    private void actualizarTextoCreditos() {
        String htmlText = "<html><center>" +
                "<h1 style='color:black;text-shadow:1px 1px 3px white;'>POOBkemon</h1><br><br>" +
                "<p style='font-size:20px;'>Creado por:</p>" +
                "<p style='font-size:18px;'>Tomás Felipe Ramírez Álvarez</p>" +
                "<p style='font-size:18px;'>Juan Pablo Nieto Cortés</p><br>" +
                "<p style='font-size:20px;'>Programación Orientada a Objetos</p>" +
                "<p style='font-size:20px;'>Grupo 3</p>" + // Grupo 3 añadido aquí
                "</center></html>";
        lblCreditos.setText(htmlText);
    }

    private void iniciarAnimacionCreditos() {
        yPos = getHeight(); // Comienza abajo de la pantalla
        
        // Timer principal para la animación (comienza inmediatamente)
        timer = new Timer(30, e -> {
            yPos -= velocidad;
            lblCreditos.setLocation(0, yPos);
            
            if (yPos < -lblCreditos.getHeight()) {
                yPos = getHeight(); // Reinicia posición
            }
        });
        timer.start();
    }

    private void configurarListeners() {
        btnVolver.addActionListener(e -> {
            if (timer != null) timer.stop();
            volverAVentanaOpciones();
        });
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                lblCreditos.setSize(getWidth(), 400);
                lblCreditos.setFont(new Font("Arial", Font.BOLD, getWidth()/25));
            }
        });
    }

    private void volverAVentanaOpciones() {
        dispose();
        POOBkemonGUI.mostrarVentanaOpciones();
    }

    @Override
    public void dispose() {
        if (timer != null) timer.stop();
        super.dispose();
    }

    // Resto de métodos abstractos...
    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Nuevo juego desde créditos");
    }

    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir desde créditos", 
            new String[]{"sav"}, "Archivos de guardado (*.sav)",
            e -> JOptionPane.showMessageDialog(this, "Partida cargada desde créditos"));
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
        yPos = getHeight();
    }
}