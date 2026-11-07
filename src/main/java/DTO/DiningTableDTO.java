/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ACER
 */
public class DiningTableDTO {

    private Long id;
    private int number;
    private int capacity;
    private Long restaurantId;

    public DiningTableDTO() {
    }

    public DiningTableDTO(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public DiningTableDTO( int number, int capacity, Long restaurantId) {
        this.number = number;
        this.capacity = capacity;
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Mesa " + number + " = capacidad: " + capacity;
    }
}