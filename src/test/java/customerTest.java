/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import DAO.CustomerDAO;
import DTO.CustomerDTO;
/**
 * 
 * @author Andres
 */
public class customerTest {

    @Test
    public void testGuardarClienteNombreVacio() {
        CustomerDAO dao = new CustomerDAO();
        CustomerDTO dto = new CustomerDTO();
        dto.setName(""); // Nombre vacío (Error)
        dto.setEmail("correo@valido.com");
        
        String resultado = dao.guardar(dto);
        assertTrue("Debe rechazar nombres vacíos", resultado.contains("obligatorio"));
    }

    @Test
    public void testGuardarClienteEmailInvalido() {
        CustomerDAO dao = new CustomerDAO();
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Juan Perez");
        dto.setEmail("correo-sin-arroba"); // Email inválido (Error)
        
        String resultado = dao.guardar(dto);
        assertTrue("Debe rechazar correos sin formato válido", resultado.contains("correo electrónico no tiene un formato válido"));
    }

    @Test
    public void testGuardarClientePremiumNivelInvalido() {
        CustomerDAO dao = new CustomerDAO();
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Ana Lopez");
        dto.setEmail("ana@valido.com");
        dto.setPremium(true);
        dto.setLevel(6); // El nivel máximo es 5 (Error)
        dto.setPoints(10);
        
        String resultado = dao.guardar(dto);
        assertTrue("Debe rechazar niveles mayores a 5", resultado.contains("nivel Premium debe estar entre 1 y 5"));
    }
}
