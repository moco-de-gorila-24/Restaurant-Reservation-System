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
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "state", nullable = true, length = 100)
    private String state;

    @Column(name = "postCode", nullable = false, length = 5) //5 por que es el estandar en mexico
    private int postCode;

    @Column(name = "city", nullable = true, length = 100)
    private String city;

    @Column(name = "number", nullable = true, length = 6) //solo lo puse por legibilidad y por que es simetrico
    private int number;
    
    public Address() {
    }

    public Address(String street, String state, int postCode, String city, int number) {
        this.street = street;
        this.state = state;
        this.postCode = postCode;
        this.city = city;
        this.number = number;
    }

    public Long getId() {
        return idDireccion;
    }

    public Address setId(Long id) {
        this.idDireccion = id;
        return this;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public Address setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public int getPostCode() {
        return postCode;
    }

    public Address setPostCode(int postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Address setNumber(int number) {
        this.number = number;
        return this;
    }

}
