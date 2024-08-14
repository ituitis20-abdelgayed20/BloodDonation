// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.BloodRequest.BloodRequestTest

package itu.blg411.kanver.BloodRequest;

import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BloodRequestTest {

    private BloodRequest bloodRequest;

    @BeforeEach
    void setUp() {
        bloodRequest = new BloodRequest();
    }

    @Test
    void testGetterSetterMethods() {
        Long id = 1L;
        User user = new User();
        Hospital hospital = new Hospital();
        String bloodType = "A+";
        String status = "Pending";
        LocalDate dateRequested = LocalDate.now();
        String title = "Urgent Request";
        String description = "Description of the request";

        // Set values using setters
        bloodRequest.setId(id);
        bloodRequest.setUser(user);
        bloodRequest.setHospital(hospital);
        bloodRequest.setBloodType(bloodType);
        bloodRequest.setStatus(status);
        bloodRequest.setDateRequested(dateRequested);
        bloodRequest.setTitle(title);
        bloodRequest.setDescription(description);

        // Verify values using getters
        assertEquals(id, bloodRequest.getId());
        assertEquals(user, bloodRequest.getUser());
        assertEquals(hospital, bloodRequest.getHospital());
        assertEquals(bloodType, bloodRequest.getBloodType());
        assertEquals(status, bloodRequest.getStatus());
        assertEquals(dateRequested, bloodRequest.getDateRequested());
        assertEquals(title, bloodRequest.getTitle());
        assertEquals(description, bloodRequest.getDescription());
    }

    @Test
    void testSetterGetterMethods() {
        Long id = 1L;
        User user = new User();
        Hospital hospital = new Hospital();
        String bloodType = "A+";
        String status = "Pending";
        LocalDate dateRequested = LocalDate.now();
        String title = "Urgent Request";
        String description = "Description of the request";

        bloodRequest.setId(id);
        bloodRequest.setUser(user);
        bloodRequest.setHospital(hospital);
        bloodRequest.setBloodType(bloodType);
        bloodRequest.setStatus(status);
        bloodRequest.setDateRequested(dateRequested);
        bloodRequest.setTitle(title);
        bloodRequest.setDescription(description);

        assertEquals(id, bloodRequest.getId());
        assertEquals(user, bloodRequest.getUser());
        assertEquals(hospital, bloodRequest.getHospital());
        assertEquals(bloodType, bloodRequest.getBloodType());
        assertEquals(status, bloodRequest.getStatus());
        assertEquals(dateRequested, bloodRequest.getDateRequested());
        assertEquals(title, bloodRequest.getTitle());
        assertEquals(description, bloodRequest.getDescription());
    }
}
