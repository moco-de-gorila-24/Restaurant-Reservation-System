/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author luis
 */
@Entity
public class DiningTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTable;

    @Column(name = "number", nullable = false, length = 10)
    private int number;

    @Column(name = "capacity", nullable = false, length = 1)
    private int capacity;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;


    public DiningTable() {
    }

    public DiningTable(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public Long getId() {
        return idTable;
    }

    public void setId(Long id) {
        this.idTable = id;
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
    
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Mesa " + number + " = capacidad: " + capacity;
    }
    
}
