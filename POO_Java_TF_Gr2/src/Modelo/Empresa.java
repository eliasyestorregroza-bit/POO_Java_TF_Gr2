package Modelo;

/**
 * Cliente de tipo Empresa. Tiene un contacto asociado.
 */
public final class Empresa extends Cliente {

    private static final long serialVersionUID = 1L;

    private Contacto suContacto;

    public Empresa(String numeroIdentificacion, String nombre, String nombreCiudad,
                   Contacto suContacto) {
        super(numeroIdentificacion, nombre, nombreCiudad);
        this.suContacto = suContacto;
    }

    // ===== Getter-Setter =====
    public Contacto getSuContacto() { return suContacto; }
    public void setSuContacto(Contacto suContacto) { this.suContacto = suContacto; }

    @Override
    public String toString() {
        return "Tipo de cliente: Empresa\n"
                + super.toString()
                + "\n" + suContacto;
    }
}
