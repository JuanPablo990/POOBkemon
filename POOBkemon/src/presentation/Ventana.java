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

    /**
     * Constructor de la clase Ventana.
     * @param title Título de la ventana.
     */
    public Ventana(String title) {
        super(title);
        ventanasAbiertas.add(this);
        configurarVentana();
        crearMenu();
    }

    /**
     * Configura las propiedades iniciales de la ventana.
     */
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

    /**
     * Actualiza el tamaño de todas las ventanas abiertas.
     */
    private static void actualizarTamanioVentanas() {
        for (Ventana ventana : ventanasAbiertas) {
            if (ventana.isVisible()) {
                ventana.setSize(currentSize);
                ventana.revalidate();
                ventana.repaint();
            }
        }
    }

    /**
     * Crea el menú de la ventana.
     */
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

    /**
     * Agrega un ítem al menú con icono y atajo de teclado.
     * @param texto Texto del ítem.
     * @param iconPath Ruta del icono.
     * @param keyEvent Tecla de acceso rápido.
     * @param listener Acción al seleccionar el ítem.
     * @return El JMenuItem creado.
     */
    private JMenuItem agregarItemMenuConIcono(String texto, String iconPath, int keyEvent, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.setAccelerator(KeyStroke.getKeyStroke(keyEvent, KeyEvent.CTRL_DOWN_MASK));
        item.addActionListener(listener);
        fileMenu.add(item);
        return item;
    }

    /**
     * Muestra un selector de archivos personalizado.
     * @param titulo Título del diálogo.
     * @param extensiones Extensiones permitidas.
     * @param descripcion Descripción del filtro.
     * @param onApprove Acción al aprobar la selección.
     */
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

    /**
     * Elimina la ventana de la lista de abiertas y libera recursos.
     */
    @Override
    public void dispose() {
        ventanasAbiertas.remove(this);
        super.dispose();
    }

    /**
     * Acción para el menú "Nuevo".
     */
    protected abstract void accionNuevo();

    /**
     * Acción para el menú "Abrir".
     */
    protected abstract void accionAbrir();

    /**
     * Acción para el menú "Guardar".
     */
    protected abstract void accionGuardar();

    /**
     * Acción para el menú "Exportar".
     */
    protected abstract void accionExportar();

    /**
     * Acción para el menú "Importar".
     */
    protected abstract void accionImportar();

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título del cuadro.
     * @param tipo Tipo de mensaje.
     */
    protected void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
