/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import DAO.CustomerDAO;
import DTO.CustomerDTO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para validar la lógica de negocio y persistencia
 * de la entidad Customer (Clientes).
 * 
 * @author Andres
 */
public class customerTest {

    /**
     * Prueba 1: Verifica que un cliente normal se pueda guardar exitosamente
     * con datos válidos.
     */
    @Test
    public void testGuardarClienteNormalExitoso() {
        CustomerDAO dao = new CustomerDAO();
        
        List<String> telefonos = new ArrayList<>(Arrays.asList("6441234567"));
        CustomerDTO dto = new CustomerDTO("Juan Perez", "juan.perez@itson.edu.mx", telefonos, false, 0, 1);
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("guardado correctamente"), "El cliente debería guardarse exitosamente.");
    }

    /**
     * Prueba 2: Verifica que un cliente Premium se guarde correctamente
     */
    @Test
    public void testClientePremiumExitoso() {
        CustomerDAO dao = new CustomerDAO();

        List<String> telefonos = new ArrayList<>(Arrays.asList("6449876543"));
        CustomerDTO dto = new CustomerDTO("Maria Lopez", "maria@itson.edu.mx", telefonos, true, 50, 2);
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("guardado correctamente"), "El cliente Premium debería guardarse exitosamente.");
    }

    /**
     * Prueba 3: Valida que no se permita guardar un correo con formato inválido.
     */
    @Test
    public void testEmailInvalido() {
        CustomerDAO dao = new CustomerDAO();

        CustomerDTO dto = new CustomerDTO();
        dto.setName("olibia rodrigo");
        dto.setEmail("correazo.com"); 
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("correo electrónico no tiene un formato válido"), "Debería rechazar un email inválido.");
    }

    /**
     * Prueba 4: Valida que el nombre no pueda superar los 50 caracteres (Regla del PDF).
     */
    @Test
    public void testNombreLargo() {
        CustomerDAO dao = new CustomerDAO();

        CustomerDTO dto = new CustomerDTO();
        dto.setName("The bee movie script: According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too\n" +
        "small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't\n" +
        "care what humans think is impossible. Yellow, black. Yellow, black. Yellow, black. Yellow, black.\n" +
        "Ooh, black and yellow! Let's shake it up a little. Barry! Breakfast is ready! Ooming! Hang on a\n" +
        "second. Hello? - Barry? - Adam? - Oan you believe this is happening? - I can't. I'll pick you up.\n" +
        "Looking sharp. Use the stairs. Your father paid good money for those. Sorry. I'm excited. Here's the\n" +
        "graduate. We're very proud of you, son. A perfect report card, all B's. Very proud. Ma! I got a thing\n" +
        "going here. - You got lint on your fuzz. - Ow! That's me! - Wave to us! We'll be in row 118,000. -\n" +
        "Bye! Barry, I told you, stop flying in the house! - Hey, Adam. - Hey, Barry. - Is that fuzz gel? - A little.\n" +
        "Special day, graduation. Never thought I'd make it. Three days grade school, three days high\n" +
        "school. Those were awkward. Three days college. I'm glad I took a day and hitchhiked around the\n" +
        "hive. You did come back different. - Hi, Barry. - Artie, growing a mustache? Looks good. - Hear\n" +
        "about Frankie? - Yeah. - You going to the funeral? - No, I'm not going. Everybody knows, sting\n" +
        "someone, you die. Don't waste it on a squirrel. Such a hothead. I guess he could have just gotten\n" +
        "out of the way. I love this incorporating an amusement park into our day. That's why we don't need\n" +
        "vacations. Boy, quite a bit of pomp... under the circumstances. - Well, Adam, today we are men. -\n" +
        "We are! - Bee-men. - Amen! Hallelujah!.............."); 
        dto.setEmail("bee@correo.com");
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("máximo 50 caracteres"), "Debería rechazar nombres de más de 50 caracteres.");
    }

    /**
     * Prueba 5: Valida que los teléfonos deban tener exactamente 10 dígitos (Regla del PDF).
     */
    @Test
    public void testTelefonoInvalido() {
        CustomerDAO dao = new CustomerDAO();

        CustomerDTO dto = new CustomerDTO();
        dto.setName("Carlos andres");
        dto.setEmail("carlos@correo.com");
        dto.setTelephone(new ArrayList<>(Arrays.asList("644123"))); //deben ser 10digitos
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("exactamente 10 dígitos"), "Debería rechazar teléfonos que no tengan 10 dígitos.");
    }

    /**
     * Prueba 6: Valida que el nivel de un cliente Premium esté entre 1 y 5.
     */
    @Test
    public void testPremiumFueraDeRango() {
        CustomerDAO dao = new CustomerDAO();

        CustomerDTO dto = new CustomerDTO();
        dto.setName("Loncho Ortiz");
        dto.setEmail("chito@ortiz.com");
        dto.setPremium(true);
        dto.setPoints(10);
        dto.setLevel(6); //el nivel maximo es 5
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.contains("Error: el nivel Premium debe estar entre 1 y 5."), "Debería rechazar niveles mayores a 5.");
    }
}