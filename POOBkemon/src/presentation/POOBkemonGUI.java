package presentation;

import javax.swing.*;

public class POOBkemonGUI {
    private static VentanaInicio ventanaInicio;
    private static VentanaOpciones ventanaOpciones;
    private static VentanaCreditos ventanaCreditos;
    private static VentanaSeleccion ventanaSeleccion;
    private static VentanaMovimientos ventanaMovimientos;

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
        if (ventanaSeleccion != null) {
            ventanaSeleccion.dispose();
            ventanaSeleccion = null;
        }
        if (ventanaMovimientos != null) {
            ventanaMovimientos.dispose();
            ventanaMovimientos = null;
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

    public static void mostrarVentanaSeleccion() {
        if (ventanaSeleccion == null) {
            ventanaSeleccion = new VentanaSeleccion();
        }
        ocultarOtrasVentanas(ventanaSeleccion);
        ventanaSeleccion.mostrar();
    }

    public static void mostrarVentanaMovimientos() {
        if (ventanaMovimientos == null) {
            ventanaMovimientos = new VentanaMovimientos();
        }
        ocultarOtrasVentanas(ventanaMovimientos);
        ventanaMovimientos.mostrar();
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
        if (ventanaSeleccion != null && ventanaSeleccion != ventanaActual && ventanaSeleccion.isVisible()) {
            ventanaSeleccion.setVisible(false);
        }
        if (ventanaMovimientos != null && ventanaMovimientos != ventanaActual && ventanaMovimientos.isVisible()) {
            ventanaMovimientos.setVisible(false);
        }
    }

    // Métodos para ocultar ventanas específicas si son necesarios
    public static void ocultarVentanaInicio() {
        if (ventanaInicio != null) {
            ventanaInicio.setVisible(false);
        }
    }

    public static void ocultarVentanaOpciones() {
        if (ventanaOpciones != null) {
            ventanaOpciones.setVisible(false);
        }
    }

    public static void ocultarVentanaCreditos() {
        if (ventanaCreditos != null) {
            ventanaCreditos.setVisible(false);
        }
    }

    public static void ocultarVentanaSeleccion() {
        if (ventanaSeleccion != null) {
            ventanaSeleccion.setVisible(false);
        }
    }

    public static void ocultarVentanaMovimientos() {
        if (ventanaMovimientos != null) {
            ventanaMovimientos.setVisible(false);
        }
    }
}