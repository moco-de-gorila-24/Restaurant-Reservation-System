/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.RestaurantDTO;
import Entidades.Restaurant;

public class RestaurantMapper {

    public static RestaurantDTO toDTO(Restaurant entity) {
        if (entity == null) {
            return null;
        }

        RestaurantDTO dto = new RestaurantDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTelephone(entity.getTelephone());
        dto.setKitchenType(entity.getKitchenType());
        dto.setAddressId(entity.getAddress() != null ? entity.getAddress().getId() : null);

        return dto;
    }

    public static Restaurant toEntity(RestaurantDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Restaurant(dto.getName(), dto.getTelephone(), dto.getKitchenType());
    }

    /**
     * Actualiza los campos propios del restaurante.
     * No toca la dirección ni las mesas: cada una se gestiona con su DAO.
     */
    public static Restaurant actualizarEntidad(Restaurant restaurant, RestaurantDTO dto) {
        if (restaurant == null || dto == null) {
            return null;
        }

        restaurant.setName(dto.getName());
        restaurant.setTelephone(dto.getTelephone());
        restaurant.setKitchenType(dto.getKitchenType());

        return restaurant;
    }
}