package org.example;

import org.example.model.Gasto;
import org.example.service.GestorGastos;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;


class GestorGastosTest {
    private GestorGastos gestor;
    private final String testCsvPath = "test_gastos.csv";

    @BeforeEach
    void setUp() {
        gestor = new GestorGastos(testCsvPath);
        crearArchivoPrueba();
    }

    @AfterEach
    void tearDown() {
        limpiarArchivoPrueba();
    }

    private void crearArchivoPrueba() {
        try {
            new FileWriter(testCsvPath).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarArchivoPrueba() {
        File file = new File(testCsvPath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Gasto exitoso
    @Test
    void testRegistrarGastoExitoso() {
        gestor.establecerLimiteGasto(2000);
        Gasto gasto = new Gasto("1000","03/11/2024", "Salida", "Comida");
        gestor.registrarGasto(gasto);

        assertTrue(new File(testCsvPath).exists());
        assertEquals(1000, gestor.calcularMontoTotal());
    }

    // Gasto fallido cuando el monto supera el límite
    @Test
    void testRegistrarGastoFallido() {
        //limite mas bajo
        gestor.establecerLimiteGasto(50);
        Gasto gasto = new Gasto("Salida",100, "03-11-2024", "Ropa", "Chaqueta");

        gestor.registrarGasto(gasto);

        assertEquals(0, gestor.calcularMontoTotal());
    }

    // Limite gasto
    @Test
    void testEstablecerLimiteGasto() {
        gestor.establecerLimiteGasto(300);
        assertEquals(300, gestor.getLimiteGasto());
    }
    // prueba para calcular el monto total gastado después de registrar dos gastos
    @Test void testCalcularMontoTotalGastado() {
        gestor.establecerLimiteGasto(5000);
        Gasto gasto1 = new Gasto("Salida",1000, "12/10/2024", "Comida", "Almuerzo");
        Gasto gasto2 = new Gasto("Salida", 1500, "12/10/2024", "Transporte", "Taxi");
        gestor.registrarGasto(gasto1);
        gestor.registrarGasto(gasto2);
        assertEquals(2500, gestor.calcularMontoTotal()); }

    // prueba para registrar un gasto con fecha incorrecta
    @Test void testRegistrarGastoFechaIncorrecta() {
        gestor.establecerLimiteGasto(1000);
        Gasto gasto = new Gasto("Salida",100, "40-14-2024", "Comida", "Desayuno"); //fecha incorrecta
        gestor.registrarGasto(gasto); // Verificar que el gasto no se haya registrado
        assertEquals(0, gestor.calcularMontoTotal());
    }

}
