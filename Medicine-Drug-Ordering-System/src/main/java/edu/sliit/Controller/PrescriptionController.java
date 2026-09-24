package edu.sliit.Controller;

import edu.sliit.dto.Prescription;
import edu.sliit.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/prescription")
public class PrescriptionController {

    final PrescriptionService service;

    @GetMapping("/get-all")
    public List<Prescription> getPrescriptions() {

        return service.getPrescriptions();
    }

    @GetMapping("/search-by-id/{prescriptionId}")
    public Prescription searchByPrescriptionId(
            @PathVariable Integer prescriptionId) {

        return service.searchByPrescriptionId(prescriptionId);
    }

    @GetMapping("/search-by-customer-id/{customerId}")
    public List<Prescription> searchByCustomerId(
            @PathVariable Integer customerId) {

        return service.searchByCustomerId(customerId);
    }

    @GetMapping("/search-by-status/{status}")
    public List<Prescription> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPrescription(
            @RequestBody Prescription prescription) {

        service.addPrescription(prescription);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrescription(
            @RequestBody Prescription prescription) {

        service.updatePrescription(prescription);
    }

    @DeleteMapping("/delete/{prescriptionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePrescription(
            @PathVariable Integer prescriptionId) {

        service.deleteByPrescriptionId(prescriptionId);
    }
}
