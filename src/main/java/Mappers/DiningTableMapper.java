/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.DiningTableDTO;
import Entidades.DiningTable;
import Entidades.Restaurant;

/**
 *
 * @author ACER
 */
public class DiningTableMapper {

    public static DiningTableDTO toDTO(DiningTable entity) {
        if (entity == null) {
            return null;
        }

        DiningTableDTO dto = new DiningTableDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setCapacity(entity.getCapacity());

        if (entity.getRestaurant() != null) {
            dto.setRestaurantId(entity.getRestaurant().getId());
            dto.setRestaurantName(entity.getRestaurant().getName());
        }

        return dto;
    }

    public static DiningTable toEntity(DiningTableDTO dto, Restaurant restaurant) {

        if (dto == null || restaurant == null) {
            throw new IllegalArgumentException("La mesa y el restaurante son obligatorios.");
        }

        DiningTable entity = new DiningTable(dto.getNumber(), dto.getCapacity());

        entity.setRestaurant(restaurant);

        return entity;
    }

    public static void actualizarEntidad(DiningTable entity, DiningTableDTO dto, Restaurant restaurant) {

        if (entity == null || dto == null || restaurant == null) {
            throw new IllegalArgumentException("La mesa, sus datos y el restaurante son obligatorios.");
        }

        entity.setNumber(dto.getNumber());
        entity.setCapacity(dto.getCapacity());
        entity.setRestaurant(restaurant);
    }
}
