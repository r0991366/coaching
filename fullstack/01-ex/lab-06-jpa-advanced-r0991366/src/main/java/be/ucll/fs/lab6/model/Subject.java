package be.ucll.fs.lab6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer maxNrOfParticipants;
    private String title;

    protected Subject() {}

    public Subject(String title, Integer maxNrOfParticipants) {
        this.maxNrOfParticipants = maxNrOfParticipants;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public Integer getMaxNrOfParticipants() {
        return maxNrOfParticipants;
    }

    public String getTitle() {
        return title;
    }
}
