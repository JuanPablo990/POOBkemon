package presentation;

import javax.swing.SwingUtilities;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarVentanaInicio();
        });
    }

    public static void mostrarVentanaInicio() {
        if (ventanaInicio == null) {
            ventanaInicio = new VentanaInicio();
        }
        if (ventanaOpciones != null && ventanaOpciones.isVisible()) {
            ventanaOpciones.setVisible(false);
        }
        ventanaInicio.mostrar();
    }

    public static void mostrarVentanaOpciones() {
        if (ventanaOpciones == null) {
            ventanaOpciones = new VentanaOpciones();
        }
        if (ventanaInicio != null && ventanaInicio.isVisible()) {
            ventanaInicio.setVisible(false);
        }
        ventanaOpciones.mostrar();
    }
}