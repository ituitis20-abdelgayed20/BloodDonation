package itu.blg411.kanver.bloodRequest.model;

import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    boolean existsByUserIdAndHospitalIdAndStatus(Long userId, Long hospitalId, String status);
    List<BloodRequest> findByUserId(Long userId);
    List<BloodRequest> findByBloodType(String bloodType);
    @Query("SELECT br FROM BloodRequest br INNER JOIN br.hospital h WHERE h.location.city = :city")
    List<BloodRequest> getBloodRequestsByCity(@Param("city") String city);

    @Query("SELECT br FROM BloodRequest br " +
            "INNER JOIN br.hospital h " +
            "WHERE br.bloodType = :bloodType AND h.location.city = :city")
    List<BloodRequest> getBloodRequestsByBloodTypeAndCity(@Param("bloodType") String bloodType,
                                                          @Param("city") String city);
}
