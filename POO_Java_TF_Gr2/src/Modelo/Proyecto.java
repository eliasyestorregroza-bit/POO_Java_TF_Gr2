package Modelo;

/**
 *
 * @author Santiago Villabona
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
        if (suEquipo != null) {
            for (int i = 0; i < suEquipo.size(); i++) {
                if (suEquipo.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)) {
                    return suEquipo.remove(i);
                }
            }
        }
        return null; // No encontrado
    }

    // --- GETTERS Y SETTERS CORREGIDOS ---

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getUnidadDuracion() {
        return unidadDuracion;
    }

    public void setUnidadDuracion(String unidadDuracion) {
        this.unidadDuracion = unidadDuracion;
    }

    public byte getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(byte duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public Cliente getSuCliente() {
        return suCliente;
    }

    public void setSuCliente(Cliente suCliente) {
        this.suCliente = suCliente;
    }

    public Empleado getSuGerente() {
        return suGerente;
    }

    public void setSuGerente(Empleado suGerente) {
        this.suGerente = suGerente;
    }

    public ArrayList<Empleado> getSuEquipo() {
        return suEquipo;
    }

    public void setSuEquipo(ArrayList<Empleado> suEquipo) {
        this.suEquipo = suEquipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Proyecto [" + codigo + "] - " + titulo + " | Estado: " + estado;
    }
}
