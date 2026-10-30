package be.ucll.fullstack.lab4.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
//because JsonManagedReference doesnt work
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Size(min = 5, max = 25)
    @NotNull
    private String name;

    @Min(1)
    @Max(18)
    @NotNull
    private int credits;

    //owning side
    @ManyToMany
    //@JsonManagedReference
    @JoinTable(name = "course_lecturer",
                joinColumns = @JoinColumn(name = "course_id"),
                inverseJoinColumns = @JoinColumn(name= "lecturer_id"))
    private Set<Lecturer> lecturers = new HashSet<>();

    protected Course() {};

    public Course(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public Long getId() {return this.Id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getCredits() {return this.credits;}
    public void setCredits(int credits) {this.credits = credits;}

    public Set<Lecturer> getLecturers() {return this.lecturers;}
    public void addLecturer(Lecturer lecturer) { this.lecturers.add(lecturer); }

}
