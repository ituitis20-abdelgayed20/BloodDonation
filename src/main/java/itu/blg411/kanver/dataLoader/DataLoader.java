package itu.blg411.kanver.dataLoader;

import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.location.LocationService;
import itu.blg411.kanver.location.model.Location;
import itu.blg411.kanver.hospital.model.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataLoader {

    private final LocationService locationService;
    private final HospitalService hospitalService;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (locationService.getAllLocations().isEmpty()) {
                List<Location> locations = Arrays.asList(
                        createLocation("Istanbul", "Fatih", "SultanAhmet"),
                        createLocation("Istanbul", "Fatih", "Topkapi"),
                        createLocation("Ankara", "Cankaya", "Kizilay"),
                        createLocation("Ankara", "Mamak", "Etlik")
                );
                locationService.createLocations(locations);
            }

            if (hospitalService.getAllHospitals().isEmpty()) {
                List<Hospital> sampleHospitals = Arrays.asList(
                        createHospital("Fatih Hospital", "123456789", 1L),
                        createHospital("A Hastane", "987654321", 1L),
                        createHospital("Medilif", "111223344", 3L),
                        createHospital("MediHaus", "555666777", 4L),
                        createHospital("Medicane", "888999000", 4L)
                );
                hospitalService.createHospitals(sampleHospitals);
            }
        };
    }

    private Location createLocation(String city, String district, String mahalle) {
        Location location = new Location();
        location.setCity(city);
        location.setDistrict(district);
        location.setMahalle(mahalle);
        return location;
    }

    private Hospital createHospital(String name, String phone, Long locationId) {
        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setPhone(phone);
        hospital.setLocation(locationService.getLocationById(locationId));
        return hospital;
    }
}
