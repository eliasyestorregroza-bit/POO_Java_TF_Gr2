/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author YESID
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
}
