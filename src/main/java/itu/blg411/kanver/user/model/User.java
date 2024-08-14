package itu.blg411.kanver.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Table(name = "kanver_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String bloodType;
    private String tcNo;
    private Integer age;
    private LocalDate lastDonationDate;
    private Integer donationCount;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BloodRequest> bloodRequests = new ArrayList<>();
}