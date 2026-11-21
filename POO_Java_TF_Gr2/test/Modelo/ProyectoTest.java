package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias (JUnit 4) de la clase Proyecto.
 * Se prueba cada camino posible de los métodos:
 *  - determinarFechaFinalizacion(): semanas, meses, años y unidad no válida.
 *  - agregarIntegrante(): con equipo existente y con equipo null.
 *  - eliminarIntegrante(): integrante encontrado, no encontrado y equipo null.
 *  - Constructor: estado null -> "INICIADO".
 *
 * @author Grupo 2 - POO_Java_TF_Gr2
 */
public class ProyectoTest {

    private Proyecto proyecto;
    private Empleado gerente;
    private Empleado integrante1;
    private Empleado integrante2;
    private Cliente cliente;

    /** Se ejecuta ANTES de cada @Test: crea objetos nuevos para cada prueba. */
    @Before
    public void setUp() {
        cliente = new PersonaNatural("1001", "Ana Gómez", "Cali");
        gerente = new Empleado("2001", "Luis Pérez", "Ingeniero", (byte) 10);
        integrante1 = new Empleado("3001", "María López", "Diseñadora", (byte) 3);
        integrante2 = new Empleado("3002", "Carlos Ruiz", "Programador", (byte) 5);

        proyecto = new Proyecto("2026-001", "App Móvil",
                LocalDate.of(2026, 1, 15), "meses", (byte) 6,
                cliente, gerente, "INICIADO");
    }

    /** Se ejecuta DESPUÉS de cada @Test. */
    @After
    public void tearDown() {
        proyecto = null;
    }

    // ---------- determinarFechaFinalizacion() ----------

    @Test
    public void testDeterminarFechaFinalizacion_Semanas() {
        proyecto.setUnidadDuracion("semanas");
        proyecto.setDuracionEstimada((byte) 4);
        LocalDate esperado = LocalDate.of(2026, 2, 12); // 15 ene + 4 semanas
        assertEquals(esperado, proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_Meses() {
        // El proyecto de setUp: 15 ene 2026 + 6 meses
        LocalDate esperado = LocalDate.of(2026, 7, 15);
        assertEquals(esperado, proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_Anios() {
        proyecto.setUnidadDuracion("años");
        proyecto.setDuracionEstimada((byte) 2);
        LocalDate esperado = LocalDate.of(2028, 1, 15);
        assertEquals(esperado, proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_IgnoraMayusculas() {
        proyecto.setUnidadDuracion("MESES");
        assertEquals(LocalDate.of(2026, 7, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_UnidadNoValida() {
        // Si la unidad no es semanas/meses/años, devuelve la fecha de inicio
        proyecto.setUnidadDuracion("días");
        assertEquals(proyecto.getFechaInicio(), proyecto.determinarFechaFinalizacion());
    }

    // ---------- agregarIntegrante() ----------

    @Test
    public void testAgregarIntegrante_EquipoExistente() {
        proyecto.agregarIntegrante(integrante1);
        proyecto.agregarIntegrante(integrante2);
        assertEquals(2, proyecto.getSuEquipo().size());
        assertTrue(proyecto.getSuEquipo().contains(integrante1));
        assertTrue(proyecto.getSuEquipo().contains(integrante2));
    }

    @Test
    public void testAgregarIntegrante_EquipoNull() {
        proyecto.setSuEquipo(null);
        proyecto.agregarIntegrante(integrante1);
        assertNotNull(proyecto.getSuEquipo());
        assertEquals(1, proyecto.getSuEquipo().size());
        assertSame(integrante1, proyecto.getSuEquipo().get(0));
    }

    // ---------- eliminarIntegrante() ----------

    @Test
    public void testEliminarIntegrante_Encontrado() {
        proyecto.agregarIntegrante(integrante1);
        proyecto.agregarIntegrante(integrante2);
        Empleado eliminado = proyecto.eliminarIntegrante("3001");
        assertSame(integrante1, eliminado);
        assertEquals(1, proyecto.getSuEquipo().size());
        assertFalse(proyecto.getSuEquipo().contains(integrante1));
    }

    @Test
    public void testEliminarIntegrante_NoEncontrado() {
        proyecto.agregarIntegrante(integrante1);
        assertNull(proyecto.eliminarIntegrante("9999"));
        assertEquals(1, proyecto.getSuEquipo().size());
    }

    @Test
    public void testEliminarIntegrante_EquipoNull() {
        proyecto.setSuEquipo(null);
        assertNull(proyecto.eliminarIntegrante("3001"));
    }

    // ---------- Constructores ----------

    @Test
    public void testConstructor_EstadoNullQuedaIniciado() {
        Proyecto p = new Proyecto("2026-002", "Web", LocalDate.of(2026, 3, 1),
                "semanas", (byte) 8, cliente, gerente, null);
        assertEquals("INICIADO", p.getEstado());
    }

    @Test
    public void testConstructor_SinEquipoCreaListaVacia() {
        assertNotNull(proyecto.getSuEquipo());
        assertTrue(proyecto.getSuEquipo().isEmpty());
    }

    @Test
    public void testConstructor_ConEquipo() {
        ArrayList<Empleado> equipo = new ArrayList<>();
        equipo.add(integrante1);
        Proyecto p = new Proyecto("2026-003", "ERP", LocalDate.of(2026, 5, 1),
                "años", (byte) 1, cliente, gerente, equipo, "EN CURSO");
        assertEquals(1, p.getSuEquipo().size());
        assertEquals("EN CURSO", p.getEstado());
        assertSame(gerente, p.getSuGerente());
        assertSame(cliente, p.getSuCliente());
    }
}
