/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Elias Torregroza
 */
public final class Empresa extends Cliente {
    private String numeroTelefono;
    private String correoElectronico;
    private Contacto suContacto;

    public Empresa(String numeroIdentificacion, String nombre, String nombreCiudad, String numeroTelefono, String correoElectronico, Contacto suContacto) {
        super(numeroIdentificacion, nombre, nombreCiudad);
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.suContacto = suContacto;
    }
    
    // Getters, Setters y toString() ...
public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Contacto getSuContacto() {
        return suContacto;
    }

    
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setSuContacto(Contacto suContacto) {
        this.suContacto = suContacto;
    }

    // Método toString
    @Override
    public String toString() {
        return "Empresa: " + nombre + " (NIT: " + numeroIdentificacion + ") - Ciudad: " + nombreCiudad + 
               "\n   Tel: " + numeroTelefono + " | Correo: " + correoElectronico + 
               "\n   " + (suContacto != null ? suContacto.toString() : "Sin contacto asignado");
    }
}
