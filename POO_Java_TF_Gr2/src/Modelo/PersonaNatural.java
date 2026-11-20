/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Elias Torregroza
 */

public final class PersonaNatural extends Cliente {
    
    public PersonaNatural(String numeroIdentificacion, String nombre, String nombreCiudad) {
        super(numeroIdentificacion, nombre, nombreCiudad);
    }
    
   // Método toString
    @Override
    public String toString() {
        return "Persona Natural: " + nombre + " (ID: " + numeroIdentificacion + ") - Ciudad: " + nombreCiudad;
    }
}
