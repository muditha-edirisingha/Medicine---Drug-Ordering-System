package edu.sliit.service;

import edu.sliit.dto.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> getInventory();

    void addInventory(Inventory inventory);

    Inventory searchByInventoryId(Integer inventoryId);

    List<Inventory> searchByMedicineId(Integer medicineId);

    List<Inventory> searchByBranchId(Integer branchId);

    void deleteByInventoryId(Integer inventoryId);

    void updateInventory(Inventory inventory);

    List<Inventory> getLowStockInventory();
}
