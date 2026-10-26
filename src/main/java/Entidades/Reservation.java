package Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;

/**
 *
 * @author luis
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idReservation;

    @Column(name = "date", nullable = false, length = 10) //dd/mm/yyyy
    private LocalDate date;

    @Column(name = "hour")
    private LocalTime hour;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalTime hour) {
        this.date = date;
        this.hour = hour;
    }

    public Long getId() {
        return idReservation;
    }

    public void setId(Long id) {
    this.idReservation = id;
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
}
