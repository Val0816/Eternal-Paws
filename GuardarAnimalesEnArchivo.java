import java.io.*;
import java.util.*;

public class GuardarAnimalesEnArchivo {

    private static final String RUTA_ARCHIVO = "animales.txt";

    public static void guardarAnimalesEnArchivo(List<Animal> animales) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            for (Animal animal : animales) {
                writer.write(animal.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO PARA CARGAR ANIMALES DESDE EL ARCHIVO
    public static List<Animal> cargarAnimalesDesdeArchivo() {
        List<Animal> animales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Animal animal = Animal.fromCSV(linea);
                if (animal != null) {
                    animales.add(animal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animales;
    }
}
