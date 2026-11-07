/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.CustomerDTO;
import Entidades.Customer;
import Entidades.PremiumCustomer;

/**
 * Clase adaptadora para convertir entre las entidades de Cliente y CustomerDTO.
 * Maneja la lógica de instanciar un PremiumCustomer si el DTO lo indica.
 * 
 * @author ACER
 */
public class CustomerMapper {

    /**
     * Convierte una entidad Customer o PremiumCustomer a CustomerDTO.
     * @param entity Entidad del cliente.
     * @return DTO con los datos correspondientes.
     */
    public static CustomerDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        boolean premium = entity instanceof PremiumCustomer;
        int points = 0;
        int level = 1;

        if (entity instanceof PremiumCustomer) {
            PremiumCustomer p = (PremiumCustomer) entity;
            points = p.getPoints();
            level = p.getLevel();
        }

        return new CustomerDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getTelephone(), premium, points, level);
    }

    /**
     * Convierte un CustomerDTO a su entidad que corresponde Customer o PremiumCustomer.
     * @param dto DTO con los datos del cliente.
     * @return Entidad lista para persistir.
     */
    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        if (dto.isPremium()) {
            return new PremiumCustomer(dto.getName(), dto.getTelephone(), dto.getEmail(), dto.getPoints(), dto.getLevel());
        }

        return new Customer(dto.getName(), dto.getTelephone(), dto.getEmail());
    }
}
