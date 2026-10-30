package be.ucll.se.courses.backend.repository.jdbc;

import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.repository.ScheduleRepository;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.ScheduleRowMapper;
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
public class JdbcScheduleRepository implements ScheduleRepository {

    private final JdbcClient jdbcClient;

    public JdbcScheduleRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Schedule save(Schedule schedule) {
        Instant creationInstant = Instant.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("""
                    insert into schedule (created_at, updated_at, "start", "end", course_id, lecturer_id) \
                    values (:createdAt, :updatedAt, :start, :end, :courseId, :lecturerId) returning id""")
                .param("createdAt", Timestamp.from(creationInstant))
                .param("updatedAt", Timestamp.from(creationInstant))
                .param("start", Timestamp.from(schedule.getStart()))
                .param("end", Timestamp.from(schedule.getEnd()))
                .param("courseId", schedule.getCourse().getId())
                .param("lecturerId", schedule.getLecturer().getId())
                .update(keyHolder);

        schedule.setId(keyHolder.getKeyAs(Long.class));

        for (Student student : schedule.getStudents()) {
            jdbcClient.sql("""
                        insert into schedule_students (schedule_id, student_id) \
                        values (:scheduleId, :studentId)""")
                    .param("scheduleId", schedule.getId())
                    .param("studentId", student.getId())
                    .update();
        }

        return schedule;
    }

    @Override
    public void deleteAll() {
        jdbcClient.sql("truncate table schedule cascade").update();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return jdbcClient.sql("""
                    select schedule.id as schedule_id, "start", "end", \
                      course.id as course_id, course.name as course_name, description, phase, credits, \
                      lecturer.id as lecturer_id, expertise, \
                      public.user.id as user_id, username, first_name, last_name, email, password, role \
                    from schedule \
                      inner join course on schedule.course_id = course.id \
                      inner join lecturer on schedule.lecturer_id = lecturer.id \
                      inner join public.user on lecturer.user_id = public.user.id \
                    where schedule.id = :id""")
                .param("id", id)
                .query(new ScheduleRowMapper())
                .optional();
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcClient.sql("""
                    select schedule.id as schedule_id, "start", "end", \
                      course.id as course_id, course.name as course_name, description, phase, credits, \
                      lecturer.id as lecturer_id, expertise, \
                      public.user.id as user_id, username, first_name, last_name, email, password, role \
                    from schedule \
                      inner join course on schedule.course_id = course.id \
                      inner join lecturer on schedule.lecturer_id = lecturer.id \
                      inner join public.user on lecturer.user_id = public.user.id""")
                .query(new ScheduleRowMapper())
                .list();
    }

    @Override
    public List<Schedule> findByLecturer_User_Username(String username) {
        return jdbcClient.sql("""
                    select schedule.id as schedule_id, "start", "end", \
                      course.id as course_id, course.name as course_name, description, phase, credits, \
                      lecturer.id as lecturer_id, expertise, \
                      public.user.id as user_id, username, first_name, last_name, email, password, role \
                    from schedule \
                      inner join course on schedule.course_id = course.id \
                      inner join lecturer on schedule.lecturer_id = lecturer.id \
                      inner join public.user on lecturer.user_id = public.user.id \
                    where public.user.username = :username""")
                .param("username", username)
                .query(new ScheduleRowMapper())
                .list();
    }

    @Override
    public boolean existsByCourse_IdAndLecturer_Id(Long courseId, Long lecturerId) {
        return jdbcClient.sql("""
                    select id \
                    from schedule \
                    where course_id = :courseId and lecturer_id = :lecturerId""")
                .param("courseId", courseId)
                .param("lecturerId", lecturerId)
                .query(Schedule.class)
                .optional().isPresent();
    }
}
