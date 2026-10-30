package be.ucll.se.courses.backend.repository.jdbc;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.CourseRowMapper;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.LecturerRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcLecturerRepository implements LecturerRepository {

    private final JdbcClient jdbcClient;

    public JdbcLecturerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Lecturer save(Lecturer lecturer) {
        Instant creationInstant = Instant.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("""
                    insert into lecturer (created_at, updated_at, expertise, user_id) \
                    values (:createdAt, :updatedAt, :expertise, :userId) returning id""")
                .param("createdAt", Timestamp.from(creationInstant))
                .param("updatedAt", Timestamp.from(creationInstant))
                .param("expertise", lecturer.getExpertise())
                .param("userId", lecturer.getUser().getId())
                .update(keyHolder);

        lecturer.setId(keyHolder.getKeyAs(Long.class));
        return lecturer;
    }

    public void deleteAll() {
        jdbcClient.sql("truncate table lecturer cascade").update();
    }

    public Optional<Lecturer> findById(Long id) {
        List<Lecturer> lecturers = jdbcClient.sql("""
                        select lecturer.id as lecturer_id, expertise, public.user.id as user_id, username, first_name, \
                            last_name, email, password, role, course.id as course_id, course.name as course_name, description, phase, credits \
                        from lecturer \
                          inner join public.user on lecturer.user_id = public.user.id \
                          inner join course_lecturers on lecturer.id = course_lecturers.lecturer_id \
                          inner join course on course_lecturers.course_id = course.id \
                        where lecturer.id = :id""")
                .param("id", id)
                .query(lecturersWithUserAndCourses);

        return lecturers.isEmpty() ? Optional.empty() : Optional.of(lecturers.get(0));
    }

    public List<Lecturer> findAll() {
        return jdbcClient.sql("""
                    select lecturer.id as lecturer_id, expertise, public.user.id as user_id, username, first_name, \
                        last_name, email, password, role, course.id as course_id, course.name as course_name, description, phase, credits \
                    from lecturer \
                      inner join public.user on lecturer.user_id = public.user.id \
                      inner join course_lecturers on lecturer.id = course_lecturers.lecturer_id \
                      inner join course on course_lecturers.course_id = course.id \
                    order by lecturer_id desc""")
                .query(lecturersWithUserAndCourses);
    }

    // This method assumes that all rows for one lecturer come right behind each other!
    private static final ResultSetExtractor<List<Lecturer>> lecturersWithUserAndCourses = (rs) -> {
        Lecturer currentLecturer = null;
        List<Long> lecturerIds = new ArrayList<>();
        List<Lecturer> lecturers = new ArrayList<>();

        while (rs.next()) {
            Long currentLecturerId = rs.getLong("lecturer_id");

            if (!lecturerIds.contains(currentLecturerId)) {
                Lecturer lecturer = new LecturerRowMapper().mapRow(rs, rs.getRow());

                lecturerIds.add(currentLecturerId);
                lecturers.add(lecturer);
                currentLecturer = lecturer;
            }

            Course course = new CourseRowMapper().mapRow(rs, rs.getRow());
            currentLecturer.addCourse(course);
        }

        return lecturers;
    };
}
