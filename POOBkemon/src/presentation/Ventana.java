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
        if (ventanasAbiertas.size() == 1) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            currentSize = new Dimension((int)(screenSize.width * 0.7), (int)(screenSize.height * 0.7));
        }
        setSize(currentSize);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
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
        agregarItemMenuConIcono("Nuevo", "/resources/nuevo_icon.png", KeyEvent.VK_N, e -> {
            POOBkemonGUI.reiniciarAplicacion();
        });
        agregarItemMenuConIcono("Abrir", "/resources/abrir_icon.png", KeyEvent.VK_A, e -> {
            mostrarFileChooser("Abrir Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        agregarItemMenuConIcono("Guardar", "/resources/guardar_icon.png", KeyEvent.VK_G, e -> {
            mostrarFileChooser("Guardar Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
        fileMenu.addSeparator();
        agregarItemMenuConIcono("Exportar", "/resources/exportar_icon.png", KeyEvent.VK_E, e -> {
            mostrarFileChooser("Exportar Archivo", new String[]{"pkm"}, "Archivos PKM", ev -> {});
        });
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
        item.setAccelerator(KeyStroke.getKeyStroke(keyEvent, KeyEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        fileMenu.add(item);
        return item;
    }

    protected void mostrarFileChooser(String titulo, String[] extensiones, String descripcion, ActionListener onApprove) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter(descripcion, extensiones));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int result = extensiones[0].equals("pkm") ? fileChooser.showSaveDialog(this) : fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            onApprove.actionPerformed(new ActionEvent(fileChooser, ActionEvent.ACTION_PERFORMED, "fileSelected"));
        }
    }
    
    @Override
    public void dispose() {
        ventanasAbiertas.remove(this);
        super.dispose();
    }

    protected abstract void accionNuevo();
    protected abstract void accionAbrir();
    protected abstract void accionGuardar();
    protected abstract void accionExportar();
    protected abstract void accionImportar();

    protected void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}