package itu.blg411.kanver.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import itu.blg411.kanver.bloodRequest.model.BloodRequest;
import itu.blg411.kanver.location.model.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String name;
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BloodRequest> bloodRequests = new ArrayList<>();

}