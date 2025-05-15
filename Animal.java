import java.time.LocalDate;

public abstract class Animal {
    protected String nombre;
    protected int edad;
    protected String unidadTiempo;
    protected String estadoSalud;
    protected boolean disponibleAdopcion;

    // Nuevos campos
    protected boolean vacunado;
    protected LocalDate fechaIngreso;

    // Constructor actualizado
    public Animal(String nombre, int edad, String unidadTiempo, String estadoSalud,
                  boolean disponibleAdopcion, boolean vacunado, LocalDate fechaIngreso) {
        this.nombre = nombre;
        this.edad = edad;
        this.unidadTiempo = unidadTiempo;
        this.estadoSalud = estadoSalud;
        this.disponibleAdopcion = disponibleAdopcion;
        this.vacunado = vacunado;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public boolean isDisponibleAdopcion() {
        return disponibleAdopcion;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }


    // CSV (para persistencia)
    public String toCSV() {
        return getClass().getSimpleName() + "," + nombre + "," + edad + "," + unidadTiempo + "," + estadoSalud + "," +
               disponibleAdopcion + "," + vacunado + "," + fechaIngreso;
    }

    public static Animal fromCSV(String linea) {
        String[] partes = linea.split(",");
        if (partes.length < 8) return null;

        String tipo = partes[0].trim().toLowerCase();
        String nombre = partes[1];
        int edad = Integer.parseInt(partes[2]);
        String unidad = partes[3];
        String salud = partes[4];
        boolean disponibleAdopcion = Boolean.parseBoolean(partes[5]);
        boolean vacunado = Boolean.parseBoolean(partes[6]);
        LocalDate fechaIngreso = LocalDate.parse(partes[7]);

        return switch (tipo.toLowerCase()) {
            case "perro" -> {
                String raza = (partes.length >= 9) ? partes[8] : "";
                yield new Perro(nombre, edad, unidad, salud, disponibleAdopcion, vacunado, fechaIngreso, raza);
            }
            case "gato" -> new Gato(nombre, edad, unidad, salud, disponibleAdopcion, vacunado, fechaIngreso);
            case "ave" -> new Ave(nombre, edad, unidad, salud, disponibleAdopcion, vacunado, fechaIngreso);
            case "reptil" -> new Reptil(nombre, edad, unidad, salud, disponibleAdopcion, vacunado, fechaIngreso);
            default -> null;
        };
    }
}
