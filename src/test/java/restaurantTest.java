/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import DAO.RestaurantDAO;
import DTO.RestaurantDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class restaurantTest {

    @Test
    public void testGuardarRestauranteDatosValidos() {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName("Tacos El Fogón");
        dto.setKitchenType("Mexicana");
        dto.setStreet("Calle 5");
        dto.setPostCode(85000);
        dto.setNumber(123);
        
        // Nota: Esto sí intentará ir a la BD. Asegúrate de tener la conexión activa.
        String resultado = dao.guardar(dto);
        assertTrue("Debería guardarse correctamente", resultado.contains("guardado correctamente"));
    }

    @Test
    public void testGuardarRestauranteSinNombre() {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(""); // Error
        dto.setKitchenType("Italiana");
        dto.setStreet("Av. Principal");
        
        String resultado = dao.guardar(dto);
        assertTrue("Debe rechazar restaurantes sin nombre", resultado.contains("campos obligatorios"));
    }

    @Test
    public void testGuardarRestauranteSinCalle() {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName("Pizzería Roma");
        dto.setKitchenType("Italiana");
        dto.setStreet(""); // Error
        
        String resultado = dao.guardar(dto);
        assertTrue("Debe rechazar restaurantes sin calle", resultado.contains("campos obligatorios"));
    }
}