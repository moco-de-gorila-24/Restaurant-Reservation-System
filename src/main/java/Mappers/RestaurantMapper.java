/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.RestaurantDTO;
import Entidades.Address;
import Entidades.Restaurant;

/**
 *
 * @author ACER
 */
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

        Address address = entity.getAddress();

        if (address != null) {
            dto.setAddressId(address.getIdDireccion());
            dto.setStreet(address.getStreet());
            dto.setState(address.getState());
            dto.setPostCode(address.getPostCode());
            dto.setCity(address.getCity());
            dto.setNumber(address.getNumber());
        }

        return dto;
    }

    public static Restaurant toEntity(RestaurantDTO dto) {
        if (dto == null) {
            return null;
        }

        Restaurant restaurant = new Restaurant(dto.getName(), dto.getTelephone(), dto.getKitchenType());
        
        Address address = new Address(dto.getStreet(), dto.getState(), dto.getPostCode(), dto.getCity(), dto.getNumber());
        restaurant.setAddress(address);

        return restaurant;
    }

    /**
     * Actualiza los campos editables de un restaurante existente.
     * Conserva su identificador y sus mesas.
     */
    public static Restaurant actualizarEntidad(Restaurant restaurant, RestaurantDTO dto) {

        if (restaurant == null || dto == null) {
            return null;
        }

        restaurant.setName(dto.getName());
        restaurant.setTelephone(dto.getTelephone());
        restaurant.setKitchenType(dto.getKitchenType());

        Address address = restaurant.getAddress();

        if (address == null) {
            address = new Address();
            restaurant.setAddress(address);
        }

        address.setStreet(dto.getStreet());
        address.setState(dto.getState());
        address.setPostCode(dto.getPostCode());
        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());

        return restaurant;
    }
}
