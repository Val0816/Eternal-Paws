import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class Ventana {

    static List<Animal> listaAnimales = new ArrayList<>();
    static JPanel panelPrincipal;

    public static void mostrarTablaAnimales() {
        panelPrincipal.removeAll();
        List<Animal> animales = GuardarAnimalesEnArchivo.cargarAnimalesDesdeArchivo();
        JPanel panelTabla = PanelTablaAnimales.crearPanelTabla(animales);
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    public static class HintTextField extends JTextField {
        private String hint;

        public HintTextField(String hint) {
            this.hint = hint;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                g2.setColor(Color.gray);
                g2.drawString(hint, 5, getHeight() / 2 + getFont().getSize() / 2 - 4);
                g2.dispose();
            }
        }
    }

    public static void mostrarFormularioRegistro() {
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new GridLayout(2, 2, 20, 20));
        panelPrincipal.setBackground(Color.PINK);

        String[] especies = {"Perro", "Gato", "Ave", "Reptil"};

        int anchoPanel = panelPrincipal.getWidth();
        int altoPanel = panelPrincipal.getHeight();

        int anchoImagen = anchoPanel / 4;
        int altoImagen = altoPanel / 4;

        for (String especie : especies) {
            JButton btn = new JButton("Registrar " + especie);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);

            String rutaImagen = switch (especie) {
                case "Perro" -> "C:/Users/VALENTINA/Documents/Ingenieria/Semestre1/PFPC/Img/perro.jpg";
                case "Gato" -> "C:/Users/VALENTINA/Documents/Ingenieria/Semestre1/PFPC/Img/gatos.jpg";
                case "Ave" -> "C:/Users/VALENTINA/Documents/Ingenieria/Semestre1/PFPC/Img/aves.jpg";
                case "Reptil" -> "C:/Users/VALENTINA/Documents/Ingenieria/Semestre1/PFPC/Img/reptiles.jpg";
                default -> null;
            };

            if (rutaImagen != null) {
                ImageIcon icono = new ImageIcon(rutaImagen);
                Image imagenEscalada = icono.getImage().getScaledInstance(anchoImagen, altoImagen, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(imagenEscalada));
            }

            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setIconTextGap(10);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);

            btn.addActionListener(e -> abrirFormularioPara(especie));
            panelPrincipal.add(btn);
        }

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    



public static void abrirFormularioPara(String especie) {
    panelPrincipal.removeAll();
    panelPrincipal.setLayout(null);
    panelPrincipal.setBackground(Color.pink);

    JLabel titulo = new JLabel("Registrar " + especie);
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
    int tituloWidth = 350;
    int yTitulo = 250;
    titulo.setBounds((panelPrincipal.getWidth() - tituloWidth) / 2, yTitulo, tituloWidth, 30);
    panelPrincipal.add(titulo);

    HintTextField campoNombre = new HintTextField("Nombre");
    HintTextField campoEdad = new HintTextField("Edad");
    HintTextField campoSalud = new HintTextField("Estado de salud");
    JComboBox<String> unidadComboBox = new JComboBox<>(new String[] {"Años", "Meses"});
    JCheckBox checkVacunado = new JCheckBox("¿Vacunado?");
    HintTextField campoFechaIngreso = new HintTextField("Fecha de ingreso (YYYY-MM-DD)");

    checkVacunado.setBackground(Color.pink);

    int formularioAncho = 350;
    int formularioAlto = 340;
    int xFormulario = (panelPrincipal.getWidth() - formularioAncho) / 2;
    int yFormulario = (panelPrincipal.getHeight() - formularioAlto) / 2;

    campoNombre.setBounds(xFormulario + 50, yFormulario + 70, 250, 30);
    campoEdad.setBounds(xFormulario + 50, yFormulario + 110, 250, 30);
    campoSalud.setBounds(xFormulario + 50, yFormulario + 150, 250, 30);
    unidadComboBox.setBounds(xFormulario + 50, yFormulario + 190, 100, 30);
    checkVacunado.setBounds(xFormulario + 50, yFormulario + 230, 150, 30);
    campoFechaIngreso.setBounds(xFormulario + 50, yFormulario + (especie.equalsIgnoreCase("Perro") ? 310 : 270), 250, 30);
    
    panelPrincipal.add(campoFechaIngreso);
    panelPrincipal.add(campoNombre);
    panelPrincipal.add(campoEdad);
    panelPrincipal.add(campoSalud);
    panelPrincipal.add(unidadComboBox);
    panelPrincipal.add(checkVacunado);

    final HintTextField campoRazaFinal = (especie.equalsIgnoreCase("Perro")) 
        ? new HintTextField("Raza") 
        : null;

    if (campoRazaFinal != null) {
        campoRazaFinal.setBounds(xFormulario + 50, yFormulario + 270, 250, 30);
        panelPrincipal.add(campoRazaFinal);
    }

    JButton btnGuardar = new JButton("Guardar");
    int yBoton = especie.equalsIgnoreCase("Perro") ? 320 : 270;
    btnGuardar.setBounds(xFormulario + 50, yFormulario + (especie.equalsIgnoreCase("Perro") ? 360 : 320), 120, 40);
    btnGuardar.setBackground(new Color(173, 216, 230));
    panelPrincipal.add(btnGuardar);

    btnGuardar.addActionListener(e -> {
        try {
            FormularioValidador.validarNombre(campoNombre.getText());

            String edadTexto = campoEdad.getText();
            if (edadTexto.isEmpty() || !edadTexto.matches("\\d+")) {
                throw new IllegalArgumentException("La edad debe ser un número válido.");
            }

            String unidad = (String) unidadComboBox.getSelectedItem();
            if (unidad == null || unidad.isEmpty()) {
                throw new IllegalArgumentException("Debe seleccionar una unidad de tiempo (Años o Meses).");
            }

            FormularioValidador.validarSalud(campoSalud.getText());

            boolean vacunado = checkVacunado.isSelected();
            LocalDate fechaIngreso;
            String fechaTexto = campoFechaIngreso.getText().trim();
            if (fechaTexto.isEmpty()) {
                fechaIngreso = LocalDate.now(); // por defecto si no se escribe nada
            } else {
                try {
                    fechaIngreso = LocalDate.parse(fechaTexto);
                } catch (DateTimeParseException ex) {
                    throw new IllegalArgumentException("La fecha debe tener el formato YYYY-MM-DD.");
                }
            }


            Animal nuevoAnimal = null;
            if (especie.equalsIgnoreCase("Perro")) {
                nuevoAnimal = new Perro(
                    campoNombre.getText(),
                    Integer.parseInt(campoEdad.getText()),
                    unidad,
                    campoSalud.getText(),
                    false, // disponibleAdopcion
                    vacunado,
                    fechaIngreso,
                    campoRazaFinal != null ? campoRazaFinal.getText() : ""
                );
            } else if (especie.equalsIgnoreCase("Gato")) {
                nuevoAnimal = new Gato(campoNombre.getText(), Integer.parseInt(campoEdad.getText()), unidad,
                        campoSalud.getText(), false, vacunado, fechaIngreso);
            } else if (especie.equalsIgnoreCase("Ave")) {
                nuevoAnimal = new Ave(campoNombre.getText(), Integer.parseInt(campoEdad.getText()), unidad,
                        campoSalud.getText(), false, vacunado, fechaIngreso);
            } else if (especie.equalsIgnoreCase("Reptil")) {
                nuevoAnimal = new Reptil(campoNombre.getText(), Integer.parseInt(campoEdad.getText()), unidad,
                        campoSalud.getText(), false, vacunado, fechaIngreso);
            }

            if (nuevoAnimal != null) {
                listaAnimales.add(nuevoAnimal);
                ArrayList<Animal> soloNuevo = new ArrayList<>();
                soloNuevo.add(nuevoAnimal);
                GuardarAnimalesEnArchivo.guardarAnimalesEnArchivo(soloNuevo);
                JOptionPane.showMessageDialog(panelPrincipal, "Animal registrado correctamente.");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(panelPrincipal, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    panelPrincipal.revalidate();
    panelPrincipal.repaint();
}

    public static void mostrarPantallaInicio() {
    panelPrincipal.removeAll();
    panelPrincipal.setLayout(null);
    panelPrincipal.setBackground(Color.pink);

    // ---------- TÍTULO ----------
    JLabel titulo = new JLabel("🐾 Bienvenido a Eternal Paws 🐾", SwingConstants.CENTER);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
    titulo.setForeground(Color.white);
    titulo.setBounds(240, 20, 600, 30);
    panelPrincipal.add(titulo);

    // ---------- SUBTÍTULO ----------
    JLabel subtitulo = new JLabel("Un refugio donde cada huella encuentra su hogar.", SwingConstants.CENTER);
    subtitulo.setFont(new Font("SansSerif", Font.ITALIC, 16));
    subtitulo.setForeground(Color.white);
    subtitulo.setBounds(240, 60, 600, 25);
    panelPrincipal.add(subtitulo);

    // ---------- TEXTO DESCRIPTIVO ----------
    JTextArea texto = new JTextArea(
        "En Eternal Paws creemos que cada animal merece una segunda oportunidad.\n" +
        "Esta aplicación te permite conocer a nuestros adorables residentes,\n" +
        "gestionar sus procesos de adopción o acogida y ser parte del cambio que tanto necesitan.\n\n" +
        "Explora, conecta y... ¿quién sabe? Tal vez hoy encuentres a tu compañero de vida."
    );
    texto.setFont(new Font("SansSerif", Font.PLAIN, 20));
    texto.setForeground(Color.white);
    texto.setEditable(false);
    texto.setOpaque(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setBounds(150, 150, 700, 200);
    panelPrincipal.add(texto);

    // ---------- IMAGEN DECORATIVA ----------
    ImageIcon decoracion = new ImageIcon("C:\\Users\\VALENTINA\\Documents\\Ingenieria\\Semestre1\\PFPC\\Img\\Princ.png");
    Image imagenDecorada = decoracion.getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH);
    JLabel imagenDecorativa = new JLabel(new ImageIcon(imagenDecorada));
    imagenDecorativa.setBounds(200, 350, 650, 300);
    panelPrincipal.add(imagenDecorativa);

    panelPrincipal.revalidate();
    panelPrincipal.repaint();
}


    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBackground(Color.PINK);
        boton.setForeground(Color.black);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.black));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.PINK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.PINK);
            }
        });

        return boton;
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Eternal Paws");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        ventana.setSize(pantalla);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(null);

        int anchoLateral = 300;
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(Color.white);
        panelLateral.setPreferredSize(new Dimension(anchoLateral, 1000));
        panelLateral.setLayout(null);

        ImageIcon logo = new ImageIcon("C:/Users/VALENTINA/Documents/Ingenieria/Semestre1/PFPC/Img/LogoC.png");
        Image logoEscalado = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel etiquetaLogo = new JLabel(new ImageIcon(logoEscalado));
        etiquetaLogo.setBounds((anchoLateral - 160) / 2, 25, 150, 150);
        panelLateral.add(etiquetaLogo);

        


        JLabel titulo = new JLabel("Eternal Paws");
        titulo.setForeground(Color.pink);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0, 140, anchoLateral, 40);
        panelLateral.add(titulo);

        JButton botonRegistrar = crearBoton("Registrar");
        JButton botonInicio = crearBoton("Inicio");
        JButton botonBuscar = crearBoton("Buscar");
        JButton botonSalir = crearBoton("Salir");
        JButton botonAdopciones = crearBoton("Adopciones / Acogidas");

        int xBoton = (anchoLateral - 250) / 2;
        botonInicio.setBounds(xBoton, 200, 250, 50);
        botonRegistrar.setBounds(xBoton, 270, 250, 50);
        botonBuscar.setBounds(xBoton, 340, 250, 50);
        botonAdopciones.setBounds(xBoton, 410, 250, 50);
        botonSalir.setBounds(xBoton, 480, 250, 50);

        botonInicio.addActionListener(e -> mostrarPantallaInicio());
        botonRegistrar.addActionListener(e -> mostrarFormularioRegistro());
        botonBuscar.addActionListener(e -> mostrarTablaAnimales());
        botonSalir.addActionListener(e -> System.exit(0));
        botonAdopciones.addActionListener(e -> {
            List<Animal> animales = GuardarAnimalesEnArchivo.cargarAnimalesDesdeArchivo();
            JPanel panelAdopciones = PanelAdopciones.crearPanelAdopciones(animales);
            panelPrincipal.removeAll();
            panelPrincipal.setLayout(new BorderLayout());
            panelPrincipal.add(panelAdopciones, BorderLayout.CENTER);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        });

        panelLateral.add(botonInicio);
        panelLateral.add(botonRegistrar);
        panelLateral.add(botonBuscar);
        panelLateral.add(botonAdopciones);
        panelLateral.add(botonSalir);

        panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.PINK);
        panelPrincipal.setBounds(anchoLateral, 0, pantalla.width - anchoLateral, pantalla.height);
        panelPrincipal.setLayout(null);
        panelLateral.setBounds(0, 0, anchoLateral, pantalla.height);

        ventana.add(panelLateral);
        ventana.add(panelPrincipal);
        mostrarPantallaInicio();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }
}
