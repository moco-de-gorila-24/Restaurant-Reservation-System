/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;

/**
 * Entidad JPA que representa una reservación realizada por un cliente en una mesa específica.
 * Almacena la fecha, hora de inicio, hora de fin y el número de personas.
 * 
 * @author luis
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name = "date", nullable = false) //dd/mm/yyyy
    private LocalDate date;

    @Column(name = "hour")
    private LocalTime hour;
    
    @Column(name = "endTime", nullable = false)
    private LocalTime endTime;
    
    @Column(name = "number_people", nullable = false)
    private int numberPeople;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private DiningTable diningTable;
    

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalTime hour) {
        this.date = date;
        this.hour = hour;
    }

    public Long getId() {
        return idReservation;
    }

    public Reservation setId(Long id) {
        this.idReservation = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Reservation setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public LocalTime getHour() {
        return hour;
    }

    public Reservation setHour(LocalTime hour) {
        this.hour = hour;
        return this;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }

    public Reservation setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public Reservation setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Reservation setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public Reservation setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
        return this;
    }
        
}
