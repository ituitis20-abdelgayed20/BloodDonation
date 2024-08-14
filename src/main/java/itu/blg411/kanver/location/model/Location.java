package itu.blg411.kanver.location.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import itu.blg411.kanver.hospital.model.Hospital;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String city;
    private String district;
    private String mahalle;

    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Hospital> hospitals = new ArrayList<>();
}
