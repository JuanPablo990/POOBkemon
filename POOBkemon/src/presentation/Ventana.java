package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Ventana extends JFrame {
    protected JMenuBar menuBar;
    protected JMenu fileMenu;
    
    // Variables estáticas para controlar el tamaño de todas las ventanas
    private static Dimension currentSize = new Dimension();
    private static List<Ventana> ventanasAbiertas = new ArrayList<>();
    
    public Ventana(String title) {
        super(title);
        ventanasAbiertas.add(this);
        configurarVentana();
        crearMenu();
    }
    
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Establecer tamaño inicial si es la primera ventana
        if (ventanasAbiertas.size() == 1) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            currentSize = new Dimension((int)(screenSize.width * 0.7), (int)(screenSize.height * 0.7));
        }
        
        setSize(currentSize);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600)); // Tamaño mínimo
        
        // Listener para cambios de tamaño
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = getSize();
                if (!newSize.equals(currentSize)) {
                    currentSize = newSize;
                    actualizarTamanioVentanas();
                }
            }
        });
    }
    
    private static void actualizarTamanioVentanas() {
        for (Ventana ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                ventana.setSize(currentSize);
                ventana.revalidate();
                ventana.repaint();
            }
        }
    }
    
    private void crearMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menú");

        // Opción Nuevo
        agregarItemMenuConIcono("Nuevo", "/resources/nuevo_icon.png", KeyEvent.VK_N, e -> {
            POOBkemonGUI.reiniciarAplicacion();
        });
        
        // Opción Abrir - muestra JFileChooser
        agregarItemMenuConIcono("Abrir", "/resources/abrir_icon.png", KeyEvent.VK_A, e -> {
            mostrarFileChooser("Abrir Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        
        // Opción Guardar - muestra JFileChooser
        agregarItemMenuConIcono("Guardar", "/resources/guardar_icon.png", KeyEvent.VK_G, e -> {
            mostrarFileChooser("Guardar Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        
        fileMenu.addSeparator();
        
        // Opción Exportar - muestra JFileChooser
        agregarItemMenuConIcono("Exportar", "/resources/exportar_icon.png", KeyEvent.VK_E, e -> {
            mostrarFileChooser("Exportar Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        
        // Opción Importar - muestra JFileChooser
        agregarItemMenuConIcono("Importar", "/resources/importar_icon.png", KeyEvent.VK_I, e -> {
            mostrarFileChooser("Importar Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        
        fileMenu.addSeparator();
        agregarItemMenuConIcono("Salir", "/resources/salir_icon.png", KeyEvent.VK_S, e -> System.exit(0));

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private JMenuItem agregarItemMenuConIcono(String texto, String iconPath, int keyEvent, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        
        // Configurar atajo de teclado
        item.setAccelerator(KeyStroke.getKeyStroke(keyEvent, KeyEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        fileMenu.add(item);
        
        return item;
    }

    protected void mostrarFileChooser(String titulo, String[] extensiones, String descripcion, ActionListener onApprove) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter(descripcion, extensiones));
        
        // Configuración mejorada del file chooser
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        
        int result = extensiones[0].equals("pkm") ? 
            fileChooser.showSaveDialog(this) : fileChooser.showOpenDialog(this);
            
        if (result == JFileChooser.APPROVE_OPTION) {
            onApprove.actionPerformed(new ActionEvent(fileChooser, ActionEvent.ACTION_PERFORMED, "fileSelected"));
        }
    }
    
    @Override
    public void dispose() {
        ventanasAbiertas.remove(this);
        super.dispose();
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void accionNuevo();
    protected abstract void accionAbrir();
    protected abstract void accionGuardar();
    protected abstract void accionExportar();
    protected abstract void accionImportar();

    protected void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}