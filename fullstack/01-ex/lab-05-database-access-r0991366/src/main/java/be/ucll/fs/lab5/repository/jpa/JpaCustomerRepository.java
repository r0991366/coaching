package be.ucll.fs.lab5.repository.jpa;

import be.ucll.fs.lab5.model.Customer;
import be.ucll.fs.lab5.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Primary
@Repository
@Transactional // Do not remove! Will be explained in the next theory class.
public class JpaCustomerRepository implements CustomerRepository {

    private final EntityManager entityManager;

    public JpaCustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findAll() { //cant use '*'. IMPORTANT! JPQL is NOT sql !
        return entityManager.createQuery(
                "SELECT c FROM Customer c", Customer.class  //-> makes sure the query returns an OBJECT
        ).getResultList();
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) { //if customer doesnt exist -> add it, else update it
            entityManager.persist(customer);
            return customer;
        } else {
            return entityManager.merge(customer);
        }
    }

    @Override
    public void deleteById(Long id) {
        //ehm internet is saying that using these bulk queries is unsafe
        //but assignment tells me to do this sooo
        entityManager.createQuery(
                "DELETE FROM Customer c WHERE c.id = :id"
                )
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Customer> findByFirstNameAndName(String firstName, String name) {
        return entityManager.createQuery(
                """
                SELECT c FROM Customer c
                WHERE c.firstName = :firstName AND c.name = :name
                """,
                Customer.class
                )
                .setParameter("firstName", firstName)
                .setParameter("name", name)
                .getResultStream()//both these are needed so we get a Customer Object, not a typed query
                .findFirst();
    }
}
