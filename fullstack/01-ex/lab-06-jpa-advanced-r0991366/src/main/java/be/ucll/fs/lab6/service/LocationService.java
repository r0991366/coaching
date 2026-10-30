package be.ucll.fs.lab6.service;

import be.ucll.fs.lab6.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void removeFirstClassroomFromFirstLocation() {
        locationRepository.findById(1L).orElseThrow().getClassrooms().remove(0);
    }
}
