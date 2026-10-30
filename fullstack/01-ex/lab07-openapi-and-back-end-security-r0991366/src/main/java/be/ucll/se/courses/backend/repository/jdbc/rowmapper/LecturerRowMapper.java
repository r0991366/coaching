package be.ucll.se.courses.backend.repository.jdbc.rowmapper;

import be.ucll.se.courses.backend.model.Lecturer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LecturerRowMapper implements RowMapper<Lecturer> {

    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lecturer lecturer = new Lecturer(
                rs.getString("expertise"),
                new UserRowMapper().mapRow(rs, rowNum)
        );
        lecturer.setId(rs.getLong("lecturer_id"));

        return lecturer;
    }
}
