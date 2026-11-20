package Modelo;

public class Empleado {
    private String numeroIdentificacion;
    private String nombreCompleto;
    private String tituloProfesional;
    private byte aniosExperiencia;

    // Constructor
    public Empleado(String numeroIdentificacion, String nombreCompleto, String tituloProfesional, byte aniosExperiencia) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.tituloProfesional = tituloProfesional;
        this.aniosExperiencia = aniosExperiencia;
    }

    // Getters and Setters
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTituloProfesional() {
        return tituloProfesional;
    }

    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }

    public byte getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(byte aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    // toString method
    @Override
    public String toString() {
        return "Empleado{" +
                "numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", tituloProfesional='" + tituloProfesional + '\'' +
                ", aniosExperiencia=" + aniosExperiencia +
                '}';
    }
}