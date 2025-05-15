import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class GestorTransacciones {

    public static void registrarTransaccion(String tipo, Animal animal) {
        String archivo = tipo.equalsIgnoreCase("adopcion") ? "transacciones/adopciones.txt" : "transacciones/acogidas.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            String linea = String.format(
                "%s | Nombre: %s | Especie: %s | Edad: %d %s | Salud: %s | Fecha: %s",
                tipo.toUpperCase(),
                animal.getNombre(),
                animal.getClass().getSimpleName(),
                animal.getEdad(),
                animal.getUnidadTiempo(),
                animal.getEstadoSalud(),
                LocalDate.now()
            );
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar la transacción: " + e.getMessage());
        }
    }
}
