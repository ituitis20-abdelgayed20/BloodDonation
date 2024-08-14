// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.BloodRequest.service.BloodRequestServiceIntegrationTest

package itu.blg411.kanver.BloodRequest.service;

import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.bloodRequest.model.BloodRequestRepository;
import itu.blg411.kanver.bloodRequest.BloodRequestService;
import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class BloodRequestServiceIntegrationTest {

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HospitalService hospitalService;

    @BeforeEach
    void setUp() {
        bloodRequestRepository.deleteAll();
    }

    @Test
    void createBloodRequest_Success() {
        Long userId = 1L;
        Long hospitalId = 2L;
        BloodRequest newBloodRequest = new BloodRequest();
        newBloodRequest.setBloodType("O+");

        BloodRequest createdBloodRequest = bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest);

        assertThat(createdBloodRequest).isNotNull();
        assertThat(createdBloodRequest.getStatus()).isEqualTo("Pending");
        assertThat(createdBloodRequest.getDateRequested()).isNotNull();

        List<BloodRequest> savedBloodRequests = bloodRequestRepository.findAll();
        assertThat(savedBloodRequests).hasSize(1);
        assertThat(savedBloodRequests.get(0)).isEqualTo(createdBloodRequest);
    }

    @Test
    void createBloodRequest_DuplicateRequest() {
        Long userId = 1L;
        Long hospitalId = 2L;
        BloodRequest newBloodRequest = new BloodRequest();

        BloodRequest existingRequest = new BloodRequest();
        existingRequest.setUser(userService.getUserById(userId));
        existingRequest.setHospital(hospitalService.getHospitalById(hospitalId));
        existingRequest.setStatus("Pending");
        bloodRequestRepository.save(existingRequest);

        assertThrows(ResponseStatusException.class, () -> bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest));

        List<BloodRequest> savedBloodRequests = bloodRequestRepository.findAll();
        assertThat(savedBloodRequests).hasSize(1);
    }


}