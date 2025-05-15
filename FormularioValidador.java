public class FormularioValidador {

    // Método para validar el nombre (no vacío)
    public static void validarNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    // Método para validar la edad (número positivo)
    public static void validarEdad(String edad) throws IllegalArgumentException {
        try {
            int edadInt = Integer.parseInt(edad);
            if (edadInt <= 0) {
                throw new IllegalArgumentException("La edad debe ser un número positivo.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido.");
        }
    }

    // Método para validar el estado de salud (no vacío)
    public static void validarSalud(String salud) throws IllegalArgumentException {
        if (salud == null || salud.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de salud no puede estar vacío.");
        }
    }

    // Método para validar la raza (solo perros)
    public static void validarRaza(String raza, String especie) throws IllegalArgumentException {
        if (especie.equalsIgnoreCase("Perro") && (raza == null || raza.trim().isEmpty())) {
            throw new IllegalArgumentException("La raza es obligatoria para los perros.");
        }
    }
}
