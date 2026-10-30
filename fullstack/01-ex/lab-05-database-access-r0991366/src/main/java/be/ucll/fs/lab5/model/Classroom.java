package be.ucll.fs.lab5.model;

import jakarta.persistence.*;

@Entity
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Long getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Location getLocation() {
        return location;
    }
}
