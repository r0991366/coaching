package be.ucll.fs.lab5.repository;

import be.ucll.fs.lab5.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteById(Long id);

    Optional<Customer> findByFirstNameAndName(String firstName, String name);
}
