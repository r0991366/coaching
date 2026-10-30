package be.ucll.fs.lab6.model;

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

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    protected Lesson() {}

    public Lesson(LocalDate date, LocalTime timeStart, Integer durationMinutes, Subject subject) {
        this.date = date;
        this.timeStart = timeStart;
        this.durationMinutes = durationMinutes;
        this.subject = subject;
    }

    public void update(LocalDate date, LocalTime timeStart, Integer durationMinutes) {
        this.date = date;
        this.timeStart = timeStart;
        this.durationMinutes = durationMinutes;
    }

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
