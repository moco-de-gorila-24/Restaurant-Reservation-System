/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.AddressDTO;
import java.util.List;

/**
 *
 * @author ACER
 */
public interface IAddressDAO {
    String actualizar(Long id, AddressDTO direccion);
    String eliminar(Long id);
    AddressDTO consultarPorId(Long id);
    List<AddressDTO> consultarTodos();
}
