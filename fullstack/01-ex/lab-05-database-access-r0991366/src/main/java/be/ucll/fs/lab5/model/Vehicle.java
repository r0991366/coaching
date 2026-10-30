package be.ucll.fs.lab5.model;

import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;

    @ManyToOne
    @JoinColumn(name = "license_id")
    private DrivingLicense drivingLicense;

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }
}
