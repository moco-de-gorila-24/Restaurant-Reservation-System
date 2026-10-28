/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ACER
 */
public class ReservationDTO {

    private Long id;
    private LocalDate date;
    private LocalTime hour;
    private int numberPeople;

    private Long customerId;
    private String customerName;

    private Long tableId;
    private int tableNumber;
    private int tableCapacity;

    public ReservationDTO() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
        this.tableCapacity = tableCapacity;
    }

    @Override
    public String toString() {
        return "Numero de reservación:" + id + " - Fecha: " + date + " - Hora: " + hour + " - Personas: " + numberPeople;
    }
}
