package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Ventana para mostrar información de depuración de POOBkemonGUI.
 */
public class VentanaDebug extends JFrame {
    private JTextArea areaTexto;

    /**
     * Crea una nueva ventana de depuración.
     */
    public VentanaDebug() {
        setTitle("Debug de POOBkemonGUI");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        cargarDatos();
    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     */
    private void initComponents() {
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Carga y muestra los datos de los jugadores en el área de texto.
     */
    private void cargarDatos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== JUGADOR 1 ===\n");
        if (POOBkemonGUI.getJugador1() != null) {
            sb.append("Nombre: ").append(POOBkemonGUI.getJugador1().getNombre()).append("\n");
            sb.append("Pokémon seleccionados: ").append(POOBkemonGUI.getSeleccionPokemonJugador1()).append("\n");
            sb.append("Ítems:\n");
            for (Object item : POOBkemonGUI.getJugador1().getItems()) {
                sb.append(" - ").append(item.getClass().getSimpleName()).append("\n");
            }
            sb.append("Movimientos:\n");
            for (Map.Entry<String, List<String>> entry : POOBkemonGUI.getMovimientosJugador1().entrySet()) {
                sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        } else {
            sb.append("No inicializado\n");
        }
        sb.append("\n=== JUGADOR 2 ===\n");
        if (POOBkemonGUI.getJugador2() != null) {
            sb.append("Nombre: ").append(POOBkemonGUI.getJugador2().getNombre()).append("\n");
            sb.append("Pokémon seleccionados: ").append(POOBkemonGUI.getSeleccionPokemonJugador2()).append("\n");
            sb.append("Ítems:\n");
            for (Object item : POOBkemonGUI.getJugador2().getItems()) {
                sb.append(" - ").append(item.getClass().getSimpleName()).append("\n");
            }
            sb.append("Movimientos:\n");
            for (Map.Entry<String, List<String>> entry : POOBkemonGUI.getMovimientosJugador2().entrySet()) {
                sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        } else {
            sb.append("No inicializado\n");
        }
        areaTexto.setText(sb.toString());
    }
}
