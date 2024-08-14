// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.Hospital.service.HospitalServiceTest

package itu.blg411.kanver.Hospital.service;

import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.hospital.model.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HospitalServiceUnitTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHospitalById() {
        Long hospitalId = 1L;
        Hospital expectedHospital = new Hospital();

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(expectedHospital));

        Hospital actualHospital = hospitalService.getHospitalById(hospitalId);

        assertEquals(expectedHospital, actualHospital);
    }

    @Test
    void testGetHospitalByIdNotFound() {
        Long hospitalId = 1L;

        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> hospitalService.getHospitalById(hospitalId));
    }

    @Test
    void testGetAllHospitals() {
        List<Hospital> expectedHospitals = Collections.singletonList(new Hospital());

        when(hospitalRepository.findAll()).thenReturn(expectedHospitals);

        List<Hospital> actualHospitals = hospitalService.getAllHospitals();

        assertEquals(expectedHospitals, actualHospitals);
    }

    @Test
    void testGetHospitalByCity() {
        String city = "Istanbul";
        List<Hospital> expectedHospitals = new ArrayList<>();

        when(hospitalRepository.getHospitalsByCity(city)).thenReturn(expectedHospitals);

        List<Hospital> resultHospitals = hospitalService.getHospitalsByCity(city);

        assertEquals(expectedHospitals, resultHospitals);
    }
}
