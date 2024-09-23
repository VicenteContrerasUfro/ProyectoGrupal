import org.example.ControlDeGastosEstudiantil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class ControlDeGastrointestinalTest {

    private String nombre;
    private String universidad;

    @BeforeEach
    void setUp() {
        nombre = "Rodrigo Olave";
        universidad = "202201234";
    }

    @Test
    void testMostrarMenu() {
        Scanner scanner = new Scanner("10"); //opción de salir
        try {
            ControlDeGastosEstudiantil.mostrarMenu(scanner);
        } catch (Exception e) {
            fail("El método mostrarMenu lanzó una excepción inesperada: " + e.getMessage());
        }
    }
}
