package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import domain.*;

public class VentanaBatalla extends Ventana {

    private JPanel panelImagenPokemon;
    private JPanel panelImagenPokemon2;
    private final int ANCHO_PANEL = 200;
    private final int ALTO_PANEL = 200;
    private FondoPanel panelGif;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JLabel labelJugador;
    private boolean turnoJugador1 = true;

    public VentanaBatalla(List<String> nombresPokemonSeleccionados) {
        super("Batalla POOBkemon");
        configurarVentana();
        inicializarComponentes();
        cargarPokemonesIniciales();
        actualizarVistaJugador();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelGif = new FondoPanel("/resources/pelea.gif");
        panelGif.setLayout(null);

        panelImagenPokemon = new JPanel(new BorderLayout());
        panelImagenPokemon.setSize(ANCHO_PANEL, ALTO_PANEL);
        panelImagenPokemon2 = new JPanel(new BorderLayout());
        panelImagenPokemon2.setSize(ANCHO_PANEL, ALTO_PANEL);

        progressBar1 = new JProgressBar(0, 100);
        progressBar1.setStringPainted(true);
        progressBar1.setForeground(Color.YELLOW);
        progressBar1.setBackground(new Color(0, 0, 0, 0));
        progressBar1.setBorderPainted(false);

        progressBar2 = new JProgressBar(0, 100);
        progressBar2.setStringPainted(true);
        progressBar2.setForeground(Color.BLUE);
        progressBar2.setBackground(new Color(0, 0, 0, 0));
        progressBar2.setBorderPainted(false);

        panelImagenPokemon.add(progressBar1, BorderLayout.SOUTH);
        panelImagenPokemon2.add(progressBar2, BorderLayout.NORTH);

        panelImagenPokemon.setBorder(BorderFactory.createEmptyBorder());
        panelImagenPokemon.setOpaque(false);
        panelImagenPokemon2.setBorder(BorderFactory.createEmptyBorder());
        panelImagenPokemon2.setOpaque(false);

        panelGif.add(panelImagenPokemon);
        panelGif.add(panelImagenPokemon2);
        SwingUtilities.invokeLater(this::actualizarPosicionPanelImagen);

        JPanel panelInferior = new JPanel(new BorderLayout());
        FondoPanel panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        labelJugador = new JLabel("Turno: " + POOBkemonGUI.getJugador1().getNombre(), SwingConstants.CENTER);
        labelJugador.setFont(new Font("Arial", Font.BOLD, 20));
        panelArena.add(labelJugador, BorderLayout.NORTH);

        JButton btnAtaque = crearBotonConImagen("/resources/ataque.png", "Ataque");
        JButton btnCambio = crearBotonConImagen("/resources/cambio.png", "Cambio");
        JButton btnItem = crearBotonConImagen("/resources/item.png", "Item");
        JButton btnHuida = crearBotonConImagen("/resources/huida.png", "Huida");

        btnAtaque.addActionListener(e -> mostrarVentanaAtaque());
        btnCambio.addActionListener(e -> mostrarVentanaCambioPokemon());
        btnItem.addActionListener(e -> mostrarVentanaItem());
        btnHuida.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que quieres huir?\nPerderás la batalla.", 
                "Confirmar huida", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "¡Has huido de la batalla!");
                POOBkemonGUI.reiniciarAplicacion();
            }
        });

        panelArena.add(btnAtaque);
        panelArena.add(btnCambio);
        panelArena.add(btnItem);
        panelArena.add(btnHuida);

        panelInferior.add(panelArena, BorderLayout.CENTER);
        panelPrincipal.add(panelGif, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        setContentPane(panelPrincipal);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarPosicionPanelImagen();
            }
        });
    }

    private void mostrarVentanaAtaque() {
        JDialog ventana = new JDialog(this, "Selecciona un Ataque", true);
        ventana.setLayout(new GridLayout(2, 2, 10, 10));
        ventana.setSize(400, 300);
        ventana.setLocationRelativeTo(this);

        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        List<Movimiento> movimientos = actual.getPokemonActivo().getMovimientos();

        if (movimientos.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "¡No tiene movimientos disponibles!");
            ventana.dispose();
            return;
        }

        for (int i = 0; i < 4; i++) {
            JButton btnAtaque;
            if (i < movimientos.size()) {
                Movimiento m = movimientos.get(i);
                btnAtaque = new JButton("<html><center>" + m.getNombre() + "<br>PP: " + m.getPp() + "/" + m.getPpMaximos() + "</center></html>");
                btnAtaque.setEnabled(m.getPp() > 0);
                btnAtaque.addActionListener(e -> {
                    ejecutarAtaque(m);
                    ventana.dispose();
                });
            } else {
                btnAtaque = new JButton("Ataque " + (i + 1));
                btnAtaque.setEnabled(false);
            }

            btnAtaque.setBackground(verdeAguamarina);
            btnAtaque.setForeground(Color.BLACK);
            btnAtaque.setFocusPainted(false);
            btnAtaque.setFont(new Font("Arial", Font.BOLD, 14));
            ventana.add(btnAtaque);
        }

        ventana.setVisible(true);
    }

    private void ejecutarAtaque(Movimiento movimiento) {
        Entrenador atacante = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        Entrenador defensor = turnoJugador1 ? POOBkemonGUI.getJugador2() : POOBkemonGUI.getJugador1();
        Pokemon objetivo = defensor.getPokemonActivo();
        
        boolean exito = movimiento.ejecutar(atacante.getPokemonActivo(), objetivo, 1.0);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, 
                atacante.getNombre() + " usó " + movimiento.getNombre() + "!",
                "Ataque exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            actualizarVidaPokemon1(POOBkemonGUI.getJugador1().getPokemonActivo().getPs());
            actualizarVidaPokemon2(POOBkemonGUI.getJugador2().getPokemonActivo().getPs());
            
            if (objetivo.estaDebilitado()) {
                JOptionPane.showMessageDialog(this, "¡" + objetivo.getNombre() + " fue debilitado!");
                if (!equipoDebilitado(defensor)) {
                    mostrarVentanaCambioObligatorio(defensor);
                }
            }
            
            turnoJugador1 = !turnoJugador1;
            actualizarVistaJugador();
        }
    }

    private boolean equipoDebilitado(Entrenador entrenador) {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            if (!pokemon.estaDebilitado()) {
                return false;
            }
        }
        return true;
    }

    private void mostrarVentanaCambioPokemon() {
        JDialog ventana = new JDialog(this, "Selecciona un Pokémon", true);
        ventana.setLayout(new GridLayout(0, 3, 10, 10));
        ventana.setSize(600, 300);
        ventana.setLocationRelativeTo(this);

        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        List<Pokemon> equipo = actual.getEquipoPokemon();

        for (Pokemon p : equipo) {
            JPanel panelPokebola = new JPanel(new BorderLayout());
            panelPokebola.setOpaque(false);
            
            FondoPanel imagenPokebola = new FondoPanel("/resources/pokebola.gif");
            imagenPokebola.setPreferredSize(new Dimension(100, 100));
            imagenPokebola.setLayout(new BorderLayout());
            
            JLabel nombrePokemon = new JLabel(p.getNombre(), SwingConstants.CENTER);
            nombrePokemon.setFont(new Font("Arial", Font.BOLD, 14));
            nombrePokemon.setForeground(Color.BLACK);
            
            JButton btnPokemon = new JButton();
            btnPokemon.setLayout(new BorderLayout());
            btnPokemon.add(imagenPokebola, BorderLayout.CENTER);
            btnPokemon.add(nombrePokemon, BorderLayout.SOUTH);
            btnPokemon.setBorder(BorderFactory.createEmptyBorder());
            btnPokemon.setContentAreaFilled(false);
            btnPokemon.setFocusPainted(false);
            btnPokemon.setEnabled(!p.equals(actual.getPokemonActivo()) && !p.estaDebilitado());
            
            btnPokemon.addActionListener(e -> {
                actual.setPokemonActivo(p);
                JOptionPane.showMessageDialog(ventana, "¡Has cambiado a " + p.getNombre() + "!");
                
                // Actualizar barra de vida del nuevo Pokémon activo
                if (turnoJugador1) {
                    progressBar1.setMaximum(p.getPsMaximos());
                    progressBar1.setValue(p.getPs());
                } else {
                    progressBar2.setMaximum(p.getPsMaximos());
                    progressBar2.setValue(p.getPs());
                }
                
                actualizarVistaJugador();
                ventana.dispose();
                
                turnoJugador1 = !turnoJugador1;
                actualizarVistaJugador();
            });

            panelPokebola.add(btnPokemon, BorderLayout.CENTER);
            ventana.add(panelPokebola);
        }

        ventana.setVisible(true);
    }

    private void mostrarVentanaCambioObligatorio(Entrenador entrenador) {
        JDialog ventana = new JDialog(this, "Selecciona un Pokémon", true);
        ventana.setLayout(new GridLayout(0, 3, 10, 10));
        ventana.setSize(600, 300);
        ventana.setLocationRelativeTo(this);

        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        List<Pokemon> equipo = entrenador.getEquipoPokemon();

        for (Pokemon p : equipo) {
            if (!p.estaDebilitado() && !p.equals(entrenador.getPokemonActivo())) {
                JPanel panelPokebola = new JPanel(new BorderLayout());
                panelPokebola.setOpaque(false);
                
                FondoPanel imagenPokebola = new FondoPanel("/resources/pokebola.gif");
                imagenPokebola.setPreferredSize(new Dimension(100, 100));
                imagenPokebola.setLayout(new BorderLayout());
                
                JLabel nombrePokemon = new JLabel(p.getNombre(), SwingConstants.CENTER);
                nombrePokemon.setFont(new Font("Arial", Font.BOLD, 14));
                nombrePokemon.setForeground(Color.BLACK);
                
                JButton btnPokemon = new JButton();
                btnPokemon.setLayout(new BorderLayout());
                btnPokemon.add(imagenPokebola, BorderLayout.CENTER);
                btnPokemon.add(nombrePokemon, BorderLayout.SOUTH);
                btnPokemon.setBorder(BorderFactory.createEmptyBorder());
                btnPokemon.setContentAreaFilled(false);
                btnPokemon.setFocusPainted(false);
                
                btnPokemon.addActionListener(e -> {
                    entrenador.setPokemonActivo(p);
                    JOptionPane.showMessageDialog(ventana, "¡" + entrenador.getNombre() + " envía a " + p.getNombre() + "!");
                    
                    // Actualizar barra de vida del nuevo Pokémon activo
                    if (entrenador == POOBkemonGUI.getJugador1()) {
                        progressBar1.setMaximum(p.getPsMaximos());
                        progressBar1.setValue(p.getPs());
                    } else {
                        progressBar2.setMaximum(p.getPsMaximos());
                        progressBar2.setValue(p.getPs());
                    }
                    
                    actualizarVistaJugador();
                    ventana.dispose();
                    
                    turnoJugador1 = !turnoJugador1;
                    actualizarVistaJugador();
                });

                panelPokebola.add(btnPokemon, BorderLayout.CENTER);
                ventana.add(panelPokebola);
            }
        }

        ventana.setVisible(true);
    }

    private void mostrarVentanaItem() {
        JDialog ventana = new JDialog(this, "Selecciona un Item", true);
        ventana.setLayout(new GridLayout(3, 1, 10, 10)); // Cambiado a 3 filas x 1 columna
        ventana.setSize(300, 300); // Ajustado el tamaño para mejor visualización
        ventana.setLocationRelativeTo(this);

        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        List<Item> items = actual.getMochilaItems();

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "¡No tienes items en tu mochila!");
            ventana.dispose();
            return;
        }

        // Creamos botones para cada tipo de poción
        JButton btnPocion = new JButton("Poción");
        JButton btnSuperPocion = new JButton("Superpoción");
        JButton btnHiperPocion = new JButton("Hiperpoción");

        // Configuramos los botones
        btnPocion.setBackground(verdeAguamarina);
        btnPocion.setForeground(Color.BLACK);
        btnPocion.setFocusPainted(false);
        btnPocion.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnSuperPocion.setBackground(verdeAguamarina);
        btnSuperPocion.setForeground(Color.BLACK);
        btnSuperPocion.setFocusPainted(false);
        btnSuperPocion.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnHiperPocion.setBackground(verdeAguamarina);
        btnHiperPocion.setForeground(Color.BLACK);
        btnHiperPocion.setFocusPainted(false);
        btnHiperPocion.setFont(new Font("Arial", Font.BOLD, 14));

        // Verificamos qué items tiene el jugador y habilitamos los botones correspondientes
        btnPocion.setEnabled(items.stream().anyMatch(i -> i instanceof Potion));
        btnSuperPocion.setEnabled(items.stream().anyMatch(i -> i instanceof SuperPotion));
        btnHiperPocion.setEnabled(items.stream().anyMatch(i -> i instanceof HyperPotion));

        // Añadimos los listeners
        btnPocion.addActionListener(e -> usarItem(ventana, actual, items, "Poción"));
        btnSuperPocion.addActionListener(e -> usarItem(ventana, actual, items, "Superpoción"));
        btnHiperPocion.addActionListener(e -> usarItem(ventana, actual, items, "Hiperpoción"));

        // Añadimos los botones a la ventana
        ventana.add(btnPocion);
        ventana.add(btnSuperPocion);
        ventana.add(btnHiperPocion);

        ventana.setVisible(true);
    }

    // Método auxiliar para usar el item seleccionado
    private void usarItem(JDialog ventana, Entrenador entrenador, List<Item> items, String tipoItem) {
        Pokemon objetivo = seleccionarPokemonParaItem(entrenador);
        if (objetivo != null) {
            Item item = items.stream()
                .filter(i -> i.getNombre().equals(tipoItem))
                .findFirst()
                .orElse(null);
            
            if (item != null) {
                item.usar(objetivo);
                items.remove(item);
                actualizarVidaPokemones();
                JOptionPane.showMessageDialog(ventana, "¡Has usado " + item.getNombre() + " en " + objetivo.getNombre() + "!");
                ventana.dispose();
                
                turnoJugador1 = !turnoJugador1;
                actualizarVistaJugador();
            }
        }
    }

    private Pokemon seleccionarPokemonParaItem(Entrenador entrenador) {
        JDialog ventana = new JDialog(this, "Selecciona un Pokémon", true);
        ventana.setLayout(new GridLayout(0, 1, 10, 10));
        ventana.setSize(300, 200);
        ventana.setLocationRelativeTo(this);

        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);

        Pokemon[] seleccionado = {null};
        List<Pokemon> equipo = entrenador.getEquipoPokemon();

        for (Pokemon pokemon : equipo) {
            JButton btn = new JButton(pokemon.getNombre() + " (PS: " + pokemon.getPs() + "/" + pokemon.getPsMaximos() + ")");
            btn.setBackground(verdeAguamarina);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.addActionListener(ev -> {
                seleccionado[0] = pokemon;
                ventana.dispose();
            });
            ventana.add(btn);
        }

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(verdeAguamarina);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(ev -> ventana.dispose());
        ventana.add(btnCancelar);

        ventana.setVisible(true);
        return seleccionado[0];
    }

    private void actualizarVidaPokemones() {
        actualizarVidaPokemon1(POOBkemonGUI.getJugador1().getPokemonActivo().getPs());
        actualizarVidaPokemon2(POOBkemonGUI.getJugador2().getPokemonActivo().getPs());
    }

    private void actualizarVistaJugador() {
        Entrenador jugador = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        labelJugador.setText("Turno: " + jugador.getNombre());

        Pokemon activo = jugador.getPokemonActivo();
        String rutaGif = PoobkemonGifs.getPokemonImage(activo.getNombre());

        if (rutaGif != null) {
            FondoPanel fondoPokemon = new FondoPanel(rutaGif);
            if (turnoJugador1) {
                setImagenPokemon(fondoPokemon);
                progressBar1.setMaximum(activo.getPsMaximos());
                actualizarVidaPokemon1(activo.getPs());
            } else {
                setImagenPokemon2(fondoPokemon);
                progressBar2.setMaximum(activo.getPsMaximos());
                actualizarVidaPokemon2(activo.getPs());
            }
        }
    }

    private void cargarPokemonesIniciales() {
        Entrenador j1 = POOBkemonGUI.getJugador1();
        Entrenador j2 = POOBkemonGUI.getJugador2();
        Pokemon p1 = j1.getPokemonActivo();
        Pokemon p2 = j2.getPokemonActivo();

        String gif1 = PoobkemonGifs.getPokemonImage(p1.getNombre());
        String gif2 = PoobkemonGifs.getPokemonImage(p2.getNombre());

        if (gif1 != null) setImagenPokemon(new FondoPanel(gif1));
        if (gif2 != null) setImagenPokemon2(new FondoPanel(gif2));

        progressBar1.setMaximum(p1.getPsMaximos());
        progressBar1.setValue(p1.getPs());
        progressBar2.setMaximum(p2.getPsMaximos());
        progressBar2.setValue(p2.getPs());
    }

    private void actualizarPosicionPanelImagen() {
        int panelWidth = panelGif.getWidth();
        int panelHeight = panelGif.getHeight();
        if (panelWidth == 0 || panelHeight == 0) return;

        int x2 = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
        int y2 = (int)(panelHeight * 0.2);

        int x1 = (int)(panelWidth * 0.3) - (ANCHO_PANEL / 2);
        int y1 = (int)(panelHeight * 0.47);

        x1 = Math.max(0, Math.min(x1, panelWidth - ANCHO_PANEL));
        y1 = Math.max(0, Math.min(y1, panelHeight - ALTO_PANEL));
        x2 = Math.max(0, Math.min(x2, panelWidth - ANCHO_PANEL));
        y2 = Math.max(0, Math.min(y2, panelHeight - ALTO_PANEL));

        panelImagenPokemon.setBounds(x1, y1, ANCHO_PANEL, ALTO_PANEL);
        panelImagenPokemon2.setBounds(x2, y2, ANCHO_PANEL, ALTO_PANEL);
        panelGif.repaint();
    }

    public void setImagenPokemon(FondoPanel fondo) {
        panelImagenPokemon.removeAll();
        panelImagenPokemon.add(fondo, BorderLayout.CENTER);
        panelImagenPokemon.add(progressBar1, BorderLayout.SOUTH);
        panelImagenPokemon.revalidate();
        panelImagenPokemon.repaint();
    }

    public void setImagenPokemon2(FondoPanel fondo) {
        panelImagenPokemon2.removeAll();
        panelImagenPokemon2.add(fondo, BorderLayout.CENTER);
        panelImagenPokemon2.add(progressBar2, BorderLayout.NORTH);
        panelImagenPokemon2.revalidate();
        panelImagenPokemon2.repaint();
    }

    public void actualizarVidaPokemon1(int vida) {
        progressBar1.setValue(vida);
        progressBar1.setString(vida + "/" + progressBar1.getMaximum());
    }

    public void actualizarVidaPokemon2(int vida) {
        progressBar2.setValue(vida);
        progressBar2.setString(vida + "/" + progressBar2.getMaximum());
    }

    private JButton crearBotonConImagen(String rutaImagen, String textoAlternativo) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
            Image img = icono.getImage().getScaledInstance(110, 60, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(img));
            boton.setPreferredSize(new Dimension(100, 90));
            boton.setToolTipText(textoAlternativo);
            boton.setBorder(BorderFactory.createEmptyBorder());
            boton.setContentAreaFilled(false);
            return boton;
        } catch (Exception e) {
            return new JButton(textoAlternativo);
        }
    }

    private void configurarVentana() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void mostrar() {
        setVisible(true);
    }

    @Override
    protected void accionNuevo() {}
    @Override
    protected void accionAbrir() {}
    @Override
    protected void accionGuardar() {}
    @Override
    protected void accionExportar() {}
    @Override
    protected void accionImportar() {}
}