/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.ReservationDTO;
import Entidades.Customer;
import Entidades.DiningTable;
import Entidades.Reservation;

/**
 * Clase adaptadora para convertir entre la entidad Reservation y ReservationDTO.
 * 
 * @author ACER
 */
public class ReservationMapper {
    
    /**
     * Convierte una entidad Reservation a ReservationDTO.
     * @param entity Entidad de la reservación.
     * @return DTO con los datos.
     */
    public static ReservationDTO toDTO(Reservation entity) {
        if (entity == null) {
            return null;
        }

        ReservationDTO dto = new ReservationDTO();

        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setHour(entity.getHour());
        dto.setEndTime(entity.getEndTime());
        dto.setNumberPeople(entity.getNumberPeople());

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
            dto.setCustomerName(entity.getCustomer().getName());
        }

        if (entity.getDiningTable() != null) {
            dto.setTableId(entity.getDiningTable().getId());
            dto.setTableNumber(entity.getDiningTable().getNumber());
            dto.setTableCapacity(entity.getDiningTable().getCapacity());
        }

        return dto;
    }
    
    
    /**
     * Crea una entidad Reservation a partir de un DTO y sus relaciones.
     * @param dto Datos de la reservación.
     * @param customer Cliente asociado.
     * @param table Mesa asociada.
     * @return Entidad lista para persistir.
     */
    public static Reservation toEntity(ReservationDTO dto, Customer customer, DiningTable table) {

        if (dto == null || customer == null || table == null) {
            throw new IllegalArgumentException("La reservación, el cliente y la mesa son obligatorios.");
        }

        Reservation entity = new Reservation(dto.getDate(), dto.getHour());

        entity.setNumberPeople(dto.getNumberPeople());
        entity.setCustomer(customer);
        entity.setDiningTable(table);

        return entity;
    }
    
    /**
     * Actualiza una entidad Reservation existente.
     * @param entity Entidad a modificar.
     * @param dto Nuevos datos.
     * @param customer Nuevo cliente.
     * @param table Nueva mesa.
     */
    public static void actualizarEntidad(Reservation entity, ReservationDTO dto, Customer customer, DiningTable table) {

        if (entity == null || dto == null || customer == null || table == null) {
            throw new IllegalArgumentException("Faltan datos para actualizar la reservación.");
        }

        entity.setDate(dto.getDate());
        entity.setHour(dto.getHour());
        entity.setNumberPeople(dto.getNumberPeople());
        entity.setCustomer(customer);
        entity.setDiningTable(table);
    }
}
