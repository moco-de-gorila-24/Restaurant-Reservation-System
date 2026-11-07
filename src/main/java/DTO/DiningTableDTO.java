/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad DiningTable.
 * Representa una mesa en la interfaz gráfica.
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

    public DiningTableDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public DiningTableDTO setNumber(int number) {
        this.number = number;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public DiningTableDTO setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public DiningTableDTO setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }
}