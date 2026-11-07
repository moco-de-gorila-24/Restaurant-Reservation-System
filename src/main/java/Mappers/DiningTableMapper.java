/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.DiningTableDTO;
import Entidades.DiningTable;
import Entidades.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class DiningTableMapper {

    public static List<DiningTableDTO> toListDTO(List<DiningTable> entity){
        if (entity == null) {
            throw new IllegalArgumentException("La mesa y el restaurante son obligatorios.");
        }
        List<DiningTableDTO> dto = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++) {
            dto.add(toDTO(entity.get(i)));
        }
        return dto;
    }

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
        }

        return dto;
    }

    public static List<DiningTable> toListEntity(List<DiningTableDTO> dto, Restaurant restaurant){
        if (dto == null || restaurant == null) {
            throw new IllegalArgumentException("La mesa y el restaurante son obligatorios.");
        }
        List<DiningTable> entidad = new ArrayList<>();
        for (int i = 0; i < dto.size(); i++) {
            entidad.add(toEntity(dto.get(i), restaurant));
        }
        return entidad;
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
