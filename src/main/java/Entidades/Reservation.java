package Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 *
 * @author luis
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idResservation;

    @Column(name = "date", nullable = false, length = 10) //dd/mm/yyyy
    private LocalDate date;

    @Column(name = "hour")
    private LocalDate hour;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalDate hour) {
        this.date = date;
        this.hour = hour;
    }

    public Long getId() {
        return idResservation;
    }

    public void setId(Long id) {
        this.idResservation = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getHour() {
        return hour;
    }

    public void setHour(LocalDate hour) {
        this.hour = hour;
    }
}
