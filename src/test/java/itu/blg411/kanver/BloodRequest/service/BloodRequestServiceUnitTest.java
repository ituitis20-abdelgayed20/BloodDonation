// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.BloodRequest.service.BloodRequestServiceUnitTest

package itu.blg411.kanver.BloodRequest.service;

import itu.blg411.kanver.bloodRequest.BloodRequestService;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.bloodRequest.model.BloodRequestRepository;
import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BloodRequestServiceUnitTest {

    @Mock
    private BloodRequestRepository bloodRequestRepository;

    @Mock
    private UserService userService;

    @Mock
    private HospitalService hospitalService;

    @InjectMocks
    private BloodRequestService bloodRequestService;

    @Test
    void createBloodRequest_Success() {
        Long userId = 1L;
        Long hospitalId = 2L;
        BloodRequest newBloodRequest = new BloodRequest();

        when(bloodRequestRepository.existsByUserIdAndHospitalIdAndStatus(userId, hospitalId, "Pending")).thenReturn(false);
        when(userService.getUserById(userId)).thenReturn(new itu.blg411.kanver.user.model.User());
        when(hospitalService.getHospitalById(hospitalId)).thenReturn(new itu.blg411.kanver.hospital.model.Hospital());
        when(bloodRequestRepository.save(any())).thenReturn(newBloodRequest);

        BloodRequest result = bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        assertNotNull(result.getDateRequested());

        verify(bloodRequestRepository, times(1)).existsByUserIdAndHospitalIdAndStatus(userId, hospitalId, "Pending");
        verify(userService, times(1)).getUserById(userId);
        verify(hospitalService, times(1)).getHospitalById(hospitalId);
        verify(bloodRequestRepository, times(1)).save(any());
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void createBloodRequest_DuplicateRequest() {
        Long userId = 1L;
        Long hospitalId = 2L;
        BloodRequest newBloodRequest = new BloodRequest();

        when(bloodRequestRepository.existsByUserIdAndHospitalIdAndStatus(userId, hospitalId, "Pending")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest);
        });

        verify(bloodRequestRepository, times(1)).existsByUserIdAndHospitalIdAndStatus(userId, hospitalId, "Pending");
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void getBloodRequestsByUserId() {
        Long userId = 1L;
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(userService.getUserById(userId)).thenReturn(new itu.blg411.kanver.user.model.User());
        when(bloodRequestRepository.findByUserId(userId)).thenReturn(bloodRequests);

        List<BloodRequest> result = bloodRequestService.getBloodRequestsByUserId(userId);

        assertNotNull(result);
        assertEquals(bloodRequests, result);

        verify(userService, times(1)).getUserById(userId);
        verify(bloodRequestRepository, times(1)).findByUserId(userId);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void getAllBloodRequests() {
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestRepository.findAll()).thenReturn(bloodRequests);

        List<BloodRequest> result = bloodRequestService.getAllBloodRequests();

        assertNotNull(result);
        assertEquals(bloodRequests, result);

        verify(bloodRequestRepository, times(1)).findAll();
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void updateBloodRequestAttributes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BloodRequest updatedBloodRequest = new BloodRequest();
        BloodRequest existingBloodRequest = new BloodRequest();
        Hospital newHospital = new Hospital();

        updatedBloodRequest.setBloodType("AB+");
        updatedBloodRequest.setStatus("Completed");
        updatedBloodRequest.setDateRequested(LocalDate.now().minusDays(1));
        updatedBloodRequest.setTitle("Updated Title");
        updatedBloodRequest.setDescription("Updated Description");
        updatedBloodRequest.setHospital(newHospital);

        Method updateBloodRequestAttributes = BloodRequestService.class.getDeclaredMethod("updateBloodRequestAttributes", BloodRequest.class, BloodRequest.class);
        updateBloodRequestAttributes.setAccessible(true);

        updateBloodRequestAttributes.invoke(bloodRequestService, updatedBloodRequest, existingBloodRequest);

        assertEquals("AB+", existingBloodRequest.getBloodType());
        assertEquals("Completed", existingBloodRequest.getStatus());
        assertEquals(LocalDate.now().minusDays(1), existingBloodRequest.getDateRequested());
        assertEquals("Updated Title", existingBloodRequest.getTitle());
        assertEquals("Updated Description", existingBloodRequest.getDescription());
        assertEquals(newHospital, existingBloodRequest.getHospital());
    }

    @Test
    void updateBloodRequest_Success() {
        Long requestId = 1L;
        BloodRequest updatedBloodRequest = new BloodRequest();

        when(bloodRequestRepository.findById(requestId)).thenReturn(Optional.of(new BloodRequest()));
        when(bloodRequestRepository.save(any())).thenReturn(updatedBloodRequest);

        BloodRequest result = bloodRequestService.updateBloodRequest(requestId, updatedBloodRequest);

        assertNotNull(result);

        verify(bloodRequestRepository, times(1)).findById(requestId);
        verify(bloodRequestRepository, times(1)).save(any());
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void updateBloodRequest_NotFound() {
        Long requestId = 1L;
        BloodRequest updatedBloodRequest = new BloodRequest();

        when(bloodRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            bloodRequestService.updateBloodRequest(requestId, updatedBloodRequest);
        });

        verify(bloodRequestRepository, times(1)).findById(requestId);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void deleteBloodRequestById_Success() {
        Long requestId = 1L;

        when(bloodRequestRepository.existsById(requestId)).thenReturn(true);

        assertDoesNotThrow(() -> {
            bloodRequestService.deleteBloodRequestById(requestId);
        });

        verify(bloodRequestRepository, times(1)).existsById(requestId);
        verify(bloodRequestRepository, times(1)).deleteById(requestId);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void deleteBloodRequestById_NotFound() {
        Long requestId = 1L;

        when(bloodRequestRepository.existsById(requestId)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            bloodRequestService.deleteBloodRequestById(requestId);
        });

        verify(bloodRequestRepository, times(1)).existsById(requestId);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void getBloodRequestsByBloodType() {
        String bloodType = "A+";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestRepository.findByBloodType(bloodType)).thenReturn(bloodRequests);

        List<BloodRequest> result = bloodRequestService.getBloodRequestsByBloodType(bloodType);

        assertNotNull(result);
        assertEquals(bloodRequests, result);

        verify(bloodRequestRepository, times(1)).findByBloodType(bloodType);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void getBloodRequestsByCity() {
        String city = "Istanbul";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestRepository.getBloodRequestsByCity(city)).thenReturn(bloodRequests);

        List<BloodRequest> result = bloodRequestService.getBloodRequestsByCity(city);

        assertNotNull(result);
        assertEquals(bloodRequests, result);

        verify(bloodRequestRepository, times(1)).getBloodRequestsByCity(city);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }

    @Test
    void getBloodRequestsByBloodTypeAndCity() {
        String bloodType = "B-";
        String city = "Ankara";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestRepository.getBloodRequestsByBloodTypeAndCity(bloodType, city)).thenReturn(bloodRequests);

        List<BloodRequest> result = bloodRequestService.getBloodRequestsByBloodTypeAndCity(bloodType, city);

        assertNotNull(result);
        assertEquals(bloodRequests, result);

        verify(bloodRequestRepository, times(1)).getBloodRequestsByBloodTypeAndCity(bloodType, city);
        verifyNoMoreInteractions(bloodRequestRepository, userService, hospitalService);
    }
}
