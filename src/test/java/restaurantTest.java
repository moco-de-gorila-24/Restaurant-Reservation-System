/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import DAO.AddressDAO;
import DAO.RestaurantDAO;
import DTO.AddressDTO;
import DTO.RestaurantDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para validar la lógica de negocio y persistencia
 * de la entidad Restaurant (Restaurantes).
 * 
 * @author Andres
 */
public class restaurantTest {

    /**
     * Prueba 1: Verifica que un restaurante se pueda guardar exitosamente
     * siempre y cuando tenga una dirección válida asociada.
     */
    @Test
    public void testGuardarRestaurante() {
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        AddressDAO addressDAO = new AddressDAO();

        AddressDTO address = new AddressDTO("por ahi", "Sonora", 85000, "Cd. Obregon", 123);
        Long addressId = addressDAO.agregar(address);
        
        assertNotNull(addressId, "La dirección de prueba debió guardarse para obtener un ID.");

        RestaurantDTO dto = new RestaurantDTO("Tacos ricos", "6441234567", "Mexicana", addressId);
        
        Long restaurantId = restaurantDAO.guardar(dto);
        
        assertNotNull(restaurantId, "El restaurante debería guardarse exitosamente y retornar un ID.");
    }

    /**
     * Prueba 2: Valida que no se permita guardar un restaurante sin nombre.
     */
    @Test
    public void testSinNombre() {
        RestaurantDAO dao = new RestaurantDAO();
        
        RestaurantDTO dto = new RestaurantDTO("", "6441234567", "Italiana", 1L); 
        
        Long resultado = dao.guardar(dto);
        
        assertNull(resultado, "Debería rechazar el restaurante y retornar null por falta de nombre.");
    }

    /**
     * Prueba 3: Valida que el nombre del restaurante no supere los 30 caracteres 
     */
    @Test
    public void testNombreLargo() {
        RestaurantDAO dao = new RestaurantDAO();
        
        // Genera un nombre de 35 caracteres
        String nombreLargo = "Don't forget these. Milk, cream, cheese, it's all me. And I\n" +
        "don't see a nickel! Sometimes I just feel like a piece of meat! I had no idea. Barry, I'm sorry. Have\n" +
        "you got a moment? Would you excuse me? My mosquito associate will help you. Sorry I'm late.\n" +
        "He's a lawyer too? I was already a blood-sucking parasite. All I needed was a briefcase. Have a\n" +
        "great afternoon! Barry, I just got this huge tulip order, and I can't get them anywhere. No problem,\n" +
        "Vannie. Just leave it to me. You're a lifesaver, Barry. Oan I help who's next? All right, scramble,\n" +
        "jocks! It's time to fly. Thank you, Barry! That bee is living my life! Let it go, Kenny. - When will this\n" +
        "nightmare end?! - Let it all go. - Beautiful day to fly. - Sure is. Between you and me, I was dying to\n" +
        "get out of that office. You have got to start thinking bee, my friend. - Thinking bee! - Me? Hold it.\n" +
        "Let's just stop for a second. Hold it. I'm sorry. I'm sorry, everyone. Oan we stop here? I'm not\n" +
        "making a major life decision during a production number! All right. Take ten, everybody. Wrap it up,\n" +
        "guys. I had virtually no rehearsal for that."; 
        RestaurantDTO dto = new RestaurantDTO(nombreLargo, "6441234567", "Italiana", 1L);
        
        Long resultado = dao.guardar(dto);
        
        assertNull(resultado, "Debería rechazar nombres que superen los 30 caracteres.");
    }

    /**
     * Prueba 4: Valida que el teléfono, si se ingresa, solo contenga números 
     * y no supere los 10 dígitos.
     */
    @Test
    public void testTelefonoInvalido() {
        RestaurantDAO dao = new RestaurantDAO();
        
        RestaurantDTO dto = new RestaurantDTO("Pizzería Ricolina", "644-ABC-123", "Italiana", 1L);
        
        Long resultado = dao.guardar(dto);
        
        assertNull(resultado, "Debería rechazar teléfonos con formatos inválidos.");
    }

    /**
     * Prueba 5: Valida que no se pueda guardar un restaurante si el ID de la dirección
     * no existe en la base de datos.
     */
    @Test
    public void testRestauranteInexistente() {
        RestaurantDAO dao = new RestaurantDAO();
        
        RestaurantDTO dto = new RestaurantDTO("Sushi Padrisimo", "6441234567", "Japonesa", 999999L);
        
        Long resultado = dao.guardar(dto);
        
        assertNull(resultado, "Debería retornar null porque la dirección no existe en la BD.");
    }
}