package be.ucll.se.courses.backend.repository.jdbc;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.repository.CourseRepository;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.CourseRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcCourseRepository implements CourseRepository {

    private final JdbcClient jdbcClient;

    public JdbcCourseRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Course save(Course course) {
        Instant creationInstant = Instant.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("""
                    insert into course (created_at, updated_at, name, description, phase, credits) \
                    values (:createdAt, :updatedAt, :name, :description, :phase, :credits) returning id""")
                .param("createdAt", Timestamp.from(creationInstant))
                .param("updatedAt", Timestamp.from(creationInstant))
                .param("name", course.getName())
                .param("description", course.getDescription())
                .param("phase", course.getPhase())
                .param("credits", course.getCredits())
                .update(keyHolder);

        course.setId(keyHolder.getKeyAs(Long.class));

        for (Lecturer lecturer : course.getLecturers()) {
            jdbcClient.sql("""
                        insert into course_lecturers (course_id, lecturer_id) \
                        values (:courseId, :lecturerId)""")
                    .param("courseId", course.getId())
                    .param("lecturerId", lecturer.getId())
                    .update();
        }

        return course;
    }

    @Override
    public void deleteAll() {
        jdbcClient.sql("truncate table course cascade").update();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jdbcClient.sql("""
                    select id as course_id, name as course_name, description, phase, credits \
                    from course \
                    where id = :id""")
                .param("id", id)
                .query(new CourseRowMapper())
                .optional();
    }

    @Override
    public List<Course> findAll() {
        return jdbcClient.sql("""
                    select id as course_id, name as course_name, description, phase, credits \
                    from course\s""")
                .query(new CourseRowMapper())
                .list();
    }
}
