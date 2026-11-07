    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTO.AddressDTO;
import Entidades.Address;

/**
 *
 * @author ACER
 */
public class AddressMapper {

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