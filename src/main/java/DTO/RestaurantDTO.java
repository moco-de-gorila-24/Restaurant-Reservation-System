/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
