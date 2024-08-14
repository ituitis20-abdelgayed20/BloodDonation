package itu.blg411.kanver.hospital.model;

import itu.blg411.kanver.hospital.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h WHERE h.location.city = :city")
    List<Hospital> getHospitalsByCity(@Param("city") String city);
}
