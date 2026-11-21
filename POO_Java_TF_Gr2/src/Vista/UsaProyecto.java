package Vista;

import Modelo.Cliente;
import Modelo.Contacto;
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.PersonaNatural;
import Modelo.Proyecto;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ventana principal (JFrame) que gestiona las operaciones con los proyectos.
 */
public class UsaProyecto extends JFrame {

    private static ArrayList<Proyecto> losProyectos = new ArrayList<>();
    private static ArrayList<Cliente> losClientes = new ArrayList<>();

    private static final String ARCHIVO_PROYECTOS = "datosProyectos";
    private static final String ARCHIVO_CLIENTES = "datosClientes";
    private static final String LISTA_VACIA = "No hay proyectos registrados.";

    private JTextArea areaResultados;

    public UsaProyecto() {
        initComponents();
    }

    // =====================================================================
    //  Interfaz gráfica
    // =====================================================================
    private void initComponents() {
        setTitle("Gestión de Proyectos - POO_Java_TF_Gr2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelMenu = new JPanel(new GridLayout(0, 1, 4, 4));
        panelMenu.setBorder(BorderFactory.createTitledBorder("Menú"));

        agregarBoton(panelMenu, "1. Registrar un proyecto", () -> {
            losProyectos = registrarProyecto(losProyectos);
        });
        agregarBoton(panelMenu, "2. Consultar todos los proyectos",
                () -> mostrar(consultarTodosProyectos(losProyectos)));
        agregarBoton(panelMenu, "3. Consultar proyectos en curso",
                () -> mostrar(consultarProyectosEnCurso(losProyectos)));
        agregarBoton(panelMenu, "4. Consultar primer proyecto",
                () -> mostrar(consultarPrimerProyecto(losProyectos)));
        agregarBoton(panelMenu, "5. Consultar último proyecto",
                () -> mostrar(consultarUltimoProyecto(losProyectos)));
        agregarBoton(panelMenu, "6. Consultar gerente de un proyecto", () -> {
            String c = pedirTexto("Código del proyecto (ej. 2026-001):");
            if (c != null) mostrar(consultarGerenteProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "7. Consultar equipo de un proyecto", () -> {
            String c = pedirTexto("Código del proyecto (ej. 2026-001):");
            if (c != null) mostrar(consultarEquipoProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "8. Consultar proyectos de un gerente", () -> {
            String id = pedirTexto("Número de identificación del gerente:");
            if (id != null) mostrar(consultarProyectosDadoNumIDEGerente(losProyectos, id));
        });
        agregarBoton(panelMenu, "9. Consultar proyectos de un cliente", () -> {
            String id = pedirTexto("Número de identificación del cliente:");
            if (id != null) mostrar(consultarProyectosDadoNumIDECliente(losProyectos, id));
        });
        agregarBoton(panelMenu, "10. Registrar integrante a un proyecto", () -> {
            String c = pedirTexto("Código del proyecto:");
            if (c != null) mostrar(registrarIntegranteProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "11. Registrar equipo a un proyecto", () -> {
            String c = pedirTexto("Código del proyecto:");
            if (c != null) mostrar(registrarEquipoProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "12. Eliminar integrante de un proyecto", () -> {
            String c = pedirTexto("Código del proyecto:");
            if (c != null) mostrar(eliminarIntegranteProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "13. Generar archivo de proyectos",
                () -> generarArchivoDeObjetosProyectos(losProyectos));
        agregarBoton(panelMenu, "14. Generar archivo de clientes",
                () -> generarArchivoDeObjetosClientes(losProyectos));
        agregarBoton(panelMenu, "15. Leer archivo de proyectos", () -> {
            losProyectos = leerArchivoDeObjetosProyectos();
            mostrar("Proyectos leídos: " + losProyectos.size());
        });
        agregarBoton(panelMenu, "16. Leer archivo de clientes", () -> {
            losClientes = leerArchivoDeObjetosClientes();
            StringBuilder sb = new StringBuilder("Clientes leídos: " + losClientes.size() + "\n\n");
            for (Cliente cl : losClientes) sb.append(cl).append("\n\n");
            mostrar(sb.toString());
        });
        // Opciones adicionales del diagrama
        agregarBoton(panelMenu, "Consultar proyecto por código", () -> {
            String c = pedirTexto("Código del proyecto:");
            if (c != null) mostrar(consultarProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "Actualizar estado de un proyecto", () -> {
            String c = pedirTexto("Código del proyecto:");
            if (c != null) mostrar(actualizarEstadoProyectoDadoCodigo(losProyectos, c));
        });
        agregarBoton(panelMenu, "Consultar proyectos por tipo de cliente", () -> {
            String t = pedirOpcion("Tipo de cliente:", new String[]{"Empresa", "PersonaNatural"});
            if (t != null) mostrar(consultarProyectosDadoTipoCliente(losProyectos, t));
        });

        areaResultados = new JTextArea(30, 55);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        add(new JScrollPane(panelMenu), BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void agregarBoton(JPanel panel, String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setHorizontalAlignment(JButton.LEFT);
        boton.addActionListener(e -> accion.run());
        panel.add(boton);
    }

    private void mostrar(String texto) {
        areaResultados.setText(texto);
        areaResultados.setCaretPosition(0);
    }

    // =====================================================================
    //  main
    // =====================================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new UsaProyecto().setVisible(true));
    }

    // =====================================================================
    //  Utilidades de captura de datos (JOptionPane)
    // =====================================================================
    private String pedirTexto(String mensaje) {
        while (true) {
            String valor = JOptionPane.showInputDialog(this, mensaje);
            if (valor == null) return null;               // Canceló
            if (!valor.trim().isEmpty()) return valor.trim();
            JOptionPane.showMessageDialog(this, "El dato no puede estar vacío.");
        }
    }

    private Byte pedirByte(String mensaje, int min, int max) {
        while (true) {
            String valor = pedirTexto(mensaje);
            if (valor == null) return null;
            try {
                int n = Integer.parseInt(valor);
                if (n >= min && n <= max) return (byte) n;
            } catch (NumberFormatException ignored) { }
            JOptionPane.showMessageDialog(this, "Ingrese un número entre " + min + " y " + max + ".");
        }
    }

    private String pedirOpcion(String mensaje, String[] opciones) {
        Object r = JOptionPane.showInputDialog(this, mensaje, "Seleccione",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        return (r == null) ? null : r.toString();
    }

    private Empleado pedirEmpleado(String rol) {
        String id = pedirTexto(rol + " - Número de identificación:");
        if (id == null) return null;
        String nombre = pedirTexto(rol + " - Nombre completo:");
        if (nombre == null) return null;
        String titulo = pedirTexto(rol + " - Título profesional:");
        if (titulo == null) return null;
        Byte anios = pedirByte(rol + " - Años de experiencia:", 0, 60);
        if (anios == null) return null;
        return new Empleado(id, nombre, titulo, anios);
    }

    private Cliente pedirCliente() {
        String tipo = pedirOpcion("Tipo de cliente:", new String[]{"Empresa", "PersonaNatural"});
        if (tipo == null) return null;
        String id = pedirTexto("Cliente - Número de identificación (NIT / cédula):");
        if (id == null) return null;
        String nombre = pedirTexto("Cliente - Nombre:");
        if (nombre == null) return null;
        String ciudad = pedirTexto("Cliente - Ciudad:");
        if (ciudad == null) return null;

        if (tipo.equals("Empresa")) {
            String nc = pedirTexto("Contacto - Nombre completo:");
            if (nc == null) return null;
            String cargo = pedirTexto("Contacto - Cargo:");
            if (cargo == null) return null;
            String tel = pedirTexto("Contacto - Teléfono:");
            if (tel == null) return null;
            String correo = pedirTexto("Contacto - Correo electrónico:");
            if (correo == null) return null;
            return new Empresa(id, nombre, ciudad, new Contacto(nc, cargo, tel, correo));
        } else {
            String tel = pedirTexto("Cliente - Teléfono:");
            if (tel == null) return null;
            String correo = pedirTexto("Cliente - Correo electrónico:");
            if (correo == null) return null;
            return new PersonaNatural(id, nombre, ciudad, tel, correo);
        }
    }

    /** Arma el texto con atributos + fecha de finalización de un proyecto. */
    private String infoProyecto(Proyecto p) {
        return p.toString() + "\nFecha de finalización: " + p.determinarFechaFinalizacion()
                + "\n==========================================\n";
    }

    private Proyecto buscarProyecto(ArrayList<Proyecto> datos, String codigoProyecto) {
        for (Proyecto p : datos) {
            if (p.getCodigo().equalsIgnoreCase(codigoProyecto.trim())) return p;
        }
        return null;
    }

    // =====================================================================
    //  Métodos del diagrama
    // =====================================================================

    /** 1. Registra un proyecto y lo agrega a la lista. */
    public ArrayList<Proyecto> registrarProyecto(ArrayList<Proyecto> datos) {
        String codigo = generarCodigoProyecto(datos);

        String titulo;
        do {
            titulo = pedirTexto("Proyecto " + codigo + "\nTítulo (entre 20 y 100 caracteres):");
            if (titulo == null) return datos;
            if (!Proyecto.tituloValido(titulo))
                JOptionPane.showMessageDialog(this, "El título debe tener entre 20 y 100 caracteres.");
        } while (!Proyecto.tituloValido(titulo));

        LocalDate fechaInicio = null;
        while (fechaInicio == null) {
            String f = pedirTexto("Fecha de inicio (aaaa-mm-dd, ej. 2026-02-15):");
            if (f == null) return datos;
            try {
                fechaInicio = LocalDate.parse(f);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use aaaa-mm-dd.");
            }
        }

        String unidad = pedirOpcion("Unidad de duración:", Proyecto.UNIDADES);
        if (unidad == null) return datos;

        Byte duracion = null;
        while (duracion == null) {
            String rango = unidad.equals("meses") ? "1-11, 13-23 o 25-35" : "1 a 3";
            Byte d = pedirByte("Duración estimada en " + unidad + " (" + rango + "):", 1, 35);
            if (d == null) return datos;
            if (Proyecto.duracionValida(unidad, d)) duracion = d;
            else JOptionPane.showMessageDialog(this, "Duración no válida para " + unidad + ": " + rango);
        }

        Cliente cliente = pedirCliente();
        if (cliente == null) return datos;

        Empleado gerente = pedirEmpleado("Gerente");
        if (gerente == null) return datos;

        Proyecto nuevo = new Proyecto(codigo, titulo, fechaInicio, unidad, duracion, cliente, gerente);
        datos.add(nuevo);
        mostrar("Proyecto registrado correctamente:\n\n" + infoProyecto(nuevo));
        return datos;
    }

    /** Genera el código "año-consecutivo" (ej. 2026-001). El consecutivo reinicia cada año. */
    public String generarCodigoProyecto(ArrayList<Proyecto> datos) {
        int anio = LocalDate.now().getYear();
        int mayor = 0;
        for (Proyecto p : datos) {
            String[] partes = p.getCodigo().split("-");
            if (partes.length == 2 && partes[0].equals(String.valueOf(anio))) {
                mayor = Math.max(mayor, Integer.parseInt(partes[1]));
            }
        }
        return String.format("%d-%03d", anio, mayor + 1);
    }

    /** 2. Información de todos los proyectos. */
    public String consultarTodosProyectos(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) return LISTA_VACIA;
        StringBuilder sb = new StringBuilder("=== TODOS LOS PROYECTOS ===\n\n");
        for (Proyecto p : datos) sb.append(infoProyecto(p));
        return sb.toString();
    }

    /** 3. Proyectos con estado "EN CURSO". */
    public String consultarProyectosEnCurso(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) return LISTA_VACIA;
        StringBuilder sb = new StringBuilder("=== PROYECTOS EN CURSO ===\n\n");
        int n = 0;
        for (Proyecto p : datos) {
            if (p.getEstado().equals("EN CURSO")) { sb.append(infoProyecto(p)); n++; }
        }
        return (n == 0) ? "No hay proyectos con estado EN CURSO." : sb.toString();
    }

    /** 4. Primer proyecto registrado. */
    public String consultarPrimerProyecto(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) return LISTA_VACIA;
        return "=== PRIMER PROYECTO REGISTRADO ===\n\n" + infoProyecto(datos.get(0));
    }

    /** 5. Último proyecto registrado. */
    public String consultarUltimoProyecto(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) return LISTA_VACIA;
        return "=== ÚLTIMO PROYECTO REGISTRADO ===\n\n" + infoProyecto(datos.get(datos.size() - 1));
    }

    /** Proyecto dado su código. */
    public String consultarProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        return (p == null) ? "No existe el proyecto " + codigoProyecto : infoProyecto(p);
    }

    /** 6. Título del proyecto e información del gerente. */
    public String consultarGerenteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;
        return "Proyecto: " + p.getTitulo() + "\n\n=== GERENTE ===\n" + p.getSuGerente();
    }

    /** 7. Título del proyecto e información de cada integrante del equipo. */
    public String consultarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;
        StringBuilder sb = new StringBuilder("Proyecto: " + p.getTitulo() + "\n\n=== EQUIPO ===\n");
        if (p.getSuEquipo().isEmpty()) return sb.append("El proyecto aún no tiene equipo.").toString();
        int i = 1;
        for (Empleado e : p.getSuEquipo()) {
            sb.append("\nIntegrante ").append(i++).append(":\n").append(e).append("\n");
        }
        return sb.toString();
    }

    /** 10. Registra un integrante en el equipo del proyecto indicado. */
    public String registrarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;
        Empleado integrante = pedirEmpleado("Integrante");
        if (integrante == null) return "Registro cancelado.";
        p.agregarIntegrante(integrante);
        return "Integrante " + integrante.getNombreCompleto()
                + " agregado al proyecto " + p.getCodigo() + ".";
    }

    /** 11. Registra el equipo completo del proyecto indicado. */
    public String registrarEquipoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;

        Byte cantidad = pedirByte("¿Cuántos integrantes va a registrar?", 1, 50);
        if (cantidad == null) return "Registro cancelado.";

        ArrayList<Empleado> equipo = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Empleado e = pedirEmpleado("Integrante " + i + " de " + cantidad);
            if (e == null) return "Registro cancelado. El equipo no se modificó.";
            equipo.add(e);
        }
        p.setSuEquipo(equipo);
        return "Equipo de " + equipo.size() + " integrantes registrado en el proyecto " + p.getCodigo() + ".";
    }

    /** Cambia el estado del proyecto indicado. */
    public String actualizarEstadoProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;
        String estado = pedirOpcion("Estado actual: " + p.getEstado() + "\nNuevo estado:", Proyecto.ESTADOS);
        if (estado == null) return "Actualización cancelada.";
        p.setEstado(estado);
        return "El proyecto " + p.getCodigo() + " ahora está en estado " + estado + ".";
    }

    /** 12. Elimina un integrante del equipo del proyecto indicado. */
    public String eliminarIntegranteProyectoDadoCodigo(ArrayList<Proyecto> datos, String codigoProyecto) {
        if (datos.isEmpty()) return LISTA_VACIA;
        Proyecto p = buscarProyecto(datos, codigoProyecto);
        if (p == null) return "No existe el proyecto " + codigoProyecto;
        String id = pedirTexto("Número de identificación del integrante a eliminar:");
        if (id == null) return "Eliminación cancelada.";
        Empleado eliminado = p.eliminarIntegrante(id);
        return (eliminado == null)
                ? "No hay un integrante con identificación " + id + " en el proyecto."
                : "Se eliminó a " + eliminado.getNombreCompleto() + " del proyecto " + p.getCodigo() + ".";
    }

    /** 8. Proyectos de un gerente. */
    public String consultarProyectosDadoNumIDEGerente(ArrayList<Proyecto> datos, String numIDEGerente) {
        if (datos.isEmpty()) return LISTA_VACIA;
        StringBuilder sb = new StringBuilder("=== PROYECTOS DEL GERENTE " + numIDEGerente + " ===\n\n");
        int n = 0;
        for (Proyecto p : datos) {
            if (p.getSuGerente().getNumeroIdentificacion().equals(numIDEGerente)) {
                sb.append(infoProyecto(p)); n++;
            }
        }
        return (n == 0) ? "No hay proyectos con ese gerente." : sb.toString();
    }

    /** 9. Proyectos de un cliente. */
    public String consultarProyectosDadoNumIDECliente(ArrayList<Proyecto> datos, String numIDECliente) {
        if (datos.isEmpty()) return LISTA_VACIA;
        StringBuilder sb = new StringBuilder("=== PROYECTOS DEL CLIENTE " + numIDECliente + " ===\n\n");
        int n = 0;
        for (Proyecto p : datos) {
            if (p.getSuCliente().getNumeroIdentificacion().equals(numIDECliente)) {
                sb.append(infoProyecto(p)); n++;
            }
        }
        return (n == 0) ? "No hay proyectos con ese cliente." : sb.toString();
    }

    /** Proyectos según el tipo de cliente (Empresa o PersonaNatural). */
    public String consultarProyectosDadoTipoCliente(ArrayList<Proyecto> datos, String tipoCliente) {
        if (datos.isEmpty()) return LISTA_VACIA;
        StringBuilder sb = new StringBuilder("=== PROYECTOS CON CLIENTE TIPO " + tipoCliente + " ===\n\n");
        int n = 0;
        for (Proyecto p : datos) {
            if (p.getSuCliente().getClass().getSimpleName().equals(tipoCliente)) {
                sb.append(infoProyecto(p)); n++;
            }
        }
        return (n == 0) ? "No hay proyectos con cliente tipo " + tipoCliente + "." : sb.toString();
    }

    /** 13. Guarda todos los proyectos en el archivo de objetos "datosProyectos". */
    public void generarArchivoDeObjetosProyectos(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, LISTA_VACIA);
            return;
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PROYECTOS))) {
            for (Proyecto p : datos) salida.writeObject(p);
            JOptionPane.showMessageDialog(this, datos.size() + " proyectos guardados en \"" + ARCHIVO_PROYECTOS + "\".");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    /** 15. Lee los proyectos del archivo "datosProyectos". */
    public ArrayList<Proyecto> leerArchivoDeObjetosProyectos() {
        ArrayList<Proyecto> datos = new ArrayList<>();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARCHIVO_PROYECTOS))) {
            while (true) datos.add((Proyecto) entrada.readObject());
        } catch (EOFException fin) {
            // Fin del archivo: lectura completa
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "No se pudo leer \"" + ARCHIVO_PROYECTOS + "\": " + e.getMessage());
        }
        return datos;
    }

    /** 14. Guarda el cliente de cada proyecto en el archivo "datosClientes". */
    public void generarArchivoDeObjetosClientes(ArrayList<Proyecto> datos) {
        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, LISTA_VACIA);
            return;
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CLIENTES))) {
            for (Proyecto p : datos) salida.writeObject(p.getSuCliente());
            JOptionPane.showMessageDialog(this, "Clientes guardados en \"" + ARCHIVO_CLIENTES + "\".");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    /** 16. Lee los clientes del archivo "datosClientes" sin repetirlos. */
    public ArrayList<Cliente> leerArchivoDeObjetosClientes() {
        ArrayList<Cliente> datos = new ArrayList<>();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARCHIVO_CLIENTES))) {
            while (true) {
                Cliente c = (Cliente) entrada.readObject();
                boolean repetido = false;
                for (Cliente existente : datos) {
                    if (existente.getNumeroIdentificacion().equals(c.getNumeroIdentificacion())) {
                        repetido = true;
                        break;
                    }
                }
                if (!repetido) datos.add(c);
            }
        } catch (EOFException fin) {
            // Fin del archivo
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "No se pudo leer \"" + ARCHIVO_CLIENTES + "\": " + e.getMessage());
        }
        return datos;
    }
}
