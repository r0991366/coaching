package be.ucll.se.courses.backend.repository.jdbc.rowmapper;

import be.ucll.se.courses.backend.model.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {

    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule(
                rs.getTimestamp("start").toInstant(),
                rs.getTimestamp("end").toInstant(),
                new CourseRowMapper().mapRow(rs, rowNum),
                new LecturerRowMapper().mapRow(rs, rowNum)
        );
        schedule.setId(rs.getLong("schedule_id"));

        return schedule;
    }
}
