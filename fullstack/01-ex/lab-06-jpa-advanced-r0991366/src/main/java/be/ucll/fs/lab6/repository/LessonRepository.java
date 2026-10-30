package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("""
        select l
        from Lesson l
            join fetch l.subject
            join fetch l.classroom c
                join fetch c.location
            join fetch l.employee
            join fetch l.vehicle v
                join fetch v.drivingLicense
    """)
    List<Lesson> findAllWithJoinFetch();
}
