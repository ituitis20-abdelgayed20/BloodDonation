package itu.blg411.kanver.bloodRequest.Controller;

import itu.blg411.kanver.bloodRequest.BloodRequestService;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blood_requests")
@RequiredArgsConstructor
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    @PostMapping("/users/{userId}/hospitals/{hospitalId}")
    public ResponseEntity<BloodRequest> createBloodRequest(@PathVariable Long userId,
                                                           @PathVariable Long hospitalId,
                                                           @RequestBody BloodRequest newBloodRequest) {
        BloodRequest createdBloodRequest = bloodRequestService.createBloodRequest(userId, hospitalId, newBloodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBloodRequest);
    }
    @GetMapping("/{requestId}")
    public ResponseEntity<BloodRequest> getBloodRequestById(@PathVariable Long requestId) {
        BloodRequest bloodRequest = bloodRequestService.getBloodRequestById(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequest);
    }
    @GetMapping
    public ResponseEntity<List<BloodRequest>> getAllBloodRequests() {
        List<BloodRequest> bloodRequests = bloodRequestService.getAllBloodRequests();
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequests);
    }
    @PutMapping("/{requestId}")
    public ResponseEntity<BloodRequest> updateBloodRequest(@PathVariable Long requestId,
                                                           @RequestBody BloodRequest updatedBloodRequest) {
        BloodRequest updated =  bloodRequestService.updateBloodRequest(requestId, updatedBloodRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteBloodRequestById(@PathVariable Long requestId) {
        bloodRequestService.deleteBloodRequestById(requestId);
        return ResponseEntity.status(HttpStatus.OK).body("The blood request is deleted successfully");
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BloodRequest>> getBloodRequestsByUserId(@PathVariable Long userId) {
        List<BloodRequest> bloodRequests = bloodRequestService.getBloodRequestsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequests);
    }
    @GetMapping(params = "blood_type")
    public ResponseEntity<List<BloodRequest>> getBloodRequestsByBloodType(@RequestParam(required = false) String blood_type) {
        List<BloodRequest> bloodRequests = bloodRequestService.getBloodRequestsByBloodType(blood_type);
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequests);
    }
    @GetMapping(params = "city")
    public ResponseEntity<List<BloodRequest>> getBloodRequestsByCity(@RequestParam(required = false) String city) {
        List<BloodRequest> bloodRequests = bloodRequestService.getBloodRequestsByCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequests);
    }

    @GetMapping(params = {"blood_type", "city"})
    public ResponseEntity<List<BloodRequest>> getBloodRequestsByBloodTypeAndCity(@RequestParam(required = false) String blood_type,
                                                                                 @RequestParam(required = false) String city) {
        List<BloodRequest> bloodRequests = bloodRequestService.getBloodRequestsByBloodTypeAndCity(blood_type, city);
        return ResponseEntity.status(HttpStatus.OK).body(bloodRequests);
    }



}
