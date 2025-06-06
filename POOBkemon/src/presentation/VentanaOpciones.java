package presentation;
 
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import domain.Entrenador;
import java.io.Serializable;

 
public class VentanaOpciones extends Ventana implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private FondoPanel fondoPanel;
    private JButton btnPvP, btnPvM, btnMvM, btnCreditos;
    private JTextField txtPlayer1, txtPlayer2;
    private JButton defensiveTrainer, attackingTrainer, changingTrainer, expertTrainer;
    private JButton defensiveTrainer2, attackingTrainer2, changingTrainer2, expertTrainer2;
    private String selectedMachineType = null;
    private String selectedMachineType2 = null;
    private static final String PVP_IMAGE = "/resources/pvp.png";
    private static final String PVM_IMAGE = "/resources/pvm.png";
    private static final String MVM_IMAGE = "/resources/mvm.png";
    private static final String CREDITOS_IMAGE = "/resources/creditos.png";
    private static final String BACKGROUND_IMAGE = "/resources/opciones.gif";
    private static final String MODO_BACKGROUND = "/resources/modo.gif";
    private static final String NORMAL_IMAGE = "/resources/Normal.png";
    private static final String SUPERVIVENCIA_IMAGE = "/resources/Supervivencia.png";
    private static final String START_IMAGE = "/resources/start.png";
 
    /**
     * Crea una nueva ventana de opciones para POOBkemon.
     */
    public VentanaOpciones() {
        super("Opciones de POOBkemon");
        inicializarComponentes();
        configurarListeners();
    }
 
    /**
     * Inicializa y configura los componentes gráficos de la ventana.
     */
    private void inicializarComponentes() {
        fondoPanel = new FondoPanel(BACKGROUND_IMAGE);
        setContentPane(fondoPanel);
        fondoPanel.setLayout(new BorderLayout());
        JPanel panelBotonesCentrales = new JPanel();
        panelBotonesCentrales.setOpaque(false);
        panelBotonesCentrales.setLayout(new GridLayout(3, 1, 0, 20));
        panelBotonesCentrales.setBorder(new EmptyBorder(50, 0, 50, 0));
        Dimension tamanoBotonesCentrales = new Dimension(200, 80);
        btnPvP = crearBotonTransparente(PVP_IMAGE, tamanoBotonesCentrales, "PvP");
        btnPvM = crearBotonTransparente(PVM_IMAGE, tamanoBotonesCentrales, "PvM");
        btnMvM = crearBotonTransparente(MVM_IMAGE, tamanoBotonesCentrales, "MvM");
        panelBotonesCentrales.add(btnPvP);
        panelBotonesCentrales.add(btnPvM);
        panelBotonesCentrales.add(btnMvM);
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panelBotonesCentrales);
        JPanel panelCreditos = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panelCreditos.setOpaque(false);
        Dimension tamanoCreditos = new Dimension(100, 50);
        btnCreditos = crearBotonTransparente(CREDITOS_IMAGE, tamanoCreditos, "Créditos");
        panelCreditos.add(btnCreditos);
        fondoPanel.add(panelCentro, BorderLayout.CENTER);
        fondoPanel.add(panelCreditos, BorderLayout.SOUTH);
    }
 
    /**
     * Crea un botón transparente con imagen y texto alternativo.
     * @param imagenPath Ruta de la imagen.
     * @param size Tamaño del botón.
     * @param textoAlternativo Texto alternativo si no se carga la imagen.
     * @return El botón creado.
     */
    private JButton crearBotonTransparente(String imagenPath, Dimension size, String textoAlternativo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.drawString(textoAlternativo, getWidth()/2 - 30, getHeight()/2);
                }
            }
        };
        boton.setPreferredSize(size);
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + imagenPath);
            boton.setText(textoAlternativo);
        }
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.setMargin(new Insets(0, 0, 0, 0));
        return boton;
    }
 
    /**
     * Configura los listeners de los botones y componentes de la ventana.
     */
    private void configurarListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int baseWidth = getWidth() / 6;
                Dimension nuevoTamanoCentral = new Dimension(baseWidth, baseWidth / 2);
                Dimension nuevoTamanoCreditos = new Dimension(baseWidth / 2, baseWidth / 4);
                btnPvP.setPreferredSize(nuevoTamanoCentral);
                btnPvM.setPreferredSize(nuevoTamanoCentral);
                btnMvM.setPreferredSize(nuevoTamanoCentral);
                btnCreditos.setPreferredSize(nuevoTamanoCreditos);
                escalarImagenBoton(btnPvP, PVP_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnPvM, PVM_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnMvM, MVM_IMAGE, nuevoTamanoCentral);
                escalarImagenBoton(btnCreditos, CREDITOS_IMAGE, nuevoTamanoCreditos);
                revalidate();
                repaint();
            }
        });
        btnPvP.addActionListener(e -> mostrarVentanaModo("Player vs Player", true, true));
        btnPvM.addActionListener(e -> mostrarVentanaModo("Player vs Machine", true, false));
        btnMvM.addActionListener(e -> mostrarVentanaModo("Machine vs Machine", false, false));
        btnCreditos.addActionListener(e -> {
            this.setVisible(false);
            POOBkemonGUI.mostrarVentanaCreditos();
        });
    }
 
    /**
     * Muestra el diálogo para seleccionar el modo de juego.
     * @param titulo Título del diálogo.
     * @param mostrarPlayer1 Indica si se muestra el campo para el jugador 1.
     * @param mostrarPlayer2 Indica si se muestra el campo para el jugador 2.
     */
    private void mostrarVentanaModo(String titulo, boolean mostrarPlayer1, boolean mostrarPlayer2) {
    	JDialog dialog = new JDialog(this, titulo, true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        FondoPanel fondoDialog = new FondoPanel(MODO_BACKGROUND);
        fondoDialog.setLayout(new BorderLayout());
        dialog.setContentPane(fondoDialog);
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        fondoDialog.add(lblTitulo, BorderLayout.NORTH);
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setOpaque(false);
        if (mostrarPlayer1 || mostrarPlayer2) {
            JPanel panelNombres = new JPanel();
            panelNombres.setLayout(new GridLayout(mostrarPlayer1 && mostrarPlayer2 ? 2 : 1, 2, 10, 10));
            panelNombres.setBorder(new EmptyBorder(10, 50, 20, 50));
            panelNombres.setOpaque(false);
            if (mostrarPlayer1) {
                JLabel lblPlayer1 = new JLabel("Player 1:");
                lblPlayer1.setForeground(Color.WHITE);
                lblPlayer1.setFont(new Font("Arial", Font.BOLD, 16));
                txtPlayer1 = new JTextField(15);
                txtPlayer1.setPreferredSize(new Dimension(200, 30));
                txtPlayer1.setFont(new Font("Arial", Font.PLAIN, 14));
                txtPlayer1.setBackground(new Color(255, 255, 102));
                panelNombres.add(lblPlayer1);
                panelNombres.add(txtPlayer1);
            }
            if (mostrarPlayer2) {
                JLabel lblPlayer2 = new JLabel("Player 2:");
                lblPlayer2.setForeground(Color.WHITE);
                lblPlayer2.setFont(new Font("Arial", Font.BOLD, 16));
                txtPlayer2 = new JTextField(15);
                txtPlayer2.setPreferredSize(new Dimension(200, 30));
                txtPlayer2.setFont(new Font("Arial", Font.PLAIN, 14));
                txtPlayer2.setBackground(new Color(173, 216, 230));
                panelNombres.add(lblPlayer2);
                panelNombres.add(txtPlayer2);
            }
            panelPrincipal.add(panelNombres);
        }
        if (titulo.equals("Player vs Machine")) {
            agregarSeleccionMaquina(panelPrincipal, "Seleccione tipo de máquina:", false);
        } else if (titulo.equals("Machine vs Machine")) {
            agregarSeleccionMaquina(panelPrincipal, "Seleccion máquina para Jugador 1:", false);
            agregarSeleccionMaquina(panelPrincipal, "Seleccion máquina para Jugador 2:", true);
        }
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(0, 0, 20, 0));
        Dimension tamanoBotones = new Dimension(150, 60);
        if (titulo.equals("Player vs Player")) {
            JButton btnNormal = crearBotonDialogo(NORMAL_IMAGE, tamanoBotones, "Normal");
            JButton btnSupervivencia = crearBotonDialogo(SUPERVIVENCIA_IMAGE, tamanoBotones, "Supervivencia");
            btnNormal.addActionListener(e -> iniciarJuego(dialog, mostrarPlayer1, mostrarPlayer2, false));
            btnSupervivencia.addActionListener(e -> iniciarJuego(dialog, mostrarPlayer1, mostrarPlayer2, true));
            panelBotones.add(btnNormal);
            panelBotones.add(btnSupervivencia);
        } else {
            JButton btnStart = crearBotonDialogo(START_IMAGE, tamanoBotones, "Start");
            btnStart.addActionListener(e -> {
                if (titulo.equals("Machine vs Machine")) {
                    if (selectedMachineType == null || selectedMachineType2 == null) {
                        JOptionPane.showMessageDialog(dialog,"Por favor seleccione tipos de máquina para ambos jugadores", "Tipos de máquina requeridos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else if (titulo.equals("Player vs Machine")) {
                    if (selectedMachineType == null) {
                    	JOptionPane.showMessageDialog(dialog,"Por favor seleccione un tipo de máquina","Tipo de máquina requerido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                if (mostrarPlayer1 && (txtPlayer1.getText() == null || txtPlayer1.getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(dialog,"Por favor ingrese un nombre para Player 1", "Nombre requerido",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (mostrarPlayer2 && (txtPlayer2.getText() == null || txtPlayer2.getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(dialog, "Por favor ingrese un nombre para Player 2","Nombre requerido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                iniciarJuego(dialog, mostrarPlayer1, mostrarPlayer2, true);
            });
            panelBotones.add(btnStart);
        }
        panelPrincipal.add(panelBotones);
        fondoDialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
 
    /**
     * Inicia el juego según la configuración seleccionada.
     * @param dialog Diálogo actual.
     * @param mostrarPlayer1 Si se muestra el jugador 1.
     * @param mostrarPlayer2 Si se muestra el jugador 2.
     * @param isSupervivencia Indica si es modo supervivencia.
     */
    private void iniciarJuego(JDialog dialog, boolean mostrarPlayer1, boolean mostrarPlayer2, boolean isSupervivencia) {
        String player1Name = mostrarPlayer1 ? txtPlayer1.getText().trim() : "Computer 1";
        String player2Name = mostrarPlayer2 ? txtPlayer2.getText().trim() : "Computer 2";
        
        // Crear entrenadores con equipos vacíos
        Entrenador jugador1 = POOBkemonGUI.getPoobkemon().crearEntrenador(player1Name);
        Entrenador jugador2 = POOBkemonGUI.getPoobkemon().crearEntrenador(player2Name);
        
        // Solo generar equipos aleatorios para modo supervivencia
        if (isSupervivencia) {
            jugador1.generarEquipoAleatorioCompleto();
            jugador2.generarEquipoAleatorioCompleto();
        }
        
        POOBkemonGUI.setJugador1(jugador1);
        POOBkemonGUI.setJugador2(jugador2);
        POOBkemonGUI.setModoSupervivencia(isSupervivencia);
        dialog.dispose();
        this.setVisible(false);
        
        if (isSupervivencia) {
            POOBkemonGUI.iniciarBatalla();
        } else {
            POOBkemonGUI.setMostrandoMovimientosJugador1(true);
            POOBkemonGUI.mostrarVentanaSeleccion(true);
        }
    }
 
    /**
     * Agrega la selección de tipo de máquina al panel principal.
     * @param panelPrincipal Panel donde se agrega la selección.
     * @param titulo Título de la selección.
     * @param isPlayer2 Indica si es para el jugador 2.
     */
    private void agregarSeleccionMaquina(JPanel panelPrincipal, String titulo, boolean isPlayer2) {
        JPanel panelMachineSelection = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMachineSelection.setBorder(new EmptyBorder(10, 50, 20, 50));
        panelMachineSelection.setOpaque(false);
        JLabel lblMachineType = new JLabel(titulo, SwingConstants.CENTER);
        lblMachineType.setForeground(Color.WHITE);
        lblMachineType.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblMachineType);
        if (!isPlayer2) {
            defensiveTrainer = createMachineButton("Defensive Trainer", false);
            attackingTrainer = createMachineButton("Attacking Trainer", false);
            changingTrainer = createMachineButton("Changing Trainer", false);
            expertTrainer = createMachineButton("Expert Trainer", false);
            panelMachineSelection.add(defensiveTrainer);
            panelMachineSelection.add(attackingTrainer);
            panelMachineSelection.add(changingTrainer);
            panelMachineSelection.add(expertTrainer);
        } else {
            defensiveTrainer2 = createMachineButton("Defensive Trainer", true);
            attackingTrainer2 = createMachineButton("Attacking Trainer", true);
            changingTrainer2 = createMachineButton("Changing Trainer", true);
            expertTrainer2 = createMachineButton("Expert Trainer", true);
            panelMachineSelection.add(defensiveTrainer2);
            panelMachineSelection.add(attackingTrainer2);
            panelMachineSelection.add(changingTrainer2);
            panelMachineSelection.add(expertTrainer2);
        }
        panelPrincipal.add(panelMachineSelection);
    }
 
    /**
     * Crea un botón para seleccionar el tipo de máquina.
     * @param text Texto del botón.
     * @param isPlayer2 Indica si es para el jugador 2.
     * @return El botón creado.
     */
    private JButton createMachineButton(String text, boolean isPlayer2) {
        JButton button = new JButton(text);
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.addActionListener(e -> {
            if (!isPlayer2) {
                defensiveTrainer.setBackground(Color.YELLOW);
                attackingTrainer.setBackground(Color.YELLOW);
                changingTrainer.setBackground(Color.YELLOW);
                expertTrainer.setBackground(Color.YELLOW);
                selectedMachineType = text;
            } else {
                defensiveTrainer2.setBackground(Color.YELLOW);
                attackingTrainer2.setBackground(Color.YELLOW);
                changingTrainer2.setBackground(Color.YELLOW);
                expertTrainer2.setBackground(Color.YELLOW);
                selectedMachineType2 = text;
            }
            button.setBackground(Color.GREEN);
        });
        return button;
    }
 
    /**
     * Crea un botón para los diálogos con imagen y texto alternativo.
     * @param imagenPath Ruta de la imagen.
     * @param size Tamaño del botón.
     * @param textoAlternativo Texto alternativo si no se carga la imagen.
     * @return El botón creado.
     */
    private JButton crearBotonDialogo(String imagenPath, Dimension size, String textoAlternativo) {
        JButton boton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.drawString(textoAlternativo, getWidth()/2 - 30, getHeight()/2);
                }
            }
        };
        boton.setPreferredSize(size);
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + imagenPath);
            boton.setText(textoAlternativo);
        }
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(null);
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return boton;
    }
 
    /**
     * Escala la imagen de un botón al tamaño especificado.
     * @param boton Botón al que se le escala la imagen.
     * @param imagenPath Ruta de la imagen.
     * @param size Tamaño deseado.
     */
    private void escalarImagenBoton(JButton boton, String imagenPath, Dimension size) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(imagenPath));
            Image img = iconoOriginal.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al escalar imagen: " + imagenPath);
        }
    }
 
    /**
     * Vuelve a la ventana de inicio.
     */
    private void volverAVentanaInicio() {
        this.dispose();
        POOBkemonGUI.mostrarVentanaInicio();
    }
 
    /**
     * Acción para la opción "Nuevo".
     */
    @Override
    protected void accionNuevo() {
        JOptionPane.showMessageDialog(this, "Función Nuevo en Opciones");
    }
 
    /**
     * Acción para la opción "Abrir".
     */
    @Override
    protected void accionAbrir() {
        mostrarFileChooser("Abrir configuración", new String[]{"cfg"}, "Archivos de configuración (*.cfg)",e -> JOptionPane.showMessageDialog(this, "Configuración cargada"));
    }
 
    /**
     * Acción para la opción "Guardar".
     */
    @Override
    protected void accionGuardar() {
        mostrarFileChooser("Guardar configuración", new String[]{"cfg"}, "Archivos de configuración (*.cfg)",e -> JOptionPane.showMessageDialog(this, "Configuración guardada"));
    }
 
    /**
     * Acción para la opción "Exportar".
     */
    @Override
    protected void accionExportar() {
        mostrarFileChooser("Exportar opciones", new String[]{"opt"}, "Archivos de opciones (*.opt)",e -> JOptionPane.showMessageDialog(this, "Opciones exportadas"));
    }
 
    /**
     * Acción para la opción "Importar".
     */
    @Override
    protected void accionImportar() {
        mostrarFileChooser("Importar opciones", new String[]{"opt"}, "Archivos de opciones (*.opt)",e -> JOptionPane.showMessageDialog(this, "Opciones importadas"));
    }
 
    /**
     * Muestra la ventana de opciones.
     */
    public void mostrar() {
        setVisible(true);
    }
}
