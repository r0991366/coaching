package be.ucll.fs.lab5.repository.springdatajpa;

import be.ucll.fs.lab5.model.Customer;
import be.ucll.fs.lab5.repository.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Primary
@Repository
public interface SpringDataJpaCustomerRepository extends CustomerRepository, JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndName(String firstName, String name);
}
