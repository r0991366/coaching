package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {}
