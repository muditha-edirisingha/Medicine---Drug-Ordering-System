package edu.sliit.Controller;

import edu.sliit.dto.Medicine;
import edu.sliit.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/medicine")
public class MedicineController {
    final MedicineService service;

    @GetMapping("/get-all-medicine")
    public List<Medicine> getMedicine() {

        return service.getMedicine();
    }

    @GetMapping("/search-by-medicine-id/{medicineId}")
    public Medicine searchByMedicineId(
            @PathVariable Integer medicineId) {

        return service.searchByMedicineId(medicineId);
    }

    @GetMapping("/search-by-medicine-name/{medicineName}")
    public List<Medicine> searchByMedicineName(
            @PathVariable String medicineName) {

        return service.searchByMedicineName(medicineName);
    }


    @PostMapping("/add-medicine")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMedicine(
            @RequestBody Medicine medicine) {

        service.addMedicine(medicine);
    }

    @DeleteMapping("/delete-medicine/{medicineId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMedicine(
            @PathVariable Integer medicineId) {

        service.deleteByMedicineId(medicineId);
    }

    @PutMapping("/update-medicine")
    @ResponseStatus(HttpStatus.OK)
    public void updateMedicine(@RequestBody Medicine medicine) {

        service.updateMedicine(medicine);
    }

}
