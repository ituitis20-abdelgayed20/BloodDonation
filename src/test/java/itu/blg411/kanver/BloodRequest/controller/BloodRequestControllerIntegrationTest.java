package itu.blg411.kanver.BloodRequest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.bloodRequest.model.BloodRequestRepository;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.hospital.model.HospitalRepository;
import itu.blg411.kanver.location.model.Location;
import itu.blg411.kanver.location.model.LocationRepository;
import itu.blg411.kanver.user.model.User;
import itu.blg411.kanver.user.model.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BloodRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBloodRequest() throws Exception {
        // Test data setup
        User user = createUser("John Doe", "john.doe@example.com", "34567890123");
        userRepository.save(user);

        Location location = createLocation("Istanbul", "Sariyer", "Maslak");
        locationRepository.save(location);

        Hospital hospital = createHospital("Acibadem", "5555555555", location);
        hospitalRepository.save(hospital);

        BloodRequest newBloodRequest = createTestBloodRequest("A+", "Pending", "Test Title", "Test Description");

        // Perform the request
        MvcResult result = mockMvc.perform(post("/blood_requests/users/{userId}/hospitals/{hospitalId}", user.getId(), hospital.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBloodRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.bloodType").value(newBloodRequest.getBloodType()))
                .andExpect(jsonPath("$.status").value(newBloodRequest.getStatus()))
                .andExpect(jsonPath("$.title").value(newBloodRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(newBloodRequest.getDescription()))
                .andReturn();

        // Your other assertions...
    }

    @Test
    public void testGetBloodRequestById() throws Exception {
        // Test data setup
        BloodRequest bloodRequest = createTestBloodRequest("B-", "Pending", "Test Title 2", "Test Description 2");
        bloodRequestRepository.save(bloodRequest);

        // Perform the request
        mockMvc.perform(get("/blood_requests/{requestId}", bloodRequest.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bloodRequest.getId()))
                .andExpect(jsonPath("$.bloodType").value(bloodRequest.getBloodType()))
                .andExpect(jsonPath("$.status").value(bloodRequest.getStatus()))
                .andExpect(jsonPath("$.title").value(bloodRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(bloodRequest.getDescription()));
    }

    @Test
    public void testGetAllBloodRequests() throws Exception {
        // Test data setup
        BloodRequest bloodRequest1 = createTestBloodRequest("O+", "Pending", "Test Title 3", "Test Description 3");
        BloodRequest bloodRequest2 = createTestBloodRequest("AB+", "InProgress", "Test Title 4", "Test Description 4");

        bloodRequestRepository.saveAll(List.of(bloodRequest1, bloodRequest2));

        // Perform the request
        mockMvc.perform(get("/blood_requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdateBloodRequest() throws Exception {
        // Test data setup
        BloodRequest originalBloodRequest = createTestBloodRequest("A-", "Pending", "Original Title", "Original Description");
        bloodRequestRepository.save(originalBloodRequest);

        BloodRequest updatedBloodRequest = new BloodRequest();
        updatedBloodRequest.setBloodType("B+");
        updatedBloodRequest.setStatus("Completed");
        updatedBloodRequest.setTitle("Updated Title");
        updatedBloodRequest.setDescription("Updated Description");

        // Perform the request
        mockMvc.perform(put("/blood_requests/{requestId}", originalBloodRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBloodRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bloodType").value(updatedBloodRequest.getBloodType()))
                .andExpect(jsonPath("$.status").value(updatedBloodRequest.getStatus()))
                .andExpect(jsonPath("$.title").value(updatedBloodRequest.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedBloodRequest.getDescription()));
    }

    @Test
    public void testDeleteBloodRequestById() throws Exception {
        // Test data setup
        BloodRequest bloodRequestToDelete = createTestBloodRequest("AB-", "InProgress", "To Delete Title", "To Delete Description");
        bloodRequestRepository.save(bloodRequestToDelete);

        // Perform the request
        mockMvc.perform(delete("/blood_requests/{requestId}", bloodRequestToDelete.getId()))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/blood_requests/{requestId}", bloodRequestToDelete.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBloodRequestsByUserId() throws Exception {
        // Test data setup
        User user = createUser("Jane Doe", "jane.doe@example.com", "98765432101");
        userRepository.save(user);

        BloodRequest bloodRequest1 = createTestBloodRequest("A+", "Pending", "Test Title 5", "Test Description 5");
        BloodRequest bloodRequest2 = createTestBloodRequest("O-", "InProgress", "Test Title 6", "Test Description 6");

        bloodRequest1.setUser(user);
        bloodRequest2.setUser(user);

        bloodRequestRepository.saveAll(List.of(bloodRequest1, bloodRequest2));

        // Perform the request
        mockMvc.perform(get("/blood_requests/users/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetBloodRequestsByBloodType() throws Exception {
        // Test data setup
        BloodRequest bloodRequest1 = createTestBloodRequest("A+", "Pending", "Test Title 7", "Test Description 7");
        BloodRequest bloodRequest2 = createTestBloodRequest("A+", "InProgress", "Test Title 8", "Test Description 8");
        bloodRequestRepository.save(bloodRequest1);
        bloodRequestRepository.save(bloodRequest2);

        // Perform the request
        mockMvc.perform(get("/blood_requests").param("blood_type", "A+"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetBloodRequestsByCity() throws Exception {
        // Test data setup
        Location location1 = createLocation("Istanbul", "Sariyer", "Maslak");
        Location location2 = createLocation("Ankara", "Cankaya", "Kizilay");
        locationRepository.save(location1);
        locationRepository.save(location2);

        Hospital hospital1 = createHospital("Acibadem", "5555555555", location1);
        Hospital hospital2 = createHospital("City Hospital", "6666666666", location2);
        hospitalRepository.save(hospital1);
        hospitalRepository.save(hospital2);

        BloodRequest bloodRequest1 = createTestBloodRequest("B+", "Pending", "Test Title 9", "Test Description 9");
        BloodRequest bloodRequest2 = createTestBloodRequest("AB-", "InProgress", "Test Title 10", "Test Description 10");
        bloodRequestRepository.save(bloodRequest1);
        bloodRequestRepository.save(bloodRequest2);

        bloodRequest1.setHospital(hospital1);
        bloodRequest2.setHospital(hospital2);

        bloodRequestRepository.saveAll(List.of(bloodRequest1, bloodRequest2));

        // Perform the request
        mockMvc.perform(get("/blood_requests").param("city", "Ankara"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetBloodRequestsByBloodTypeAndCity() throws Exception {
        // Test data setup
        Location location1 = createLocation("Istanbul", "Sariyer", "Maslak");
        Location location2 = createLocation("Istanbul", "Besiktas", "Levent");
        locationRepository.save(location1);
        locationRepository.save(location2);

        Hospital hospital1 = createHospital("Acibadem", "5555555555", location1);
        Hospital hospital2 = createHospital("City Hospital", "6666666666", location2);
        hospitalRepository.save(hospital1);
        hospitalRepository.save(hospital2);

        BloodRequest bloodRequest1 = createTestBloodRequest("B+", "Pending", "Test Title 11", "Test Description 11");
        BloodRequest bloodRequest2 = createTestBloodRequest("AB-", "InProgress", "Test Title 12", "Test Description 12");
        bloodRequestRepository.save(bloodRequest1);
        bloodRequestRepository.save(bloodRequest2);

        bloodRequest1.setHospital(hospital1);
        bloodRequest2.setHospital(hospital2);

        bloodRequestRepository.saveAll(List.of(bloodRequest1, bloodRequest2));

        // Perform the request
        mockMvc.perform(get("/blood_requests")
                        .param("blood_type", "AB-")
                        .param("city", "Istanbul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1));
    }

    private BloodRequest createTestBloodRequest(String bloodType, String status, String title, String description) {
        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodType(bloodType);
        bloodRequest.setStatus(status);
        bloodRequest.setTitle(title);
        bloodRequest.setDescription(description);
        bloodRequest.setDateRequested(LocalDate.now());
        return bloodRequest;
    }

    private User createUser(String fullName, String email, String tcNo) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setTcNo(tcNo);
        return user;
    }

    private Hospital createHospital(String name, String phone, Location location) {
        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setPhone(phone);
        hospital.setLocation(location);
        return hospital;
    }

    private Location createLocation(String city, String district, String mahalle) {
        Location location = new Location();
        location.setCity(city);
        location.setDistrict(district);
        location.setMahalle(mahalle);
        return location;
    }
}