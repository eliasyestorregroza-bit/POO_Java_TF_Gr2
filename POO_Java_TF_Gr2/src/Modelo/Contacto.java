package Modelo;

import java.io.Serializable;

/**
 * Persona de contacto de un cliente tipo Empresa.
 */
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreCompleto;
    private String cargo;
    private String numeroTelefono;
    private String correoElectronico;

    public Contacto(String nombreCompleto, String cargo,
                    String numeroTelefono, String correoElectronico) {
        super();
        this.nombreCompleto = nombreCompleto;
        this.cargo = cargo;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // ===== Getter-Setter =====
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    @Override
    public String toString() {
        return "Contacto: " + nombreCompleto
                + "\nCargo: " + cargo
                + "\nTeléfono: " + numeroTelefono
                + "\nCorreo: " + correoElectronico;
    }
}
