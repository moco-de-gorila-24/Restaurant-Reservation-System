/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.CustomerDTO;
import Entidades.Customer;
import Entidades.PremiumCustomer;

/**
 *
 * @author ACER
 */
public class CustomerMapper {

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
