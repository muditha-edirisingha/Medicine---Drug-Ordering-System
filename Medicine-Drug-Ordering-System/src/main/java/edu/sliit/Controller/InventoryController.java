package edu.sliit.Controller;

import edu.sliit.dto.Inventory;
import edu.sliit.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    final InventoryService service;

    @GetMapping("/get-all-inventory")
    public List<Inventory> getInventory() {

        return service.getInventory();
    }

    @GetMapping("/search-by-inventory-id/{inventoryId}")
    public Inventory searchByInventoryId(
            @PathVariable Integer inventoryId) {

        return service.searchByInventoryId(inventoryId);
    }

    @GetMapping("/search-by-medicine-id/{medicineId}")
    public List<Inventory> searchByMedicineId(
            @PathVariable Integer medicineId) {

        return service.searchByMedicineId(medicineId);
    }

    @GetMapping("/search-by-branch-id/{branchId}")
    public List<Inventory> searchByBranchId(
            @PathVariable Integer branchId) {

        return service.searchByBranchId(branchId);
    }

    @GetMapping("/get-low-stock")
    public List<Inventory> getLowStockInventory() {

        return service.getLowStockInventory();
    }

    @PostMapping("/add-inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInventory(
            @RequestBody Inventory inventory) {

        service.addInventory(inventory);
    }

    @DeleteMapping("/delete-inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteInventory(
            @PathVariable Integer inventoryId) {

        service.deleteByInventoryId(inventoryId);
    }

    @PutMapping("/update-inventory")
    @ResponseStatus(HttpStatus.OK)
    public void updateInventory(
            @RequestBody Inventory inventory) {

        service.updateInventory(inventory);
    }
}
