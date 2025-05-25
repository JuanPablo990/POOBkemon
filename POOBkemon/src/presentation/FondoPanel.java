package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * FondoPanel es un JPanel personalizado que muestra una imagen de fondo.
 * Puede ser utilizado para establecer un fondo en paneles de una aplicación Swing.
 */
public class FondoPanel extends JPanel {
    /** Imagen de fondo que se mostrará */
    private Image imagen;

    /**
     * Construye un FondoPanel con la imagen de fondo predeterminada.
     * La ruta de la imagen por defecto es "/images/menu.png".
     */
    public FondoPanel() {
        this("/images/menu.png");
    }

    /**
     * Construye un FondoPanel con una ruta de imagen especificada.
     * @param imagePath la ruta del recurso de la imagen de fondo
     */
    public FondoPanel(String imagePath) {
        try {
            this.imagen = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen de fondo: " + imagePath);
        }
    }

    /**
     * Pinta la imagen de fondo escalada al tamaño del panel.
     * @param g el contexto gráfico en el que se pinta
     */
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
        setOpaque(false);
        super.paint(g);
    }

    /**
     * Retorna la imagen de fondo utilizada por este panel.
     * @return la imagen de fondo
     */
    public Image getImagen() {
        return imagen;
    }
}
