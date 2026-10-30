package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
