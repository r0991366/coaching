package be.ucll.se.courses.backend.repository.jdbc.rowmapper;

import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role").toUpperCase(Locale.ROOT))
        );
        user.setId(rs.getLong("user_id"));

        return user;
    }
}
