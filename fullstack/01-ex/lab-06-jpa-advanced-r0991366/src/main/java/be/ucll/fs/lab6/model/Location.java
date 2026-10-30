package be.ucll.fs.lab6.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String addressWithHouseNumber;
    private String city;
    private Integer zipCode;

    @OneToMany(mappedBy = "location")
    private List<Classroom> classrooms = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}
