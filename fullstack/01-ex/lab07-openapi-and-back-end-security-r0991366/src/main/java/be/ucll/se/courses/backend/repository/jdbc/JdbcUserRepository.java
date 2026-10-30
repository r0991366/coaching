package be.ucll.se.courses.backend.repository.jdbc;

import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import be.ucll.se.courses.backend.repository.jdbc.rowmapper.UserRowMapper;
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
public class JdbcUserRepository implements UserRepository {

    private final JdbcClient jdbcClient;

    public JdbcUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User save(User user) {
        Instant creationInstant = Instant.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql( """
                    insert into public.user (created_at, updated_at, username, first_name, last_name, email, password, role) \
                    values (:createdAt, :updatedAt, :username, :firstName, :lastName, :email, :password, :role) returning id""")
                .param("createdAt", Timestamp.from(creationInstant))
                .param("updatedAt", Timestamp.from(creationInstant))
                .param("username", user.getUsername())
                .param("firstName", user.getFirstName())
                .param("lastName", user.getLastName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("role", user.getRole().toString())
                .update(keyHolder);

        user.setId(keyHolder.getKeyAs(Long.class));
        return user;
    }

    @Override
    public void deleteAll() {
        jdbcClient.sql("truncate table public.user cascade").update();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcClient.sql("""
                    select id as user_id, username, first_name, last_name, email, password, role \
                    from public.user \
                    where id = :id""")
                .param("id", id)
                .query(new UserRowMapper())
                .optional();
    }

    @Override
    public List<User> findAll() {
        return jdbcClient.sql("""
                    select id as user_id, username, first_name, last_name, email, password, role \
                    from public.user\s""")
                .query(new UserRowMapper())
                .list();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jdbcClient.sql("""
                    select id as user_id, username, first_name, last_name, email, password, role \
                    from public.user \
                    where username = :username""")
                .param("username", username)
                .query(new UserRowMapper())
                .optional();
    }
}
