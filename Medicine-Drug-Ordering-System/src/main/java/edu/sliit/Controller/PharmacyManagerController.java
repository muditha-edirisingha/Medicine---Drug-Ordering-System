package edu.sliit.Controller;

import edu.sliit.dto.PharmacyManager;
import edu.sliit.service.PharmacyManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy-manager")
@RequiredArgsConstructor
public class PharmacyManagerController {

    final PharmacyManagerService service;

    @GetMapping("/get-all")
    public List<PharmacyManager> getPharmacyManagers() {
        return service.getPharmacyManagers();
    }

    @GetMapping("/search-by-manager-id/{managerId}")
    public PharmacyManager searchByManagerId(
            @PathVariable Integer managerId) {

        return service.searchByManagerId(managerId);
    }

    @GetMapping("/search-by-first-name/{firstName}")
    public List<PharmacyManager> searchByFirstName(
            @PathVariable String firstName) {

        return service.searchByFirstName(firstName);
    }

    @GetMapping("/search-by-email/{email}")
    public List<PharmacyManager> searchByEmail(
            @PathVariable String email) {

        return service.searchByEmail(email);
    }

    @GetMapping("/search-by-status/{status}")
    public List<PharmacyManager> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    public String addPharmacyManager(
            @RequestBody PharmacyManager manager) {

        service.addPharmacyManager(manager);
        return "Pharmacy Manager added successfully";
    }

    @PutMapping("/update")
    public String updatePharmacyManager(
            @RequestBody PharmacyManager manager) {

        service.updatePharmacyManager(manager);
        return "Pharmacy Manager updated successfully";
    }

    @DeleteMapping("/delete/{managerId}")
    public String deletePharmacyManager(
            @PathVariable Integer managerId) {

        service.deleteByManagerId(managerId);
        return "Pharmacy Manager deleted successfully";
    }
}