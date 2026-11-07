/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ACER
 */
public class AddressDTO {

    private Long id;
    private String street;
    private String state;
    private int postCode;
    private String city;
    private int number;
    private Long restaurantId;

    public AddressDTO() {
    }

    public AddressDTO(String street, String state, int postCode, String city, int number) {
        this.street = street;
        this.state = state;
        this.postCode = postCode;
        this.city = city;
        this.number = number;
    }

    public AddressDTO(Long id, String street, String state, int postCode, String city, int number) {
        this.id = id;
        this.street = street;
        this.state = state;
        this.postCode = postCode;
        this.city = city;
        this.number = number;
    }

    public Long getId() {
        return id;
    }
    
    public AddressDTO setId(Long id) {
        this.id = id;  
        return this;
    }

    public String getStreet() {
        return street;
    }
    
    public AddressDTO setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getState() {
        return state;
    }
    public AddressDTO setState(String state) {
        this.state = state;
        return this;
    }

    public int getPostCode() {
        return postCode;
    }
    public AddressDTO setPostCode(int postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCity() {
        return city;
    }
    public AddressDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public int getNumber() {
        return number;
    }
    public AddressDTO setNumber(int number) {
        this.number = number;
        return this;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
    
}
