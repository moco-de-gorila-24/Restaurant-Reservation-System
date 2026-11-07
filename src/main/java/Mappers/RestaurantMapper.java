/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.RestaurantDTO;
import Entidades.Restaurant;

/**
 * Clase adaptadora para convertir entre la entidad Restaurant y RestaurantDTO.
 * 
 * @author ACER
 */
public class RestaurantMapper {

    /**
     * Convierte una entidad Restaurant a RestaurantDTO.
     * @param entity Entidad del restaurante.
     * @return DTO con los datos.
     */
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
    
    /**
     * Convierte un RestaurantDTO a entidad.
     * @param dto Datos del restaurante.
     * @return Entidad lista para persistir.
     */
    public static Restaurant toEntity(RestaurantDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Restaurant(dto.getName(), dto.getTelephone(), dto.getKitchenType());
    }

    /**
     * Actualiza los campos editables de un restaurante existente.
     * No toca la dirección ni las mesas.
     * @param restaurant Entidad a modificar.
     * @param dto Nuevos datos.
     * @return El restaurante modificado.
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