/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.RestaurantDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos (CRUD) para los restaurantes.
 * 
 * @author ACER
 */
public interface IRestaurantDAO {
    /**
     * Guarda un nuevo restaurante en la base de datos.
     * @param restaurante DTO con los datos del restaurante.
     * @return El ID generado o null si falla.
     */
    Long guardar(RestaurantDTO restaurante);

    /**
     * Actualiza los datos de un restaurante.
     * @param id ID del restaurante.
     * @param restaurante DTO con los nuevos datos.
     * @return Mensaje de éxito o error.
     */
    String actualizar(Long id, RestaurantDTO restaurante);

    /**
     * Elimina un restaurante.
     * @param id ID del restaurante.
     * @return Mensaje de éxito o error.
     */
    String eliminar(Long id);

    /**
     * Busca un restaurante por su ID.
     * @param id ID del restaurante.
     * @return El DTO del restaurante.
     */
    RestaurantDTO consultarPorId(Long id);

    /**
     * Obtiene todos los restaurantes registrados.
     * @return Lista de RestaurantDTO.
     */
    List<RestaurantDTO> consultarTodos();
}
