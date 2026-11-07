    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.AddressDTO;
import Entidades.Address;

/**
 * Clase encargada de convertir objetos entre la entidad Address y AddressDTO.
 * 
 * @author ACER
 */
public class AddressMapper {

    /**
     * Convierte una entidad Address a un AddressDTO.
     * @param entity Entidad de la base de datos.
     * @return DTO con los datos, o null si la entidad es nula.
     */
    public static AddressDTO toDTO(Address entity) {
        if (entity == null) {
            return null;
        }

        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setStreet(entity.getStreet());
        dto.setState(entity.getState());
        dto.setPostCode(entity.getPostCode());
        dto.setCity(entity.getCity());
        dto.setNumber(entity.getNumber());

        return dto;
    }
    
    /**
     * Convierte un AddressDTO a una entidad Address.
     * @param addressDTO DTO con los datos.
     * @return Entidad lista para persistir, o null si el DTO es nulo.
     */
    public static Address toEntity(AddressDTO addressDTO){
        if (addressDTO == null){
            return null;
        }
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setStreet(addressDTO.getStreet());
        address.setState(addressDTO.getState());
        address.setPostCode(addressDTO.getPostCode());
        address.setCity(addressDTO.getCity());
        address.setNumber(addressDTO.getNumber());

        return address;
    }
}