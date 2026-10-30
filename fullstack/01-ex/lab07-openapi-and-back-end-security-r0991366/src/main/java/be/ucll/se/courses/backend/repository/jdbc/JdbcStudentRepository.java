package be.ucll.se.courses.backend.repository.jdbc;

import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.repository.StudentRepository;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.StudentRowMapper;
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
public class JdbcStudentRepository implements StudentRepository {

    private final JdbcClient jdbcClient;

    public JdbcStudentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Student save(Student student) {
        Instant creationInstant = Instant.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("""
                    insert into student (created_at, updated_at, student_number, user_id) \
                    values (:createdAt, :updatedAt, :studentNumber, :userId) returning id""")
                .param("createdAt", Timestamp.from(creationInstant))
                .param("updatedAt", Timestamp.from(creationInstant))
                .param("studentNumber", student.getStudentNumber())
                .param("userId", student.getUser().getId())
                .update(keyHolder);

        student.setId(keyHolder.getKeyAs(Long.class));
        return student;
    }

    public void deleteAll() {
        jdbcClient.sql("truncate table student cascade").update();
    }

    public Optional<Student> findById(Long id) {
        return jdbcClient.sql("""
                    select student.id as student_id, student_number, public.user.id as user_id, username, first_name, \
                        last_name, email, password, role \
                    from student \
                      inner join public.user on student.user_id = public.user.id \
                    where student.id = :id""")
                .param("id", id)
                .query(new StudentRowMapper())
                .optional();
    }

    public List<Student> findAll() {
        return jdbcClient.sql("""
                    select student.id as student_id, student_number, public.user.id as user_id, username, first_name, \
                        last_name, email, password, role \
                    from student \
                      inner join public.user on student.user_id = public.user.id""")
                .query(new StudentRowMapper())
                .list();
    }
}
