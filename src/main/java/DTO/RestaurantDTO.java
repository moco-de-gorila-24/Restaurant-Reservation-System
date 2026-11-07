/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Restaurant.
 * 
 * @author ACER
 */
public class RestaurantDTO {

    private Long id;
    private String name;
    private String telephone;
    private String kitchenType;
    private Long addressId;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name, String telephone, String kitchenType, Long addressId) {
        this.name = name;
        this.telephone = telephone;
        this.kitchenType = kitchenType;
        this.addressId = addressId;
    }

    public RestaurantDTO(Long id, String name, String telephone, String kitchenType, Long addressId) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.kitchenType = kitchenType;
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public RestaurantDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public RestaurantDTO setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public RestaurantDTO setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
        return this;
    }

    public Long getAddressId() {
        return addressId;
    }

    public RestaurantDTO setAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }
}
