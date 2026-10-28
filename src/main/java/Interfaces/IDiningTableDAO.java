/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.DiningTableDTO;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface IDiningTableDAO {

    String guardar(DiningTableDTO mesa);
    String actualizar(Long id, DiningTableDTO mesa);
    String eliminar(Long id);
    DiningTableDTO consultarPorId(Long id);
    List<DiningTableDTO> consultarTodos();
    List<DiningTableDTO> consultarPorRestaurante(Long restaurantId);
}
