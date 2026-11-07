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
 * Clase adaptadora para convertir entre la entidad DiningTable y DiningTableDTO.
 * 
 * @author ACER
 */
public class DiningTableMapper {
    /**
     * Convierte una lista de entidades a una lista de DTOs.
     * @param entity Lista de mesas.
     * @return Lista de DiningTableDTO.
     */
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

    /**
     * Convierte una entidad DiningTable a DiningTableDTO.
     * @param entity Entidad de la mesa.
     * @return DTO con los datos.
     */
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
    /**
     * Convierte una lista de DTOs a una lista de entidades, asociándolas a un restaurante.
     * @param dto Lista de DTOs.
     * @param restaurant Restaurante al que pertenecen.
     * @return Lista de entidades.
     */
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
    
    /**
     * Convierte un DTO a entidad asociándola a un restaurante.
     * @param dto DTO de la mesa.
     * @param restaurant Restaurante al que pertenece.
     * @return Entidad de la mesa.
     */
    public static DiningTable toEntity(DiningTableDTO dto, Restaurant restaurant) {

        if (dto == null || restaurant == null) {
            throw new IllegalArgumentException("La mesa y el restaurante son obligatorios.");
        }

        DiningTable entity = new DiningTable(dto.getNumber(), dto.getCapacity());

        entity.setRestaurant(restaurant);

        return entity;
    }
    
    /**
     * Actualiza una entidad existente con los datos de un DTO.
     * @param entity Entidad a modificar.
     * @param dto Nuevos datos.
     * @param restaurant Restaurante asociado.
     */
    public static void actualizarEntidad(DiningTable entity, DiningTableDTO dto, Restaurant restaurant) {

        if (entity == null || dto == null || restaurant == null) {
            throw new IllegalArgumentException("La mesa, sus datos y el restaurante son obligatorios.");
        }

        entity.setNumber(dto.getNumber());
        entity.setCapacity(dto.getCapacity());
        entity.setRestaurant(restaurant);
    }
}
