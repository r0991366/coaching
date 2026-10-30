package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
