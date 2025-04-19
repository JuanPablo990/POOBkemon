package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class POOBkemonGUI {

    public static void main(String[] args) {
        // Crear el JFrame
        JFrame frame = new JFrame("POOBkemon");
        
        // Obtener las dimensiones de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        // Calcular el tamaño del frame (1/4 de la pantalla)
        int frameWidth = screenWidth / 2;
        int frameHeight = screenHeight / 2;
        
        // Calcular la posición para centrar el frame
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        
        // Configurar el frame
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        // Crear el menú "Archivo"
        JMenu fileMenu = new JMenu("Menu");
        
        // Crear los items del menú (opciones principales)
        JMenuItem newItem = new JMenuItem("Nuevo");
        JMenuItem saveItem = new JMenuItem("Guardar");
        JMenuItem openItem = new JMenuItem("Abrir");
        JMenuItem exportItem = new JMenuItem("Exportar");
        JMenuItem importItem = new JMenuItem("Importar");
        JMenuItem exitItem = new JMenuItem("Salir");
        
        // Agregar acción al item Salir
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Agregar items al menú Archivo
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // Línea separadora
        fileMenu.add(exportItem);
        fileMenu.add(importItem);
        fileMenu.addSeparator(); // Línea separadora
        fileMenu.add(exitItem);
        
        // Agregar el menú Archivo a la barra de menú
        menuBar.add(fileMenu);
        
        // Configurar la barra de menú en el frame
        frame.setJMenuBar(menuBar);
        
        // Mostrar el frame
        frame.setVisible(true);
    }
}