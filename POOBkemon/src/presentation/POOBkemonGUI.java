package presentation;

import javax.swing.SwingUtilities;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;
    private static VentanaCreditos ventanaCreditos;

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
        if (ventanaCreditos != null && ventanaCreditos.isVisible()) {
            ventanaCreditos.setVisible(false);
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
        if (ventanaCreditos != null && ventanaCreditos.isVisible()) {
            ventanaCreditos.setVisible(false);
        }
        ventanaOpciones.mostrar();
    }

    public static void mostrarVentanaCreditos() {
        if (ventanaCreditos == null) {
            ventanaCreditos = new VentanaCreditos();
        }
        if (ventanaOpciones != null && ventanaOpciones.isVisible()) {
            ventanaOpciones.setVisible(false);
        }
        ventanaCreditos.mostrar();
    }
}