
import java.time.LocalDate;

public class Reptil extends Animal {
    public Reptil(String nombre, int edad, String unidadTiempo, String estadoSalud, boolean disponibleAdopcion, boolean vacunado, LocalDate fechaIngreso) {
        super(nombre, edad, unidadTiempo, estadoSalud, disponibleAdopcion, vacunado, fechaIngreso);
    }

    @Override
    public String toCSV() {
        return super.toCSV();
    }
}
