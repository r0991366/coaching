package be.ucll.se.courses.backend.repository.jpa;

import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaUserRepository implements UserRepository {

    private final EntityManager entityManager;

    public JpaUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from User").executeUpdate();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager
                .createQuery("""
                    select u from User u
                    where u.username = :username""", User.class)
                .setParameter("username", username)
                .getSingleResult()
            );
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
