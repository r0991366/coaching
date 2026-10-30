package be.ucll.fs.lab5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DrivingLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String license;
    private String transmission;

    protected DrivingLicense() {}

    public DrivingLicense(String license, String transmission) {
        this.license = license;
        this.transmission = transmission;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLicense() {
        return license;
    }

    public String getTransmission() {
        return transmission;
    }
}
