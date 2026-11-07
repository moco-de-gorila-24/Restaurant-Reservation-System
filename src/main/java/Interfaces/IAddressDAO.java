/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.AddressDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Address.
 * 
 * @author ACER
 */
public interface IAddressDAO {
     /**
     * Inserta una nueva dirección en la base de datos.
     * @param addressDTO Datos de la dirección.
     * @return El ID generado o null si falla.
     */
    Long agregar(AddressDTO addressDTO);
    
    /**
     * Actualiza los datos de una dirección existente.
     * @param id ID de la dirección a actualizar.
     * @param direccion Nuevos datos de la dirección.
     * @return Mensaje indicando el resultado de la operación.
     */
    String actualizar(Long id, AddressDTO direccion);
    
    /**
     * Elimina una dirección de la base de datos.
     * @param id ID de la dirección.
     * @return Mensaje indicando el resultado.
     */
    String eliminar(Long id);
    
    /**
     * Busca una dirección por su ID.
     * @param id ID de la dirección.
     * @return El DTO de la dirección o null si no existe.
     */
    AddressDTO consultarPorId(Long id);
    
    /**
     * Obtiene todas las direcciones registradas.
     * @return Lista de AddressDTO.
     */
    List<AddressDTO> consultarTodos();
}
