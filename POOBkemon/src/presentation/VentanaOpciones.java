package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaOpciones extends Ventana {
    private FondoPanel fondoPanel;

    public VentanaOpciones() {
        super("Opciones de POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }

    private void inicializarComponentes() {
        // Configurar el fondo con la imagen animada
        fondoPanel = new FondoPanel("/resources/opciones.gif");
        setContentPane(fondoPanel);
        
        // Configurar layout para agregar componentes adicionales si es necesario
        fondoPanel.setLayout(new GridBagLayout());
        
        // Puedes agregar aquí tus componentes de opciones (botones, sliders, etc.)
        // Ejemplo:
        /*
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.addActionListener(e -> volverAVentanaInicio());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        fondoPanel.add(btnVolver, gbc);
        */
    }

    private void configurarListeners() {
        // Listener para clic en cualquier parte del fondo para volver
        fondoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volverAVentanaInicio();
            }
        });
        
        // Listener para redimensionamiento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                fondoPanel.repaint();
            }
        });
    }

    private void volverAVentanaInicio() {
        this.dispose(); // Cierra esta ventana
        VentanaInicio ventanaInicio = new VentanaInicio();
        ventanaInicio.mostrar(); // Muestra la ventana inicial
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