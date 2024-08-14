package itu.blg411.kanver.location;

import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.location.model.Location;
import itu.blg411.kanver.location.model.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public void createLocations(List<Location> newLocations) {
        locationRepository.saveAll(newLocations);
    }
    public Location getLocationById(Long locationId) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        if (locationOptional.isPresent()) {
            return locationOptional.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + locationId);
        }
    }
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

}


