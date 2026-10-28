/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.ReservationDTO;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface IReservationDAO {
    String guardar(ReservationDTO reservacion);
    String actualizar(Long id, ReservationDTO reservacion);
    String eliminar(Long id);
    ReservationDTO consultarPorId(Long id);
    List<ReservationDTO> consultarTodos();
    List<ReservationDTO> consultarPorCliente(Long customerId);
}
