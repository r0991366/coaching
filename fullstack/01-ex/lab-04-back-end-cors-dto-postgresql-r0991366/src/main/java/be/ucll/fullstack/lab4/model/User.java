package be.ucll.fullstack.lab4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;   //can also import from .sql but let's do this

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {}

    public User(String name, LocalDate birthDate, Role role) {
        this.name = name;
        this.birthDate = birthDate;
        this.role = role;
    }

    public String getName() {return this.name; }
    public void setName(String name) {this.name = name; }

    public LocalDate getBirthDate() { return this.birthDate; }
    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate; }
    //   will probably have to do the dates a bit different but for now this is fine

    public Role getRole() {return this.role; }
    public void setRole(Role role) {this.role = role;}
    //      again maybe use strings instead?

}
