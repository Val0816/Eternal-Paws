import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class PanelAdopciones {

    public static JPanel crearPanelAdopciones(List<Animal> animales) {
        JPanel panel = new JPanel(new BorderLayout());

        // Columnas
        String[] columnas = {
            "Adoptado/Acogido", "Nombre", "Edad", "Unidad", "Salud",
            "Especie", "Raza", "Vacunado", "Fecha de ingreso", "Días en el refugio"
        };

        Object[][] datos = new Object[animales.size()][columnas.length];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        

        for (int i = 0; i < animales.size(); i++) {
            Animal a = animales.get(i);
            datos[i][0] = a.isDisponibleAdopcion();
            datos[i][1] = a.getNombre();
            datos[i][2] = a.getEdad();
            datos[i][3] = a.getUnidadTiempo();
            datos[i][4] = a.getEstadoSalud();
            datos[i][5] = a.getClass().getSimpleName();
            datos[i][6] = (a instanceof Perro) ? ((Perro) a).getRaza() : "-";
            datos[i][7] = a.isVacunado() ? "Sí" : "No";

            LocalDate fechaIngreso = a.getFechaIngreso();  
            datos[i][8] = (fechaIngreso != null) ? fechaIngreso.format(formatter) : "-";

            // Cálculo de los días en el refugio
            if (fechaIngreso != null) {
                long dias = ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now());
                datos[i][9] = dias + " días";
            } else {
                datos[i][9] = "-";
            }
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 0) ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(255, 192, 203));
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Crear el botón
        JButton btnVerSeleccionados = new JButton("Ver animales adoptados o acogidos");

        // Usamos un panel con FlowLayout para el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centrado
        panelBoton.add(btnVerSeleccionados); // Añadir el botón al panelBoton
        
        // Ajuste de tamaño del panelBoton (evita que ocupe todo el espacio)
        panelBoton.setPreferredSize(new Dimension(600, 80));  // Ajusta el tamaño del panel de botón
        
        // Crear un separador para controlar el espacio debajo de la tabla
        Box.Filler filler = new Box.Filler(new Dimension(0, 0), new Dimension(0, 10), new Dimension(0, 10));
        
        // Añadir el separador y el botón
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.add(filler);  // Esto crea espacio entre la tabla y el botón
        panelSur.add(panelBoton);  // El botón va después del espacio

        panel.add(panelSur, BorderLayout.SOUTH);

        // Establecer acción del botón
        btnVerSeleccionados.addActionListener(e -> {
            StringBuilder seleccionados = new StringBuilder("Animales Adoptados o Acogidos:\n");
            for (int i = 0; i < tabla.getRowCount(); i++) {
                Boolean seleccionado = (Boolean) tabla.getValueAt(i, 0);
                if (Boolean.TRUE.equals(seleccionado)) {
                    String nombre = (String) tabla.getValueAt(i, 1);
                    seleccionados.append("- ").append(nombre).append("\n");
                    
                    // Buscar el objeto Animal original
                    Animal animal = animales.get(i);
                    
                    // Guardar transacción como adopción (puedes adaptarlo a acogida si lo deseas)
                    GestorTransacciones.registrarTransaccion("adopcion", animal);
                }
            }
            // Mostrar los seleccionados en un mensaje emergente
            JOptionPane.showMessageDialog(panel, seleccionados.toString());
        });

        return panel;
    }
}
