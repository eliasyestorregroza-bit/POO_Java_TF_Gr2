package Vista;

import Modelo.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsaProyecto {
    
    static ArrayList<Proyecto> losProyectos = new ArrayList<>();
    static ArrayList<Cliente> losClientes = new ArrayList<>();

    public static void main(String[] args) {
        String menu = "--- GESTIÓN DE PROYECTOS ---\n\n"
                + "1. Registrar un proyecto\n"
                + "2. Consultar todos los proyectos\n"
                + "3. Consultar los proyectos en curso\n"
                + "4. Consultar el primer proyecto registrado\n"
                + "5. Consultar el último proyecto registrado\n"
                + "6. Consultar el gerente de un proyecto\n"
                + "7. Consultar el equipo de un proyecto\n"
                + "8. Consultar los proyectos de un gerente\n"
                + "9. Consultar los proyectos de un cliente\n"
                + "10. Registra un integrante\n"
                + "11. Registra el equipo a un proyecto\n"
                + "12. Elimina un integrante de un proyecto\n"
                + "13. Genera archivo de objetos de todos los proyectos\n"
                + "14. Genera archivo de objetos de todos los clientes\n"
                + "15. Lee un archivo de objetos de todos los proyectos\n"
                + "16. Lee un archivo de objetos de todos los clientes\n"
                + "17. Salir\n\n"
                + "Seleccione una opción:";

        int opcion = 0;
        
        do {
            try {
                String entrada = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.QUESTION_MESSAGE);
                
                if (entrada == null) {
                    break; // Si el usuario presiona "Cancelar" o cierra la ventana
                }
                
                opcion = Integer.parseInt(entrada);
                
                switch (opcion) {
                    case 1:
                        losProyectos = registrarProyecto(losProyectos);
                        break;
                    case 2:
                        mostrarMensaje(consultarTodosProyectos(losProyectos), "Todos los Proyectos");
                        break;
                    case 3:
                        mostrarMensaje(consultarProyectosEnCurso(losProyectos), "Proyectos en Curso");
                        break;
                    case 4:
                        mostrarMensaje(consultarPrimerProyecto(losProyectos), "Primer Proyecto");
                        break;
                    case 5:
                        mostrarMensaje(consultarUltimoProyecto(losProyectos), "Último Proyecto");
                        break;
                    case 6:
                        String cod1 = pedirDato("Ingrese el código del proyecto:");
                        if (cod1 != null) mostrarMensaje(consultarGerenteProyectoDadoCodigo(losProyectos, cod1), "Gerente del Proyecto");
                        break;
                    case 7:
                        String cod2 = pedirDato("Ingrese el código del proyecto:");
                        if (cod2 != null) mostrarMensaje(consultarEquipoProyectoDadoCodigo(losProyectos, cod2), "Equipo del Proyecto");
                        break;
                    case 8:
                        String idGer = pedirDato("Ingrese el ID del gerente:");
                        if (idGer != null) mostrarMensaje(consultarProyectosDadoNumIDEGerente(losProyectos, idGer), "Proyectos del Gerente");
                        break;
                    case 9:
                        String idCli = pedirDato("Ingrese el ID del cliente:");
                        if (idCli != null) mostrarMensaje(consultarProyectosDadoNumIDECliente(losProyectos, idCli), "Proyectos del Cliente");
                        break;
                    case 10:
                        String cod3 = pedirDato("Ingrese el código del proyecto:");
                        if (cod3 != null) mostrarMensaje(registrarIntegranteProyectoDadoCodigo(losProyectos, cod3), "Registro de Integrante");
                        break;
                    case 11:
                        String cod4 = pedirDato("Ingrese el código del proyecto:");
                        if (cod4 != null) mostrarMensaje(registrarEquipoProyectoDadoCodigo(losProyectos, cod4), "Registro de Equipo");
                        break;
                    case 12:
                        String cod5 = pedirDato("Ingrese el código del proyecto:");
                        if (cod5 != null) retirarIntegranteProyectoDadoCodigo(losProyectos, cod5);
                        break;
                    case 13:
                        generarArchivoDeObjetosProyectos(losProyectos);
                        break;
                    case 14:
                        generarArchivoDeObjetosClientes(losProyectos);
                        break;
                    case 15:
                        losProyectos = leerArchivoDeObjetosProyectos();
                        break;
                    case 16:
                        losClientes = leerArchivoDeObjetosClientes();
                        break;
                    case 17:
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 17);
    }

    // --- MÉTODOS AUXILIARES PARA GUI ---
    public static void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static String pedirDato(String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje);
    }

    // --- MÉTODOS DEL NEGOCIO (Adaptados para ventanas emergentes) ---

    public static ArrayList<Proyecto> registrarProyecto(ArrayList<Proyecto> datos) {
        try {
            String codigo = generarCodigoProyecto(datos);
            String titulo = pedirDato("Título del proyecto:");
            if (titulo == null) return datos;
            
            String fechaStr = pedirDato("Fecha de inicio (YYYY-MM-DD):");
            LocalDate fecha = LocalDate.parse(fechaStr);
            
            String unidad = pedirDato("Unidad de duración (semanas, meses, años):");
            byte duracion = Byte.parseByte(pedirDato("Duración estimada (número):"));
            
            Proyecto nuevoProyecto = new Proyecto(codigo, titulo, fecha, unidad, duracion, null, null, "INICIADO");
            datos.add(nuevoProyecto);
            mostrarMensaje("Proyecto registrado con éxito.\nCódigo generado: " + codigo, "Éxito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos inválidos o formato incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    public static String generarCodigoProyecto(ArrayList<Proyecto> datos) {
        int anioActual = LocalDate.now().getYear();
        return String.format("%d-%03d", anioActual, datos.size() + 1);
    }

    public static String consultarTodosProyectos(ArrayList<Proyecto> datos) {
        if (datos == null || datos.isEmpty()) return "No hay proyectos registrados.";
        StringBuilder sb = new StringBuilder();
        for (Proyecto p : datos) {
            sb.append("► ").append(p.getTitulo()).append(" [").append((String)p.getCodigo()).append("]\n");
        }
        return sb.toString();
    }

    public static String consultarProyectosEnCurso(ArrayList<Proyecto> datos) {
        if (datos == null || datos.isEmpty()) return "No hay proyectos registrados.";
        StringBuilder sb = new StringBuilder();
        boolean encontrado = false;
        for (Proyecto p : datos) {
            if ("EN CURSO".equalsIgnoreCase((String) p.getEstado())) {
                sb.append("► ").append(p.getTitulo()).append(" [").append((String)p.getCodigo()).append("]\n");
                encontrado = true;
            }
        }
        return encontrado ? sb.toString() : "No hay proyectos en curso.";
    }

    public static String consultarPrimerProyecto(ArrayList<Proyecto> datos) {
        if (datos == null || datos.isEmpty()) return "No hay proyectos registrados.";
        return "► " + datos.get(0).getTitulo() + " [" + datos.get(0).getCodigo() + "]";
    }

    public static String consultarUltimoProyecto(ArrayList<Proyecto> datos) {
        if (datos == null || datos.isEmpty()) return "No hay proyectos registrados.";
        return "► " + datos.get(datos.size() - 1).getTitulo() + " [" + datos.get(datos.size() - 1).getCodigo() + "]";
    }

    public static String consultarGerenteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigo) {
        for (Proyecto p : datos) {
            if (((String) p.getCodigo()).equals(codigo)) {
                Empleado g = (Empleado) p.getSuGerente();
                return g != null ? g.toString() : "El proyecto no tiene un gerente asignado.";
            }
        }
        return "Proyecto no encontrado.";
    }

    @SuppressWarnings("unchecked")
    public static String consultarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigo) {
        for (Proyecto p : datos) {
            if (((String) p.getCodigo()).equals(codigo)) {
                ArrayList<Empleado> eq = (ArrayList<Empleado>) p.getSuEquipo();
                if (eq != null && !eq.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (Empleado e : eq) sb.append("- ").append(e.getNombreCompleto()).append("\n");
                    return sb.toString();
                }
                return "El proyecto no tiene equipo asignado.";
            }
        }
        return "Proyecto no encontrado.";
    }

    public static String consultarProyectosDadoNumIDEGerente(ArrayList<Proyecto> datos, String id) {
        StringBuilder sb = new StringBuilder();
        for (Proyecto p : datos) {
            Empleado g = (Empleado) p.getSuGerente();
            if (g != null && g.getNumeroIdentificacion().equals(id)) sb.append(p.getTitulo()).append("\n");
        }
        return sb.length() > 0 ? sb.toString() : "No hay proyectos para ese gerente.";
    }

    public static String consultarProyectosDadoNumIDECliente(ArrayList<Proyecto> datos, String id) {
        StringBuilder sb = new StringBuilder();
        for (Proyecto p : datos) {
            Cliente c = (Cliente) p.getSuCliente();
            if (c != null && c.getNumeroIdentificacion().equals(id)) sb.append(p.getTitulo()).append("\n");
        }
        return sb.length() > 0 ? sb.toString() : "No hay proyectos para ese cliente.";
    }

    public static String registrarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigo) {
        for (Proyecto p : datos) {
            if (((String) p.getCodigo()).equals(codigo)) {
                try {
                    String id = pedirDato("ID:");
                    String nombre = pedirDato("Nombre:");
                    String titulo = pedirDato("Título Profesional:");
                    byte exp = Byte.parseByte(pedirDato("Años de experiencia:"));
                    
                    p.agregarIntegrante(new Empleado(id, nombre, titulo, exp));
                    return "Integrante agregado con éxito.";
                } catch (Exception e) {
                    return "Error en los datos ingresados.";
                }
            }
        }
        return "Proyecto no encontrado.";
    }

    public static String registrarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigo) {
        for (Proyecto p : datos) {
            if (((String) p.getCodigo()).equals(codigo)) {
                try {
                    int cant = Integer.parseInt(pedirDato("¿Cuántos integrantes registrará?"));
                    for (int i = 0; i < cant; i++) {
                        JOptionPane.showMessageDialog(null, "Datos del integrante #" + (i+1));
                        String id = pedirDato("ID:");
                        String nombre = pedirDato("Nombre:");
                        String titulo = pedirDato("Título Profesional:");
                        byte exp = Byte.parseByte(pedirDato("Años de experiencia:"));
                        p.agregarIntegrante(new Empleado(id, nombre, titulo, exp));
                    }
                    return "Equipo registrado con éxito.";
                } catch (Exception e) {
                    return "Error al registrar el equipo.";
                }
            }
        }
        return "Proyecto no encontrado.";
    }

    public static void retirarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigo) {
        for (Proyecto p : datos) {
            if (((String) p.getCodigo()).equals(codigo)) {
                String id = pedirDato("ID del integrante a retirar:");
                Empleado eliminado = p.eliminarIntegrante(id);
                if (eliminado != null) mostrarMensaje("Integrante retirado con éxito.", "Éxito");
                else mostrarMensaje("Integrante no encontrado en el equipo.", "Error");
                return;
            }
        }
        mostrarMensaje("Proyecto no encontrado.", "Error");
    }

    public static void generarArchivoDeObjetosProyectos(ArrayList<Proyecto> datos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datosProyectos.dat"))) {
            for (Proyecto p : datos) oos.writeObject(p);
            mostrarMensaje("Archivo de proyectos generado con éxito.", "Archivo Guardado");
        } catch (IOException e) {
            mostrarMensaje("Error al guardar: " + e.getMessage(), "Error");
        }
    }

    public static ArrayList<Proyecto> leerArchivoDeObjetosProyectos() {
        ArrayList<Proyecto> recuperados = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosProyectos.dat"))) {
            while (true) {
                try { recuperados.add((Proyecto) ois.readObject()); } 
                catch (EOFException e) { break; }
            }
            mostrarMensaje("Proyectos cargados correctamente.", "Archivo Leído");
        } catch (Exception e) {
            mostrarMensaje("No se pudo leer el archivo de proyectos.", "Error");
        }
        return recuperados;
    }

    public static void generarArchivoDeObjetosClientes(ArrayList<Proyecto> datos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datosClientes.dat"))) {
            ArrayList<String> ids = new ArrayList<>();
            for (Proyecto p : datos) {
                Cliente c = (Cliente) p.getSuCliente();
                if (c != null && !ids.contains(c.getNumeroIdentificacion())) {
                    oos.writeObject(c);
                    ids.add(c.getNumeroIdentificacion());
                }
            }
            mostrarMensaje("Archivo de clientes generado con éxito.", "Archivo Guardado");
        } catch (IOException e) {
            mostrarMensaje("Error al guardar clientes.", "Error");
        }
    }

    public static ArrayList<Cliente> leerArchivoDeObjetosClientes() {
        ArrayList<Cliente> recuperados = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datosClientes.dat"))) {
            while (true) {
                try { recuperados.add((Cliente) ois.readObject()); } 
                catch (EOFException e) { break; }
            }
            mostrarMensaje("Clientes cargados correctamente.", "Archivo Leído");
        } catch (Exception e) {
            mostrarMensaje("No se pudo leer el archivo de clientes.", "Error");
        }
        return recuperados;
    }
}