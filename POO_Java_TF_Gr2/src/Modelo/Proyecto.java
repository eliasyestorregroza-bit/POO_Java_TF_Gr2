package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa un proyecto con su cliente, su gerente y su equipo de trabajo.
 *
 * Reglas (nota "Clase: Proyecto" del diagrama):
 *  - codigo: año-consecutivo, ej. 2026-001 (lo genera UsaProyecto.generarCodigoProyecto)
 *  - titulo: entre 20 y 100 caracteres
 *  - fechaInicio: aaaa-mm-dd
 *  - unidadDuracion: semanas, meses, años
 *  - duracionEstimada: semanas 1-3; meses 1-11, 13-23, 25-35; años 1-3
 *  - estado: por defecto INICIADO
 */
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String[] ESTADOS = {
        "INICIADO", "EN CURSO", "CANCELADO", "FINALIZADO", "SUSPENDIDO", "APLAZADO"
    };
    public static final String[] UNIDADES = {"semanas", "meses", "años"};

    private String codigo;
    private String titulo;
    private LocalDate fechaInicio;
    private String unidadDuracion;
    private byte duracionEstimada;
    private Cliente suCliente;
    private Empleado suGerente;
    private ArrayList<Empleado> suEquipo;
    private String estado;

    /** Constructor con todos los parámetros (incluye el equipo). */
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion,
                    byte duracionEstimada, Cliente suCliente, Empleado suGerente,
                    ArrayList<Empleado> suEquipo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.unidadDuracion = unidadDuracion;
        this.duracionEstimada = duracionEstimada;
        this.suCliente = suCliente;
        this.suGerente = suGerente;
        this.suEquipo = (suEquipo != null) ? suEquipo : new ArrayList<>();
        this.estado = "INICIADO";
    }

    /** Constructor sin equipo: el equipo se agrega después. */
    public Proyecto(String codigo, String titulo, LocalDate fechaInicio, String unidadDuracion,
                    byte duracionEstimada, Cliente suCliente, Empleado suGerente) {
        this(codigo, titulo, fechaInicio, unidadDuracion, duracionEstimada,
             suCliente, suGerente, new ArrayList<>());
    }

    // ===== Validaciones (útiles para la vista) =====
    public static boolean tituloValido(String titulo) {
        return titulo != null && titulo.trim().length() >= 20 && titulo.trim().length() <= 100;
    }

    public static boolean duracionValida(String unidad, int duracion) {
        switch (unidad) {
            case "semanas":
            case "años":
                return duracion >= 1 && duracion <= 3;
            case "meses":
                return (duracion >= 1 && duracion <= 11)
                        || (duracion >= 13 && duracion <= 23)
                        || (duracion >= 25 && duracion <= 35);
            default:
                return false;
        }
    }

    // ===== Getter-Setter =====
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getUnidadDuracion() { return unidadDuracion; }
    public void setUnidadDuracion(String unidadDuracion) { this.unidadDuracion = unidadDuracion; }

    public byte getDuracionEstimada() { return duracionEstimada; }
    public void setDuracionEstimada(byte duracionEstimada) { this.duracionEstimada = duracionEstimada; }

    public Cliente getSuCliente() { return suCliente; }
    public void setSuCliente(Cliente suCliente) { this.suCliente = suCliente; }

    public Empleado getSuGerente() { return suGerente; }
    public void setSuGerente(Empleado suGerente) { this.suGerente = suGerente; }

    public ArrayList<Empleado> getSuEquipo() { return suEquipo; }
    public void setSuEquipo(ArrayList<Empleado> suEquipo) { this.suEquipo = suEquipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // ===== Métodos propios =====

    /** Calcula la fecha de finalización sumando la duración a la fecha de inicio. */
    public LocalDate determinarFechaFinalizacion() {
        switch (unidadDuracion) {
            case "semanas": return fechaInicio.plusWeeks(duracionEstimada);
            case "meses":   return fechaInicio.plusMonths(duracionEstimada);
            case "años":    return fechaInicio.plusYears(duracionEstimada);
            default:        return fechaInicio;
        }
    }

    /** Agrega un integrante al equipo. */
    public void agregarIntegrante(Empleado nuevoIntegrante) {
        suEquipo.add(nuevoIntegrante);
    }

    /**
     * Elimina del equipo el integrante con el número de identificación dado.
     * @return el empleado eliminado, o null si no existe.
     */
    public Empleado eliminarIntegrante(String numeroIdentificacion) {
        for (int i = 0; i < suEquipo.size(); i++) {
            if (suEquipo.get(i).getNumeroIdentificacion().equals(numeroIdentificacion)) {
                return suEquipo.remove(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Código: " + codigo
                + "\nTítulo: " + titulo
                + "\nFecha de inicio: " + fechaInicio
                + "\nDuración estimada: " + duracionEstimada + " " + unidadDuracion
                + "\nEstado: " + estado
                + "\n--- Cliente ---\n" + suCliente
                + "\n--- Gerente ---\n" + suGerente
                + "\nIntegrantes del equipo: " + suEquipo.size();
    }
}
