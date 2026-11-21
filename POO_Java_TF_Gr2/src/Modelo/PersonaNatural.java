package Modelo;

/**
 * Cliente de tipo Persona Natural.
 */
public final class PersonaNatural extends Cliente {

    private static final long serialVersionUID = 1L;

    private String numeroTelefono;
    private String correoElectronico;

    public PersonaNatural(String numeroIdentificacion, String nombre, String nombreCiudad,
                          String numeroTelefono, String correoElectronico) {
        super(numeroIdentificacion, nombre, nombreCiudad);
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // ===== Getter-Setter =====
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    @Override
    public String toString() {
        return "Tipo de cliente: Persona Natural\n"
                + super.toString()
                + "\nTeléfono: " + numeroTelefono
                + "\nCorreo: " + correoElectronico;
    }
}
