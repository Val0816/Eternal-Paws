import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

public class PanelTablaAnimales {

    public static JPanel crearPanelTabla(List<Animal> animales) {
        JPanel panel = new JPanel(new BorderLayout());

        // Nueva columna "Días en el refugio"
        String[] columnas = {
            "Nombre", "Edad", "Unidad", "Salud", "Especie",
            "Raza", "Vacunado", "Fecha de ingreso", "Días en el refugio"
        };

        String[][] datos = new String[animales.size()][columnas.length];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < animales.size(); i++) {
            Animal a = animales.get(i);
            datos[i][0] = a.getNombre();
            datos[i][1] = String.valueOf(a.getEdad());
            datos[i][2] = a.getUnidadTiempo();
            datos[i][3] = a.getEstadoSalud();
            datos[i][4] = a.getClass().getSimpleName();
            datos[i][5] = (a instanceof Perro && ((Perro) a).getRaza() != null && !((Perro) a).getRaza().isEmpty())
                    ? ((Perro) a).getRaza()
                    : "-";
            datos[i][6] = a.isVacunado() ? "Sí" : "No";

            LocalDate fechaIngreso = a.getFechaIngreso();
            datos[i][7] = (fechaIngreso != null) ? fechaIngreso.format(formatter) : "-";

            if (fechaIngreso != null) {
                long dias = ChronoUnit.DAYS.between(fechaIngreso, LocalDate.now());
                datos[i][8] = dias + " días";
            } else {
                datos[i][8] = "-";
            }
        }

        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabla.setRowSorter(sorter);

        tabla.setRowHeight(24);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(255, 192, 203));
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        // Filtros por Nombre o Especie
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelBusqueda = new JLabel("Buscar por:");
        String[] opciones = {"Nombre", "Especie"};
        JComboBox<String> comboFiltro = new JComboBox<>(opciones);
        JTextField campoBusqueda = new JTextField(20);
        panelBusqueda.add(labelBusqueda);
        panelBusqueda.add(comboFiltro);
        panelBusqueda.add(campoBusqueda);

        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }

            public void filtrar() {
                String texto = campoBusqueda.getText().trim();
                int columna = comboFiltro.getSelectedIndex() == 0 ? 0 : 4;

                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
}
