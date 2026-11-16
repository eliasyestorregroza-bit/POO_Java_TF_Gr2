/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author YESID
 */
import java.time.LocalDate;
import java.util.ArrayList;

public class Proyecto implements java.io.Serializable {
    private String codigo;
    private String titulo;
    private LocalDate fechaInicio;
    private String unidadDuracion;
    private byte duracionEstimada;
    private Cliente suCliente;
    private Empleado suGerente;
    private ArrayList<Empleado> suEquipo;
    private String estado;

    // Constructor con equipo
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada, Cliente suCliente, Empleado suGerente, ArrayList<Empleado> suEquipo, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.suEquipo = suEquipo;
        this.estado = estado != null ? estado : "INICIADO";
    }

    // Constructor sin equipo
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion, byte duracionEstimada, Cliente suCliente, Empleado suGerente, String estado) {
        this(codigo, titulo, fechaInicio, unidadDuracion, duracionEstimada, suCliente, suGerente, new ArrayList<>(), estado);
    }

    public LocalDate determinarFechaFinalizacion() {
        // Lógica para sumar la duración estimada a la fechaInicio según la unidad (semanas, meses, años)
        if(unidadDuracion.equalsIgnoreCase("semanas")) return fechaInicio.plusWeeks(duracionEstimada);
        if(unidadDuracion.equalsIgnoreCase("meses")) return fechaInicio.plusMonths(duracionEstimada);
        if(unidadDuracion.equalsIgnoreCase("años")) return fechaInicio.plusYears(duracionEstimada);
        return fechaInicio;
    }

    public void agregarIntegrante(Empleado nuevoIntegrante) {
        if(this.suEquipo == null) {
            this.suEquipo = new ArrayList<>();
        }
        this.suEquipo.add(nuevoIntegrante);
    }

    public Empleado eliminarIntegrante(String numeroIdentificacion) {
        for (int i = 0; i < suEquipo.size(); i++) {
            if (suEquipo.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return suEquipo.remove(i);
            }
        }
        return null; // No encontrado
    }

    // Getters, Setters y toString()...

    public Object getSuCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getEstado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getCodigo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getSuGerente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getTitulo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getSuEquipo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
