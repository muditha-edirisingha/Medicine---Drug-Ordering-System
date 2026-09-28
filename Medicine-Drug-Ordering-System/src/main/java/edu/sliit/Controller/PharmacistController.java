package edu.sliit.Controller;

import edu.sliit.dto.Pharmacist;
import edu.sliit.service.PharmacistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/pharmacist")
public class PharmacistController {

    final PharmacistService service;

    @GetMapping("/get-all")
    public List<Pharmacist> getPharmacists() {
        return service.getPharmacists();
    }

    @GetMapping("/search-by-pharmacist-id/{pharmacistId}")
    public Pharmacist searchByPharmacistId(
            @PathVariable Integer pharmacistId) {

        return service.searchByPharmacistId(pharmacistId);
    }

    @GetMapping("/search-by-first-name/{firstName}")
    public List<Pharmacist> searchByFirstName(
            @PathVariable String firstName) {

        return service.searchByFirstName(firstName);
    }

    @GetMapping("/search-by-email/{email}")
    public List<Pharmacist> searchByEmail(
            @PathVariable String email) {

        return service.searchByEmail(email);
    }

    @GetMapping("/search-by-status/{status}")
    public List<Pharmacist> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPharmacist(
            @RequestBody Pharmacist pharmacist) {

        service.addPharmacist(pharmacist);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updatePharmacist(
            @RequestBody Pharmacist pharmacist) {

        service.updatePharmacist(pharmacist);
    }

    @DeleteMapping("/delete/{pharmacistId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePharmacist(
            @PathVariable Integer pharmacistId) {

        service.deleteByPharmacistId(pharmacistId);
    }
}