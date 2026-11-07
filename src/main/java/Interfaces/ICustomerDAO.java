/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.CustomerDTO;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos (CRUD) para los clientes.
 * 
 * @author ACER
 */
public interface ICustomerDAO {
    /**
     * Guarda un cliente (normal o premium) en la base de datos.
     * @param cliente DTO con los datos del cliente.
     * @return Mensaje de éxito o error.
     */
    String guardar(CustomerDTO cliente);
    
    /**
     * Actualiza los datos de un cliente.
     * @param id ID del cliente.
     * @param cliente DTO con los nuevos datos.
     * @return Mensaje de éxito o error.
     */
    String actualizar(Long id, CustomerDTO cliente);
    
    /**
     * Elimina un cliente si no tiene reservaciones asociadas.
     * @param id ID del cliente.
     * @return Mensaje de éxito o error.
     */   
    String eliminar(Long id);
    
    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente.
     * @return El DTO del cliente o null.
     */   
    CustomerDTO consultarPorId(Long id);
    
    /**
     * Obtiene todos los clientes registrados.
     * @return Lista de CustomerDTO.
     */
    List<CustomerDTO> consultarTodos();
}
