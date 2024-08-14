// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.Hospital.HospitalTest

package itu.blg411.kanver.Hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.location.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HospitalTest {

    private Hospital hospital;

    @BeforeEach
    void setUp() {
        hospital = new Hospital();
    }

    @Test
    void gettersAndSetters() {
        hospital.setId(1L);
        Location location = new Location();
        location.setId(1L);
        hospital.setLocation(location);
        hospital.setName("Hospital A");
        hospital.setPhone("1234567890");

        assertEquals(1L, hospital.getId());
        assertEquals(location, hospital.getLocation());
        assertEquals("Hospital A", hospital.getName());
        assertEquals("1234567890", hospital.getPhone());
    }

    @Test
    void bloodRequestsGetterAndSetter() {
        List<BloodRequest> bloodRequests = new ArrayList<>();

        BloodRequest request1 = new BloodRequest();
        request1.setId(1L);
        bloodRequests.add(request1);

        BloodRequest request2 = new BloodRequest();
        request2.setId(2L);
        bloodRequests.add(request2);

        hospital.setBloodRequests(bloodRequests);

        assertEquals(2, hospital.getBloodRequests().size());
        assertEquals(request1, hospital.getBloodRequests().get(0));
        assertEquals(request2, hospital.getBloodRequests().get(1));
    }

    @Test
    void testJsonIgnoreAnnotation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String json = objectMapper.writeValueAsString(hospital);

        assertFalse(json.contains("bloodRequests"));
    }
}
