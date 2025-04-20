package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Ventana extends JFrame {
    protected JMenuBar menuBar;
    protected JMenu fileMenu;

    public Ventana(String title) {
        super(title);
        configurarVentana();
        crearMenu();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationRelativeTo(null);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menú");

        agregarItemMenu("Nuevo", e -> accionNuevo());
        agregarItemMenu("Abrir", e -> accionAbrir());
        agregarItemMenu("Guardar", e -> accionGuardar());
        fileMenu.addSeparator();
        agregarItemMenu("Exportar", e -> accionExportar());
        agregarItemMenu("Importar", e -> accionImportar());
        fileMenu.addSeparator();
        agregarItemMenu("Salir", e -> System.exit(0));

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void agregarItemMenu(String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(listener);
        fileMenu.add(item);
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void accionNuevo();
    protected abstract void accionAbrir();
    protected abstract void accionGuardar();
    protected abstract void accionExportar();
    protected abstract void accionImportar();

    protected void mostrarFileChooser(String titulo, String[] extensiones, String descripcion, ActionListener onApprove) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);
        fileChooser.setFileFilter(new FileNameExtensionFilter(descripcion, extensiones));
        
        int result = extensiones[0].equals("pkm") ? 
            fileChooser.showSaveDialog(this) : fileChooser.showOpenDialog(this);
            
        if (result == JFileChooser.APPROVE_OPTION) {
            onApprove.actionPerformed(new ActionEvent(fileChooser, ActionEvent.ACTION_PERFORMED, "fileSelected"));
        }
    }
}