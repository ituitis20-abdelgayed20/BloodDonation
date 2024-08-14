// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.Hospital.controller.HospitalControllerTest

package itu.blg411.kanver.Hospital.controller;

import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.hospital.controller.HospitalController;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.location.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HospitalControllerTest {

    @Mock
    private HospitalService hospitalService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private HospitalController hospitalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHospitals() {
        List<Hospital> expectedHospitals = Collections.singletonList(new Hospital());
        when(hospitalService.getAllHospitals()).thenReturn(expectedHospitals);

        ResponseEntity<List<Hospital>> responseEntity = hospitalController.getAllHospitals();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedHospitals, responseEntity.getBody());
    }

    @Test
    void testGetHospitalsByCity() {
        String cityName = "Istanbul";
        List<Hospital> expectedHospitals = Collections.singletonList(new Hospital());
        when(hospitalService.getHospitalsByCity(cityName)).thenReturn(expectedHospitals);

        ResponseEntity<List<Hospital>> responseEntity = hospitalController.getHospitalsByCity(cityName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedHospitals, responseEntity.getBody());
    }
}
