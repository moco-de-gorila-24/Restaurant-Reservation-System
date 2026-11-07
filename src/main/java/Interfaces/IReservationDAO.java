/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.ReservationDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos (CRUD) para las reservaciones.
 * 
 * @author ACER
 */
public interface IReservationDAO {
    /**
     * Guarda una nueva reservación aplicando reglas de negocio.
     * @param reservacion DTO con los datos de la reservación.
     * @return Mensaje de éxito o error.
     */
    String guardar(ReservationDTO reservacion);

    /**
     * Actualiza una reservación existente.
     * @param id ID de la reservación.
     * @param reservacion DTO con los nuevos datos.
     * @return Mensaje de éxito o error.
     */
    String actualizar(Long id, ReservationDTO reservacion);

    /**
     * Elimina una reservación.
     * @param id ID de la reservación.
     * @return Mensaje de éxito o error.
     */
    String eliminar(Long id);

    /**
     * Busca una reservación por su ID.
     * @param id ID de la reservación.
     * @return El DTO de la reservación.
     */
    ReservationDTO consultarPorId(Long id);

    /**
     * Obtiene todas las reservaciones.
     * @return Lista de ReservationDTO.
     */
    List<ReservationDTO> consultarTodos();

    /**
     * Obtiene las reservaciones de un cliente específico.
     * @param customerId ID del cliente.
     * @return Lista de reservaciones del cliente.
     */
    List<ReservationDTO> consultarPorCliente(Long customerId);
}
