package be.ucll.fs.lab5.model;

import be.ucll.fs.lab5.controller.dto.DrivingLicenseInput;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private Boolean passedTheoryExam;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_driving_license",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "driving_license_id")
    )
    private List<DrivingLicense> drivingLicenses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "customer_theory_lesson",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private List<Lesson> lessons = new ArrayList<>();

    protected Customer() {}

    public Customer(String firstName, String name, Boolean passedTheoryExam) {
        this.name = name;
        this.firstName = firstName;
        this.passedTheoryExam = passedTheoryExam;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public Boolean getPassedTheoryExam() {
        return passedTheoryExam;
    }

    public List<DrivingLicense> getDrivingLicenses() {
        return drivingLicenses;
    }

    public void addDrivingLicense(DrivingLicense license) {
        drivingLicenses.add(license);
    }

    public void addDrivingLicenses(List<DrivingLicense> licenses) {
        for (DrivingLicense license : licenses) {
            this.addDrivingLicense(license);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
