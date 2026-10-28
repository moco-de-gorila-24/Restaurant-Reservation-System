/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.RestaurantDTO;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface IRestaurantDAO {

    String guardar(RestaurantDTO restaurante);

    String actualizar(Long id, RestaurantDTO restaurante);

    String eliminar(Long id);

    RestaurantDTO consultarPorId(Long id);

    List<RestaurantDTO> consultarTodos();
}
