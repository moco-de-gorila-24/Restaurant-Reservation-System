/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.DiningTableDTO;
import java.util.List;


/**
 * Interfaz que define las operaciones de acceso a datos (CRUD) para las mesas.
 * 
 * @author ACER
 */
public interface IDiningTableDAO {
    /**
     * Guarda una lista de mesas asociadas a un restaurante.
     * @param mesa Lista de DTOs de las mesas.
     * @return Mensaje de éxito o error.
     */
    String guardar(List<DiningTableDTO> mesa);

    /**
     * Actualiza los datos de una mesa.
     * @param id ID de la mesa.
     * @param mesa DTO con los nuevos datos.
     * @return Mensaje de éxito o error.
     */
    String actualizar(Long id, DiningTableDTO mesa);

    /**
     * Elimina una mesa si no tiene reservaciones.
     * @param id ID de la mesa.
     * @return Mensaje de éxito o error.
     */
    String eliminar(Long id);

    /**
     * Busca una mesa por su ID.
     * @param id ID de la mesa.
     * @return El DTO de la mesa.
     */
    DiningTableDTO consultarPorId(Long id);

    /**
     * Obtiene todas las mesas registradas.
     * @return Lista de DiningTableDTO.
     */
    List<DiningTableDTO> consultarTodos();

    /**
     * Obtiene las mesas pertenecientes a un restaurante específico.
     * @param restaurantId ID del restaurante.
     * @return Lista de mesas del restaurante.
     */
    List<DiningTableDTO> consultarPorRestaurante(Long restaurantId);
}
