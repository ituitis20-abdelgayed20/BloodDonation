// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.Location.LocationTest

package itu.blg411.kanver.Location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.location.model.Location;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
    }

    @Test
    void gettersAndSetters() {
        location.setId(1L);
        location.setCity("Istanbul");
        location.setDistrict("Kadikoy");
        location.setMahalle("Goztepe");

        assertEquals(1L, location.getId());
        assertEquals("Istanbul", location.getCity());
        assertEquals("Kadikoy", location.getDistrict());
        assertEquals("Goztepe", location.getMahalle());
    }

    @Test
    void setCityNonNull(){
        assertThrows(NullPointerException.class, () -> location.setCity(null));
    }

    @Test
    void hospitalsGetterAndSetter() {
        List<Hospital> hospitals = new ArrayList<>();

        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setName("Hospital A");
        hospitals.add(hospital1);

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setName("Hospital B");
        hospitals.add(hospital2);

        location.setHospitals(hospitals);

        assertEquals(2, location.getHospitals().size());
        assertEquals(hospital1, location.getHospitals().get(0));
        assertEquals(hospital2, location.getHospitals().get(1));
    }

    @Test
    void testJsonIgnoreAnnotation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String json = objectMapper.writeValueAsString(location);

        assertFalse(json.contains("hospitals"));
    }

    @Test
    void noArgsConstructor() {
        assertNotNull(location);
    }

    @Test
    void allArgsConstructor() {
        Long id = 1L;
        String city = "Istanbul";
        String district = "Sariyer";
        String neighborhood = "Maslak";
        List<Hospital> hospitals = new ArrayList<>();

        Location newLocation = new Location(id, city, district, neighborhood, hospitals);

        assertNotNull(newLocation);
    }

    @Test
    void allArgsConstructorCityNullThrowsException() {
        Long id = 1L;
        String district = "Kadikoy";
        String neighborhood = "Goztepe";
        List<Hospital> hospitals = new ArrayList<>();

        assertThrows(NullPointerException.class, () -> {
            Location newLocation = new Location(id, null, district, neighborhood, hospitals);
        });
    }
}
