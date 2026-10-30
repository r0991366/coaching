package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndName(String firstName, String name);

    @Modifying
    @Query(value = "update customer set name = :name where id = :id", nativeQuery = true)
    void updateCustomerName(Long id, String name);
}
