package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.DrivingLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, Long> {

    Optional<DrivingLicense> findById(Long id);

    DrivingLicense save(DrivingLicense drivingLicense);
}
