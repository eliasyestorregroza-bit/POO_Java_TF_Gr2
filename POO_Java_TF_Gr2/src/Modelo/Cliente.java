/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.io.Serializable;
/**Serializable para que se permita guardar y leer archivos ;)=D
 *
 * @author Cristian David Rios Correa (1105372319)
 * @version 1.0 - Nov 18 2026
 */
public abstract class Cliente implements Serializable {
  /*Atributos*/
  protected String numeroIdentificacion;
  protected String nombre;
  protected String nombreCiudad;
  /*Constructror*/
  public Cliente(String numeroIdentificacion, String nombre, String nombreCiudad){
    this.numeroIdentificacion = numeroIdentificacion;
    this.nombre = nombre;
    this.nombreCiudad = nombreCiudad;
  }
/*getters y setters*/
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombreCiudad() {
        return nombreCiudad;
    }
    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
  /*toString*/
    @Override
    public String toString() {
        return "Cliente{" 
                + "ID: " + numeroIdentificacion 
                + ", Nombre: " + nombre 
                + ", Ciudad: " + nombreCiudad 
                + '}';
    }
}//fin class
