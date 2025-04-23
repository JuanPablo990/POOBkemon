package presentation;

import javax.swing.*;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;
    private static VentanaCreditos ventanaCreditos;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarVentanaInicio();
        });
    }

    public static void reiniciarAplicacion() {
        // Cierra todas las ventanas existentes
        if (ventanaInicio != null) {
            ventanaInicio.dispose();
            ventanaInicio = null;
        }
        if (ventanaOpciones != null) {
            ventanaOpciones.dispose();
            ventanaOpciones = null;
        }
        if (ventanaCreditos != null) {
            ventanaCreditos.dispose();
            ventanaCreditos = null;
        }
        
        // Vuelve a mostrar la ventana de inicio
        mostrarVentanaInicio();
    }

    public static void mostrarVentanaInicio() {
        if (ventanaInicio == null) {
            ventanaInicio = new VentanaInicio();
        }
        ocultarOtrasVentanas(ventanaInicio);
        ventanaInicio.mostrar();
    }

    public static void mostrarVentanaOpciones() {
        if (ventanaOpciones == null) {
            ventanaOpciones = new VentanaOpciones();
        }
        ocultarOtrasVentanas(ventanaOpciones);
        ventanaOpciones.mostrar();
    }

    public static void mostrarVentanaCreditos() {
        if (ventanaCreditos == null) {
            ventanaCreditos = new VentanaCreditos();
        }
        ocultarOtrasVentanas(ventanaCreditos);
        ventanaCreditos.mostrar();
    }

    private static void ocultarOtrasVentanas(JFrame ventanaActual) {
        if (ventanaInicio != null && ventanaInicio != ventanaActual && ventanaInicio.isVisible()) {
            ventanaInicio.setVisible(false);
        }
        if (ventanaOpciones != null && ventanaOpciones != ventanaActual && ventanaOpciones.isVisible()) {
            ventanaOpciones.setVisible(false);
        }
        if (ventanaCreditos != null && ventanaCreditos != ventanaActual && ventanaCreditos.isVisible()) {
            ventanaCreditos.setVisible(false);
        }
    }
}