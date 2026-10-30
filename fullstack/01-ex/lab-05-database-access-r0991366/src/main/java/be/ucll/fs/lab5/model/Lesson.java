package be.ucll.fs.lab5.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime timeStart;
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public Subject getSubject() {
        return subject;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
