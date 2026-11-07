/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Reservation.
 * 
 * @author ACER
 */
public class ReservationDTO {

    private Long id;
    private LocalDate date;
    private LocalTime hour;
    private LocalTime endTime;  
    private int numberPeople;

    private Long customerId;
    private String customerName;

    private Long tableId;
    private int tableNumber;
    private int tableCapacity;

    public ReservationDTO() {
    }

    public ReservationDTO(LocalDate date, LocalTime hour, int numberPeople, Long customerId, String customerName, Long tableId, int tableNumber, int tableCapacity) {
        this.date = date;
        this.hour = hour;
        this.numberPeople = numberPeople;
        this.customerId = customerId;
        this.customerName = customerName;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.tableCapacity = tableCapacity;
    }

    public ReservationDTO(Long id, LocalDate date, LocalTime hour, int numberPeople, Long customerId, String customerName, Long tableId, int tableNumber, int tableCapacity) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.numberPeople = numberPeople;
        this.customerId = customerId;
        this.customerName = customerName;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.tableCapacity = tableCapacity;
    }

    public Long getId() {
        return id;
    }

    public ReservationDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public LocalTime getHour() {
        return hour;
    }

    public ReservationDTO setHour(LocalTime hour) {
        this.hour = hour;
        return this;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }

    public ReservationDTO setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public ReservationDTO setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ReservationDTO setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ReservationDTO setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Long getTableId() {
        return tableId;
    }

    public ReservationDTO setTableId(Long tableId) {
        this.tableId = tableId;
        return this;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public ReservationDTO setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public ReservationDTO setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
        return this;
    }
    
    @Override
    public String toString() {
        return "Numero de reservación:" + id + " - Fecha: " + date + " - Hora: " + hour + " - Personas: " + numberPeople;
    }
}
