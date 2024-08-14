package itu.blg411.kanver.hospital;

import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.hospital.model.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public void createHospitals(List<Hospital> newHospitals) {
        hospitalRepository.saveAll(newHospitals);
    }
    public Hospital getHospitalById(Long hospitalId) {
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalId);
        if (hospitalOptional.isPresent()) {
            return hospitalOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital not found with ID: " + hospitalId);
        }
    }
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> getHospitalsByCity(String city) {
        return hospitalRepository.getHospitalsByCity(city);
    }
}
