/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.CustomerDTO;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface ICustomerDAO {
    String guardar(CustomerDTO cliente);
    String actualizar(Long id, CustomerDTO cliente);
    String eliminar(Long id);
    CustomerDTO consultarPorId(Long id);
    List<CustomerDTO> consultarTodos();
}
