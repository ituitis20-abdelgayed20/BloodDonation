package itu.blg411.kanver.hospital.controller;

import itu.blg411.kanver.hospital.HospitalService;
import itu.blg411.kanver.hospital.model.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.status(HttpStatus.OK).body(hospitals);
    }

    @GetMapping(params = "city")
    public ResponseEntity<List<Hospital>> getHospitalsByCity(@RequestParam(required = false) String city) {
        List<Hospital> hospitals = hospitalService.getHospitalsByCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(hospitals);
    }
}