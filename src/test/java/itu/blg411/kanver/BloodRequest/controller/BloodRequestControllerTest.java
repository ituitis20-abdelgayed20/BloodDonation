// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.BloodRequest.controller.BloodRequestControllerTest

package itu.blg411.kanver.BloodRequest.controller;

import itu.blg411.kanver.bloodRequest.BloodRequestService;
import itu.blg411.kanver.bloodRequest.Controller.BloodRequestController;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BloodRequestControllerTest {

    @Mock
    private BloodRequestService bloodRequestService;

    @InjectMocks
    private BloodRequestController bloodRequestController;

    @Test
    void createBloodRequest() {
        Long userId = 1L;
        Long hospitalId = 2L;
        BloodRequest newBloodRequest = new BloodRequest();

        when(bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest)).thenReturn(newBloodRequest);

        ResponseEntity<BloodRequest> responseEntity = bloodRequestController.createBloodRequest(userId, hospitalId, newBloodRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(newBloodRequest, responseEntity.getBody());

        verify(bloodRequestService, times(1)).createBloodRequest(userId, hospitalId, newBloodRequest);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void getAllBloodRequests() {
        List<BloodRequest> bloodRequests = new ArrayList<>();
        when(bloodRequestService.getAllBloodRequests()).thenReturn(bloodRequests);

        ResponseEntity<List<BloodRequest>> responseEntity = bloodRequestController.getAllBloodRequests();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bloodRequests, responseEntity.getBody());

        verify(bloodRequestService, times(1)).getAllBloodRequests();
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void updateBloodRequest() {
        Long requestId = 1L;
        BloodRequest updatedBloodRequest = new BloodRequest();

        when(bloodRequestService.updateBloodRequest(requestId, updatedBloodRequest)).thenReturn(updatedBloodRequest);

        ResponseEntity<BloodRequest> responseEntity = bloodRequestController.updateBloodRequest(requestId, updatedBloodRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedBloodRequest, responseEntity.getBody());

        verify(bloodRequestService, times(1)).updateBloodRequest(requestId, updatedBloodRequest);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void deleteBloodRequestById() {
        Long requestId = 1L;

        ResponseEntity<String> responseEntity = bloodRequestController.deleteBloodRequestById(requestId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("The blood request is deleted successfully", responseEntity.getBody());

        verify(bloodRequestService, times(1)).deleteBloodRequestById(requestId);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void getBloodRequestsByUserId() {
        Long userId = 1L;
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestService.getBloodRequestsByUserId(userId)).thenReturn(bloodRequests);

        ResponseEntity<List<BloodRequest>> responseEntity = bloodRequestController.getBloodRequestsByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bloodRequests, responseEntity.getBody());

        verify(bloodRequestService, times(1)).getBloodRequestsByUserId(userId);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void getBloodRequestsByBloodType() {
        String bloodType = "A+";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestService.getBloodRequestsByBloodType(bloodType)).thenReturn(bloodRequests);

        ResponseEntity<List<BloodRequest>> responseEntity = bloodRequestController.getBloodRequestsByBloodType(bloodType);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bloodRequests, responseEntity.getBody());

        verify(bloodRequestService, times(1)).getBloodRequestsByBloodType(bloodType);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void getBloodRequestsByCity() {
        String city = "Istanbul";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestService.getBloodRequestsByCity(city)).thenReturn(bloodRequests);

        ResponseEntity<List<BloodRequest>> responseEntity = bloodRequestController.getBloodRequestsByCity(city);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bloodRequests, responseEntity.getBody());

        verify(bloodRequestService, times(1)).getBloodRequestsByCity(city);
        verifyNoMoreInteractions(bloodRequestService);
    }

    @Test
    void getBloodRequestsByBloodTypeAndCity() {
        String bloodType = "A+";
        String city = "Istanbul";
        List<BloodRequest> bloodRequests = new ArrayList<>();

        when(bloodRequestService.getBloodRequestsByBloodTypeAndCity(bloodType, city)).thenReturn(bloodRequests);

        ResponseEntity<List<BloodRequest>> responseEntity = bloodRequestController.getBloodRequestsByBloodTypeAndCity(bloodType, city);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bloodRequests, responseEntity.getBody());

        verify(bloodRequestService, times(1)).getBloodRequestsByBloodTypeAndCity(bloodType, city);
        verifyNoMoreInteractions(bloodRequestService);
    }
}