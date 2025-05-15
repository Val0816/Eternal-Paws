import java.util.ArrayList;

public class Refugio {
    private static ArrayList<Animal> listaAnimales = new ArrayList<>();

    public static void registrarAnimal(Animal animal) {
        listaAnimales.add(animal);
    }

    public static ArrayList<Animal> obtenerAnimales() {
        return listaAnimales;
    }
}