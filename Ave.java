
import java.time.LocalDate;

public class Ave extends Animal {
    public Ave(String nombre, int edad, String unidadTiempo, String estadoSalud, boolean disponibleAdopcion, boolean vacunado, LocalDate fechaIngreso) {
        super(nombre, edad, unidadTiempo, estadoSalud, disponibleAdopcion, vacunado, fechaIngreso);
    }

    @Override
    public String toCSV() {
        return super.toCSV();
    }
}