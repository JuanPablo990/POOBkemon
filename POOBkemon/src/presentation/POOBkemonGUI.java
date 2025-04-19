package presentation;

import javax.swing.SwingUtilities;

public class POOBkemonGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventanaInicio = new VentanaInicio();
            ventanaInicio.mostrar(); // Método para hacer visible la ventana
        });
    }
}