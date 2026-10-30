package be.ucll.fs.lab5.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private String addressWithHouseNumber;
    private String city;
    private Integer zipCode;

    @ManyToMany
    @JoinTable(
            name = "employee_driving_license",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "driving_license_id")
    )
    private List<DrivingLicense> drivingLicenses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddressWithHouseNumber() {
        return addressWithHouseNumber;
    }

    public String getCity() {
        return city;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public List<DrivingLicense> getDrivingLicenses() {
        return drivingLicenses;
    }
}
