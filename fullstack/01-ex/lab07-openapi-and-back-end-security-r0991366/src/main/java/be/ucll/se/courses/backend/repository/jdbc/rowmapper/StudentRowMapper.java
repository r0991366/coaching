package be.ucll.se.courses.backend.repository.jdbc.rowmapper;

import be.ucll.se.courses.backend.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student(
                rs.getString("student_number"),
                new UserRowMapper().mapRow(rs, rowNum)
        );
        student.setId(rs.getLong("student_id"));

        return student;
    }
}
