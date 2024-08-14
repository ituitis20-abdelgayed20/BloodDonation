package itu.blg411.kanver.bloodRequest.model;

import itu.blg411.kanver.hospital.model.Hospital;
import itu.blg411.kanver.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "blood_request")
@Getter
@Setter
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    private String bloodType;
    private String status;
    private LocalDate dateRequested;
    private String title;
    private String description;
}