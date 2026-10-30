package be.ucll.fullstack.lab4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecturer")
//because JsonBackReference doesnt work
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String expertise;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "lecturers")
    //@JsonBackReference
    private List<Course> courses = new ArrayList<>();

    protected Lecturer() {}

    public Lecturer(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public List<Course> getCourses() {return this.courses;}

}