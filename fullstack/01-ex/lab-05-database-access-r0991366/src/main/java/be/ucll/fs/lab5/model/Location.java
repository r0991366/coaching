package be.ucll.fs.lab5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String addressWithHouseNumber;
    private String city;
    private Integer zipCode;

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
}
