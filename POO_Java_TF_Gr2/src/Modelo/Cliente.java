package Modelo;

import java.io.Serializable;

/**
 * Clase abstracta que representa al cliente de un proyecto.
 * Sus subclases son Empresa y PersonaNatural.
 */
public abstract class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String numeroIdentificacion;
    protected String nombre;
    protected String nombreCiudad;

    public Cliente(String numeroIdentificacion, String nombre, String nombreCiudad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.nombreCiudad = nombreCiudad;
    }

    // ===== Getter-Setter =====
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreCiudad() { return nombreCiudad; }
    public void setNombreCiudad(String nombreCiudad) { this.nombreCiudad = nombreCiudad; }

    @Override
    public String toString() {
        return "Identificación: " + numeroIdentificacion
                + "\nNombre: " + nombre
                + "\nCiudad: " + nombreCiudad;
    }
}
