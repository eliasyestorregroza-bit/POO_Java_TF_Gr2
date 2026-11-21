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
 *  - agregarIntegrante() y eliminarIntegrante(): encontrado y no encontrado.
 *  - tituloValido(): límites de 20 y 100 caracteres, espacios y null.
 *  - duracionValida(): rangos de semanas, meses y años.
 *  - Constructores: estado inicial, equipo vacío y equipo null.
 *
 * @author Yesid Suarez - POO_Java_TF_Gr2
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
        cliente = new PersonaNatural("1001", "Ana Gómez", "Cali", "3001234567", "ana@correo.com");
        gerente = new Empleado("2001", "Luis Pérez", "Ingeniero", (byte) 10);
        integrante1 = new Empleado("3001", "María López", "Diseñadora", (byte) 3);
        integrante2 = new Empleado("3002", "Carlos Ruiz", "Programador", (byte) 5);

        proyecto = new Proyecto("2026-001", "Aplicación móvil de inventarios",
                LocalDate.of(2026, 1, 15), "meses", (byte) 6, cliente, gerente);
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
        proyecto.setDuracionEstimada((byte) 3);
        // 15 ene 2026 + 3 semanas = 5 feb 2026
        assertEquals(LocalDate.of(2026, 2, 5), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_Meses() {
        // Proyecto de setUp: 15 ene 2026 + 6 meses
        assertEquals(LocalDate.of(2026, 7, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_Anios() {
        proyecto.setUnidadDuracion("años");
        proyecto.setDuracionEstimada((byte) 2);
        assertEquals(LocalDate.of(2028, 1, 15), proyecto.determinarFechaFinalizacion());
    }

    @Test
    public void testDeterminarFechaFinalizacion_UnidadNoValida() {
        // Si la unidad no es semanas/meses/años, devuelve la fecha de inicio
        proyecto.setUnidadDuracion("días");
        assertEquals(proyecto.getFechaInicio(), proyecto.determinarFechaFinalizacion());
    }

    // ---------- agregarIntegrante() ----------

    @Test
    public void testAgregarIntegrante() {
        proyecto.agregarIntegrante(integrante1);
        proyecto.agregarIntegrante(integrante2);
        assertEquals(2, proyecto.getSuEquipo().size());
        assertSame(integrante1, proyecto.getSuEquipo().get(0));
        assertSame(integrante2, proyecto.getSuEquipo().get(1));
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

    // ---------- tituloValido() ----------

    @Test
    public void testTituloValido_Limites() {
        assertFalse(Proyecto.tituloValido("a".repeat(19)));
        assertTrue(Proyecto.tituloValido("a".repeat(20)));
        assertTrue(Proyecto.tituloValido("a".repeat(100)));
        assertFalse(Proyecto.tituloValido("a".repeat(101)));
    }

    @Test
    public void testTituloValido_EspaciosYNull() {
        // Los espacios al inicio y al final no cuentan
        assertFalse(Proyecto.tituloValido("   App corta   "));
        assertFalse(Proyecto.tituloValido(null));
    }

    // ---------- duracionValida() ----------

    @Test
    public void testDuracionValida_SemanasYAnios() {
        assertTrue(Proyecto.duracionValida("semanas", 1));
        assertTrue(Proyecto.duracionValida("semanas", 3));
        assertFalse(Proyecto.duracionValida("semanas", 4));
        assertTrue(Proyecto.duracionValida("años", 3));
        assertFalse(Proyecto.duracionValida("años", 0));
    }

    @Test
    public void testDuracionValida_Meses() {
        assertTrue(Proyecto.duracionValida("meses", 11));
        assertFalse(Proyecto.duracionValida("meses", 12)); // 12 meses = 1 año
        assertTrue(Proyecto.duracionValida("meses", 13));
        assertFalse(Proyecto.duracionValida("meses", 24));
        assertTrue(Proyecto.duracionValida("meses", 35));
        assertFalse(Proyecto.duracionValida("meses", 36));
    }

    @Test
    public void testDuracionValida_UnidadNoValida() {
        assertFalse(Proyecto.duracionValida("días", 2));
    }

    // ---------- Constructores ----------

    @Test
    public void testConstructor_EstadoInicialYEquipoVacio() {
        assertEquals("INICIADO", proyecto.getEstado());
        assertNotNull(proyecto.getSuEquipo());
        assertTrue(proyecto.getSuEquipo().isEmpty());
    }

    @Test
    public void testConstructor_ConEquipo() {
        ArrayList<Empleado> equipo = new ArrayList<>();
        equipo.add(integrante1);
        Proyecto p = new Proyecto("2026-002", "Plataforma web para reservas de citas",
                LocalDate.of(2026, 3, 1), "semanas", (byte) 3, cliente, gerente, equipo);
        assertEquals(1, p.getSuEquipo().size());
        assertSame(gerente, p.getSuGerente());
        assertSame(cliente, p.getSuCliente());
    }

    @Test
    public void testConstructor_EquipoNullCreaListaVacia() {
        Proyecto p = new Proyecto("2026-003", "Sistema de facturación electrónica",
                LocalDate.of(2026, 5, 1), "años", (byte) 1, cliente, gerente, null);
        assertNotNull(p.getSuEquipo());
        assertTrue(p.getSuEquipo().isEmpty());
    }
}
