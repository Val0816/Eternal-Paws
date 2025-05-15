import java.time.LocalDate;

public class Perro extends Animal {
    private String raza;

    public Perro(String nombre, int edad, String unidadTiempo, String estadoSalud,
                 boolean disponibleAdopcion, boolean vacunado, LocalDate fechaIngreso, String raza) {
        super(nombre, edad, unidadTiempo, estadoSalud, disponibleAdopcion, vacunado, fechaIngreso);
        this.raza = raza;
    }

    public String getRaza() {
        return raza;
    }

    @Override
    public String toCSV() {
        // Incluye el campo raza
        return super.toCSV() + "," + raza;
    }
}
