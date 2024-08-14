package itu.blg411.kanver.bloodRequest;

import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.bloodRequest.model.BloodRequestRepository;
import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final UserService userService;
    private final HospitalService hospitalService;

    public BloodRequest createBloodRequest(Long userId, Long hospitalId, BloodRequest newBloodRequest) {
        if (bloodRequestRepository.existsByUserIdAndHospitalIdAndStatus(userId, hospitalId, "Pending")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicated blood request found for same user, hospital, and status = Pending");
        }
        newBloodRequest.setUser(userService.getUserById(userId));
        newBloodRequest.setHospital(hospitalService.getHospitalById(hospitalId));
        newBloodRequest.setStatus("Pending");
        newBloodRequest.setDateRequested(LocalDate.now());
        return bloodRequestRepository.save(newBloodRequest);
    }
    public BloodRequest getBloodRequestById(Long requestId) {
        Optional<BloodRequest> bloodRequestOptional = bloodRequestRepository.findById(requestId);
        if (bloodRequestOptional.isPresent()) {
            return bloodRequestOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blood Request not found with ID: " + requestId);
        }
    }
    public List<BloodRequest> getBloodRequestsByUserId(Long userId) {
        userService.getUserById(userId);
        return bloodRequestRepository.findByUserId(userId);
    }
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }
    public BloodRequest updateBloodRequest(Long requestId, BloodRequest updatedBloodRequest) {
        Optional<BloodRequest> existingBloodRequestOptional = bloodRequestRepository.findById(requestId);

        if (existingBloodRequestOptional.isPresent()) {
            BloodRequest existingBloodRequest = existingBloodRequestOptional.get();
            updateBloodRequestAttributes(updatedBloodRequest, existingBloodRequest);
            return bloodRequestRepository.save(existingBloodRequest);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blood request not found with ID: " + requestId);
        }
    }
    private void updateBloodRequestAttributes(BloodRequest updatedBloodRequest, BloodRequest existingBloodRequest) {
        existingBloodRequest.setBloodType(updatedBloodRequest.getBloodType() != null ? updatedBloodRequest.getBloodType() : existingBloodRequest.getBloodType());
        existingBloodRequest.setStatus(updatedBloodRequest.getStatus() != null ? updatedBloodRequest.getStatus() : existingBloodRequest.getStatus());
        existingBloodRequest.setDateRequested(updatedBloodRequest.getDateRequested() != null ? updatedBloodRequest.getDateRequested() : existingBloodRequest.getDateRequested());
        existingBloodRequest.setTitle(updatedBloodRequest.getTitle() != null ? updatedBloodRequest.getTitle() : existingBloodRequest.getTitle());
        existingBloodRequest.setDescription(updatedBloodRequest.getDescription() != null ? updatedBloodRequest.getDescription() : existingBloodRequest.getDescription());

        existingBloodRequest.setHospital(updatedBloodRequest.getHospital() != null ? updatedBloodRequest.getHospital() : existingBloodRequest.getHospital());
    }

    public void deleteBloodRequestById(Long requestId) {
        if (bloodRequestRepository.existsById(requestId)) {
            bloodRequestRepository.deleteById(requestId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blood Request not found with ID: " + requestId);
        }
    }

    public List<BloodRequest> getBloodRequestsByBloodType(String bloodType) {
        return bloodRequestRepository.findByBloodType(bloodType);
    }

    public List<BloodRequest> getBloodRequestsByCity(String city) {
        return bloodRequestRepository.getBloodRequestsByCity(city);
    }

    public List<BloodRequest> getBloodRequestsByBloodTypeAndCity(String bloodType, String city) {
        return bloodRequestRepository.getBloodRequestsByBloodTypeAndCity(bloodType, city);
    }

}
