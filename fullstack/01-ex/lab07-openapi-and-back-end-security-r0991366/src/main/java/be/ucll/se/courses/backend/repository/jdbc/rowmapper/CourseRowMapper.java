package be.ucll.se.courses.backend.repository.jdbc.rowmapper;

import be.ucll.se.courses.backend.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course(
                rs.getString("course_name"),
                rs.getString("description"),
                rs.getInt("phase"),
                rs.getInt("credits")
        );
        course.setId(rs.getLong("course_id"));

        return course;
    }
}
