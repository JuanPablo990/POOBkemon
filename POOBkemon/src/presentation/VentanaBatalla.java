package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    private boolean turnoJugador1;
    private JTextArea areaMensajes;
    private JScrollPane scrollMensajes;
    private Map<Entrenador, List<Item>> mochilaLocal = new HashMap<>();
    private JPanel panelArenaContainer;
    private FondoPanel panelArena;
    private JPanel panelBotones;
    private boolean juegoPausado = false;
    private JButton btnPausa;

    public VentanaBatalla(List<String> nombresPokemonSeleccionados) {
        super("Batalla POOBkemon");
        this.turnoJugador1 = new Random().nextBoolean();
        mostrarAnimacionInicial();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializarComponentes();
        cargarPokemonesIniciales();
        actualizarVistaJugador();
    }

    private void mostrarAnimacionInicial() {
        JDialog dialog = new JDialog(this, "", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(null);
        try {
            FondoPanel fondo = new FondoPanel("/resources/iniciopelea.gif");
            fondo.setLayout(new BorderLayout());
            Timer timer = new Timer(6000, e -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();
            fondo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    timer.stop();
                    dialog.dispose();
                }
            });
            dialog.add(fondo);
            dialog.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al cargar la animación inicial: " + e.getMessage());
        }
    }

    private void inicializarComponentes() {
        Entrenador j1 = POOBkemonGUI.getJugador1();
        Entrenador j2 = POOBkemonGUI.getJugador2();
        mochilaLocal.put(j1, new ArrayList<>(j1.getMochilaItems()));
        mochilaLocal.put(j2, new ArrayList<>(j2.getMochilaItems()));
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
        SwingUtilities.invokeLater(() -> actualizarPosicionPanelImagen());
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelArenaContainer = new JPanel(new BorderLayout());
        panelArena = new FondoPanel("/resources/abajo.png");
        panelArena.setLayout(new BorderLayout());
        areaMensajes = new JTextArea(5, 30);
        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        areaMensajes.setFont(new Font("Arial", Font.PLAIN, 14));
        areaMensajes.setBackground(new Color(240, 240, 240));
        scrollMensajes = new JScrollPane(areaMensajes);
        scrollMensajes.setBorder(BorderFactory.createTitledBorder("Mensajes de Batalla"));
        panelArena.add(scrollMensajes, BorderLayout.CENTER);
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        labelJugador = new JLabel("Turno: " + (turnoJugador1 ? POOBkemonGUI.getJugador1().getNombre() : POOBkemonGUI.getJugador2().getNombre()), SwingConstants.CENTER);
        labelJugador.setFont(new Font("Arial", Font.BOLD, 20));
        panelBotones.add(labelJugador);
        JButton btnAtaque = crearBotonConImagen("/resources/ataque.png", "Ataque");
        JButton btnCambio = crearBotonConImagen("/resources/cambio.png", "Cambio");
        JButton btnItem = crearBotonConImagen("/resources/item.png", "Item");
        btnPausa = crearBotonConImagen("/resources/pausa.png", "Pausa");
        btnAtaque.addActionListener(e -> {
            if (!juegoPausado) mostrarVentanaAtaque();
            else mostrarMensaje("El juego está en pausa");
        });
        btnCambio.addActionListener(e -> {
            if (!juegoPausado) mostrarVentanaCambioPokemon();
            else mostrarMensaje("El juego está en pausa");
        });
        btnItem.addActionListener(e -> {
            if (!juegoPausado) mostrarVentanaItem();
            else mostrarMensaje("El juego está en pausa");
        });
        btnPausa.addActionListener(e -> mostrarMenuPausa());
        panelBotones.add(btnAtaque);
        panelBotones.add(btnCambio);
        panelBotones.add(btnItem);
        panelBotones.add(btnPausa);
        panelArena.add(panelBotones, BorderLayout.SOUTH);
        panelArenaContainer.add(panelArena, BorderLayout.CENTER);
        panelInferior.add(panelArenaContainer, BorderLayout.CENTER);
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

    private void mostrarMenuPausa() {
        JDialog dialog = new JDialog(this, "Opciones de Pausa", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        dialog.getContentPane().setBackground(fondoClaro);
        dialog.setLayout(new BorderLayout());
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(fondoClaro);
        JButton btnReanudar = new JButton("Reanudar Juego");
        estilizarBotonComoItem(btnReanudar, verdeAguamarina);
        btnReanudar.addActionListener(e -> {
            dialog.dispose();
            if (juegoPausado) {
                reanudarJuego();
            }
        });
        JButton btnHuir = new JButton("Huir de la Batalla");
        estilizarBotonComoItem(btnHuir, verdeAguamarina);
        btnHuir.addActionListener(e -> {
            dialog.dispose();
            confirmarHuir();
        });
        panelPrincipal.add(btnReanudar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnHuir);
        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    private void estilizarBotonComoItem(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getPreferredSize().height));
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorFondo.brighter());
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorFondo);
            }
        });
    }

    private void pausarJuego() {
        juegoPausado = true;
        deshabilitarControles();
        agregarMensaje("¡El juego está en pausa!");
    }

    private void reanudarJuego() {
        juegoPausado = false;
        habilitarControles();
        agregarMensaje("¡El juego se ha reanudado!");
    }

    private void confirmarHuir() {
    	 Color verdeAguamarina = new Color(102, 205, 170);
    	    Color fondoClaro = new Color(224, 255, 240);
    	    UIManager.put("OptionPane.background", fondoClaro);
    	    UIManager.put("Panel.background", fondoClaro);
    	    UIManager.put("Button.background", verdeAguamarina);
    	    UIManager.put("Button.foreground", Color.BLACK);
    	    UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12));
    	    int confirmacion = JOptionPane.showConfirmDialog(this,"¿Estás seguro de que quieres huir?\nPerderás la batalla.", "Confirmar huida", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
    	    UIManager.put("OptionPane.background", null);
    	    UIManager.put("Panel.background", null);
    	    UIManager.put("Button.background", null);
    	    UIManager.put("Button.foreground", null);
    	    UIManager.put("Button.font", null);
    	    if (confirmacion == JOptionPane.YES_OPTION) {
    	        if (juegoPausado) {
    	            reanudarJuego();
    	        }
    	        agregarMensaje("¡Has huido de la batalla!");
    	        POOBkemonGUI.reiniciarAplicacion();
    	    } else {
    	        agregarMensaje("¡Continúa la batalla!");
    	    }
    }

    private void deshabilitarControles() {
        for (Component comp : panelBotones.getComponents()) {
            if (comp != btnPausa) {
                comp.setEnabled(false);
            }
        }
    }

    private void habilitarControles() {
        for (Component comp : panelBotones.getComponents()) {
            comp.setEnabled(true);
        }
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

    private void actualizarPosicionPanelImagen() {
        int panelWidth = panelGif.getWidth();
        int panelHeight = panelGif.getHeight();
        if (panelWidth == 0 || panelHeight == 0) return;
        int x1, y1, x2, y2;
        if (turnoJugador1) {
            x1 = (int)(panelWidth * 0.3) - (ANCHO_PANEL / 2);
            y1 = (int)(panelHeight * 0.47);
            x2 = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
            y2 = (int)(panelHeight * 0.2);
        } else {
            x2 = (int)(panelWidth * 0.3) - (ANCHO_PANEL / 2);
            y2 = (int)(panelHeight * 0.47);
            x1 = (int)(panelWidth * 0.7) - (ANCHO_PANEL / 2);
            y1 = (int)(panelHeight * 0.2);
        }
        x1 = Math.max(0, Math.min(x1, panelWidth - ANCHO_PANEL));
        y1 = Math.max(0, Math.min(y1, panelHeight - ALTO_PANEL));
        x2 = Math.max(0, Math.min(x2, panelWidth - ANCHO_PANEL));
        y2 = Math.max(0, Math.min(y2, panelHeight - ALTO_PANEL));
        panelImagenPokemon.setBounds(x1, y1, ANCHO_PANEL, ALTO_PANEL);
        panelImagenPokemon2.setBounds(x2, y2, ANCHO_PANEL, ALTO_PANEL);
        panelGif.repaint();
    }

    public void agregarMensaje(String mensaje) {
        areaMensajes.append(mensaje + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarEfectividad(double efectividad) {
        String mensaje;
        if (efectividad <= 0.0) {
            mensaje = "INEFECTIVO";
        } else if (efectividad >= 2.0) {
            mensaje = "SUPEREFECTIVO";
        } else if (efectividad == 0.5) {
            mensaje = "POCO EFECTIVO";
        } else {
            mensaje = "NEUTRAL";
        }
        agregarMensaje(mensaje);
    }

    public void mostrarVentanaAtaque() {
        JDialog ventana = new JDialog(this, "Selecciona un Ataque", true);
        ventana.setLayout(new GridLayout(2, 2, 10, 10));
        ventana.setSize(400, 300);
        ventana.setLocationRelativeTo(this);
        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);
        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        Pokemon pokemonActivo = actual.getPokemonActivo();
        List<Movimiento> movimientos = pokemonActivo.getMovimientos();
        if (movimientos.isEmpty()) {
            agregarMensaje("¡No tiene movimientos disponibles!");
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
        Pokemon pokemonAtacante = atacante.getPokemonActivo();
        Pokemon pokemonDefensor = defensor.getPokemonActivo();
        agregarMensaje("¡" + atacante.getNombre() + " usó " + movimiento.getNombre() + "!");
        double efectividad = calcularEfectividad(movimiento, pokemonDefensor);
        mostrarEfectividad(efectividad);
        boolean exito = movimiento.ejecutar(pokemonAtacante, pokemonDefensor, efectividad);
        if (exito) {
            if (movimiento.getPotencia() > 0) {
                int danio = pokemonDefensor.getPsMaximos() - pokemonDefensor.getPs();
                agregarMensaje("¡El ataque hizo " + danio + " de daño!");
            }
            if (MENSAJES_MOVIMIENTOS.containsKey(movimiento.getNombre())) {
                String objetivo = movimiento.getNombre().startsWith("Subir") ? 
                    pokemonAtacante.getNombre() : pokemonDefensor.getNombre();
                agregarMensaje(String.format(MENSAJES_MOVIMIENTOS.get(movimiento.getNombre()), objetivo));
            }
            if (pokemonDefensor.estaDebilitado()) {
                agregarMensaje("¡" + pokemonDefensor.getNombre() + " fue debilitado!");
                if (!equipoDebilitado(defensor)) {
                    mostrarVentanaCambioObligatorio(defensor);
                }
            }
        } else {
            agregarMensaje(movimiento.getPp() <= 0 ? 
                "¡No quedan PP para este movimiento!" : "¡El ataque falló!");
        }
        actualizarVidaPokemones();
        turnoJugador1 = !turnoJugador1;
        actualizarVistaJugador();
    }

    private static final Map<String, String> MENSAJES_MOVIMIENTOS = Map.of(
        "Bajar Ataque", "%s bajó su ataque!",
        "Subir Ataque", "%s aumentó su ataque!",
        "Bajar Defensa", "%s bajó su defensa!",
        "Subir Defensa", "%s aumentó su defensa!",
        "Bajar Velocidad", "%s bajó su velocidad!",
        "Subir Velocidad", "%s aumentó su velocidad!"
    );

    private double calcularEfectividad(Movimiento movimiento, Pokemon objetivo) {
        String tipoAtaque = (movimiento.getTipo() != null) ? movimiento.getTipo().toLowerCase().replace("é", "e") : "";
        String tipoDefensaPrincipal = (objetivo.getTipoPrincipal() != null) ? objetivo.getTipoPrincipal().toLowerCase().replace("é", "e") : "";
        double efectividad = Efectividad.calcular(tipoAtaque, tipoDefensaPrincipal);
        if (objetivo.getTipoSecundario() != null && !objetivo.getTipoSecundario().isEmpty()) {
            String tipoDefensaSecundario = objetivo.getTipoSecundario().toLowerCase().replace("é", "e");
            efectividad *= Efectividad.calcular(tipoAtaque, tipoDefensaSecundario);
        }
        return efectividad;
    }

    private boolean equipoDebilitado(Entrenador entrenador) {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            if (!pokemon.estaDebilitado()) {
                return false;
            }
        }
        return true;
    }

    public void mostrarVentanaCambioPokemon() {
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
                agregarMensaje("¡Has cambiado a " + p.getNombre() + "!");
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

    public void mostrarVentanaCambioObligatorio(Entrenador entrenador) {
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
                    agregarMensaje("¡" + entrenador.getNombre() + " envía a " + p.getNombre() + "!");
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

    public void mostrarVentanaItem() {
        JDialog ventana = new JDialog(this, "Selecciona un Item", true);
        ventana.setLayout(new GridLayout(0, 1, 10, 10));
        ventana.setSize(300, 400);
        ventana.setLocationRelativeTo(this);
        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);
        Entrenador actual = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        List<Item> items = mochilaLocal.get(actual);
        if (items.isEmpty()) {
            agregarMensaje("¡No tienes items en tu mochila!");
            ventana.dispose();
            return;
        }
        Map<String, List<Item>> itemsPorTipo = new HashMap<>();
        for (Item item : items) {
            String nombre = item.getNombre();
            if (!itemsPorTipo.containsKey(nombre)) {
                itemsPorTipo.put(nombre, new ArrayList<>());
            }
            itemsPorTipo.get(nombre).add(item);
        }
        for (Map.Entry<String, List<Item>> entry : itemsPorTipo.entrySet()) {
            String nombreItem = entry.getKey();
            List<Item> itemsDelTipo = entry.getValue();
            Item itemEjemplo = itemsDelTipo.get(0);
            JButton btnItem = new JButton(nombreItem + " (" + itemsDelTipo.size() + ")");
            btnItem.setBackground(verdeAguamarina);
            btnItem.setForeground(Color.BLACK);
            btnItem.setFocusPainted(false);
            btnItem.setFont(new Font("Arial", Font.BOLD, 14));
            btnItem.addActionListener(e -> {
                Pokemon objetivo = seleccionarPokemonParaItem(actual, itemEjemplo);
                if (objetivo != null) {
                    Item itemAUsar = itemsDelTipo.get(0);
                    itemAUsar.usar(objetivo);
                    actual.getMochilaItems().remove(itemAUsar);
                    items.remove(itemAUsar);
                    itemsDelTipo.remove(0);
                    actualizarVidaPokemones();
                    agregarMensaje("¡Has usado " + nombreItem + " en " + objetivo.getNombre() + "!");
                    if (itemsDelTipo.isEmpty()) {
                        ventana.remove(btnItem);
                    } else {
                        btnItem.setText(nombreItem + " (" + itemsDelTipo.size() + ")");
                    }
                    ventana.revalidate();
                    ventana.repaint();
                    turnoJugador1 = !turnoJugador1;
                    actualizarVistaJugador();
                }
            });
            ventana.add(btnItem);
        }
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(verdeAguamarina);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> ventana.dispose());
        ventana.add(btnCancelar);
        ventana.setVisible(true);
    }

    private Pokemon seleccionarPokemonParaItem(Entrenador entrenador, Item item) {
        JDialog ventana = new JDialog(this, "Selecciona un Pokémon", true);
        ventana.setLayout(new GridLayout(0, 1, 10, 10));
        ventana.setSize(300, 300);
        ventana.setLocationRelativeTo(this);
        Color verdeAguamarina = new Color(102, 205, 170);
        Color fondoClaro = new Color(224, 255, 240);
        ventana.getContentPane().setBackground(fondoClaro);
        Pokemon[] seleccionado = {null};
        List<Pokemon> equipo = entrenador.getEquipoPokemon();
        for (Pokemon pokemon : equipo) {
            JButton btn = new JButton("<html><center>" + pokemon.getNombre() + "<br>PS: " + pokemon.getPs() + "/" + pokemon.getPsMaximos() + "<br>Estado: " + (pokemon.estaDebilitado() ? "Debilitado" : "Activo") + "</center></html>");
            btn.setBackground(verdeAguamarina);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            boolean puedeUsar = true;
            String mensajeError = "";
            
            if (item instanceof Potion || item instanceof SuperPotion || item instanceof HyperPotion) {
                if (pokemon.estaDebilitado()) {
                    puedeUsar = false;
                    mensajeError = "No se puede usar poción en un Pokémon debilitado";
                } else if (pokemon.getPs() == pokemon.getPsMaximos()) {
                    puedeUsar = false;
                    mensajeError = "El Pokémon ya tiene toda su vida";
                }
            } else if (item instanceof Revive) {
                if (!pokemon.estaDebilitado()) {
                    puedeUsar = false;
                    mensajeError = "Solo se puede revivir Pokémon debilitados";
                }
            }
            btn.setEnabled(puedeUsar);
            if (!puedeUsar) {
                btn.setToolTipText(mensajeError);
            }
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

    public void cargarPokemonesIniciales() {
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

    public void actualizarVistaJugador() {
        Entrenador jugador = turnoJugador1 ? POOBkemonGUI.getJugador1() : POOBkemonGUI.getJugador2();
        labelJugador.setText("Turno: " + jugador.getNombre());
        String fondoPath = turnoJugador1 ? "/resources/abajo.png" : "/resources/abajo2.png";
        panelArenaContainer.removeAll();
        panelArena = new FondoPanel(fondoPath);
        panelArena.setLayout(new BorderLayout());
        panelArena.add(scrollMensajes, BorderLayout.CENTER);
        panelArena.add(panelBotones, BorderLayout.SOUTH);
        panelArenaContainer.add(panelArena, BorderLayout.CENTER);
        panelArenaContainer.revalidate();
        panelArenaContainer.repaint();
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
        actualizarPosicionPanelImagen();
        verificarFinDeBatalla();
    }

    private void verificarFinDeBatalla() {
        Entrenador j1 = POOBkemonGUI.getJugador1();
        Entrenador j2 = POOBkemonGUI.getJugador2();
        if (equipoDebilitado(j1)) {
            mostrarAnimacionFinal(j2);
        } else if (equipoDebilitado(j2)) {
            mostrarAnimacionFinal(j1);
        }
    }

    private void mostrarAnimacionFinal(Entrenador ganador) {
        JDialog dialog = new JDialog(this, "", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(null);
        try {
            FondoPanel fondo = new FondoPanel("/resources/endgame.gif");
            fondo.setLayout(new BorderLayout());
            Color dorado = new Color(255, 215, 0);
            JLabel labelGanador = new JLabel("¡EL GANADOR ES " + ganador.getNombre().toUpperCase() + "!");
            labelGanador.setHorizontalAlignment(SwingConstants.CENTER);
            labelGanador.setFont(new Font("Arial", Font.BOLD, 36));
            labelGanador.setForeground(dorado);
            labelGanador.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            JPanel panelTexto = new JPanel(new GridBagLayout());
            panelTexto.setOpaque(false);
            panelTexto.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
            JLabel sombraTexto = new JLabel("¡EL GANADOR ES " + ganador.getNombre().toUpperCase() + "!");
            sombraTexto.setHorizontalAlignment(SwingConstants.CENTER);
            sombraTexto.setFont(new Font("Arial", Font.BOLD, 36));
            sombraTexto.setForeground(new Color(0, 0, 0, 150));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(2, 2, 0, 0);
            panelTexto.add(sombraTexto, gbc);
            gbc.insets = new Insets(0, 0, 2, 2);
            panelTexto.add(labelGanador, gbc);
            fondo.add(panelTexto, BorderLayout.SOUTH);
            MouseAdapter clickListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dialog.dispose();
                    POOBkemonGUI.reiniciarAplicacion();
                }
            };
            fondo.addMouseListener(clickListener);
            dialog.add(fondo);
            dialog.setVisible(true);
            new Timer(10000, e -> {
                dialog.dispose();
                POOBkemonGUI.reiniciarAplicacion();
            }).start();
        } catch (Exception e) {
            System.err.println("Error al cargar la animación final: " + e.getMessage());
            POOBkemonGUI.reiniciarAplicacion();
        }
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