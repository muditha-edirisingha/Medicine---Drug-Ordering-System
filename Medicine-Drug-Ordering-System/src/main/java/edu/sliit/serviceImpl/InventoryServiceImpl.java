package edu.sliit.serviceImpl;

import edu.sliit.dto.Inventory;
import edu.sliit.entity.BranchEntity;
import edu.sliit.entity.InventoryEntity;
import edu.sliit.entity.MedicineEntity;
import edu.sliit.repository.InventoryRepository;
import edu.sliit.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    final InventoryRepository repository;
    final ModelMapper mapper;
    @Override
    public List<Inventory> getInventory() {
        List<Inventory> inventories = new ArrayList<>();

        repository.findAll().forEach(inventoryEntity -> {

            Inventory inventory =
                    mapper.map(inventoryEntity, Inventory.class);

            // Get Medicine ID
            if (inventoryEntity.getMedicine() != null) {
                inventory.setMedicineId(
                        inventoryEntity.getMedicine().getMedicineId()
                );
            }

            // Get Branch ID
            if (inventoryEntity.getBranch() != null) {
                inventory.setBranchId(
                        inventoryEntity.getBranch().getBranchId()
                );
            }

            inventories.add(inventory);
        });

        return inventories;
    }

    @Override
    public void addInventory(Inventory inventory) {
        if (inventory.getLastUpdated() == null) {
            inventory.setLastUpdated(java.time.LocalDateTime.now());
        }

        InventoryEntity entity =
                mapper.map(inventory, InventoryEntity.class);

        // Set Medicine relationship
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineId(inventory.getMedicineId());
        entity.setMedicine(medicine);

        // Set Branch relationship
        BranchEntity branch = new BranchEntity();
        branch.setBranchId(inventory.getBranchId());
        entity.setBranch(branch);

        entity.setLastUpdated(inventory.getLastUpdated());

        repository.save(entity);
    }

    @Override
    public Inventory searchByInventoryId(Integer inventoryId) {
        InventoryEntity inventoryEntity =
                repository.findById(inventoryId)
                        .orElseThrow(() ->
                                new RuntimeException("Inventory not found"));

        return mapper.map(inventoryEntity, Inventory.class);
    }

    @Override
    public List<Inventory> searchByMedicineId(Integer medicineId) {
        List<Inventory> inventories = new ArrayList<>();

        repository.findByMedicine_MedicineId(medicineId)
                .forEach(inventory -> {

                    inventories.add(
                            mapper.map(inventory, Inventory.class)
                    );

                });

        return inventories;
    }

    @Override
    public List<Inventory> searchByBranchId(Integer branchId) {
        List<Inventory> inventories = new ArrayList<>();

        repository.findByBranch_BranchId(branchId)
                .forEach(inventory -> {

                    inventories.add(
                            mapper.map(inventory, Inventory.class)
                    );

                });

        return inventories;
    }

    @Override
    public void deleteByInventoryId(Integer inventoryId) {
        if (!repository.existsById(inventoryId)) {
            throw new RuntimeException("Inventory not found");
        }

        repository.deleteById(inventoryId);
    }

    @Override
    public void updateInventory(Inventory inventory) {
        if (!repository.existsById(inventory.getInventoryId())) {
            throw new RuntimeException("Inventory not found");
        }

        inventory.setLastUpdated(java.time.LocalDateTime.now());

        repository.save(
                mapper.map(inventory, InventoryEntity.class)
        );
    }

    @Override
    public List<Inventory> getLowStockInventory() {
        List<Inventory> lowStock = new ArrayList<>();

        repository.findAll().forEach(inventory -> {

            if (inventory.getStockQuantity() <= inventory.getReorderLevel()) {

                lowStock.add(
                        mapper.map(inventory, Inventory.class)
                );

            }

        });

        return lowStock;
    }
}
