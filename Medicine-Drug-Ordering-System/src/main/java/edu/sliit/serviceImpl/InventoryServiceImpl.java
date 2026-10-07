package edu.sliit.serviceImpl;

import edu.sliit.dto.Inventory;
import edu.sliit.entity.BranchEntity;
import edu.sliit.entity.InventoryEntity;
import edu.sliit.entity.MedicineEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.exception.DuplicateResourceException;
import edu.sliit.repository.BranchRepository;
import edu.sliit.repository.InventoryRepository;
import edu.sliit.repository.MedicineRepository;
import edu.sliit.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    final InventoryRepository repository;
    final MedicineRepository medicineRepository;
    final BranchRepository branchRepository;
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

        // Medicine ID validation
        if (inventory.getMedicineId() == null) {
            throw new BadRequestException(
                    "Medicine is required"
            );
        }

        // Check medicine exists
        MedicineEntity medicine = medicineRepository
                .findById(inventory.getMedicineId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Medicine not found"
                        )
                );

        // Branch ID validation
        if (inventory.getBranchId() == null) {
            throw new BadRequestException(
                    "Branch is required"
            );
        }

        // Check branch exists
        BranchEntity branch = branchRepository
                .findById(inventory.getBranchId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Branch not found"
                        )
                );

        // Stock quantity validation
        if (inventory.getStockQuantity() == null ||
                inventory.getStockQuantity() < 0) {

            throw new BadRequestException(
                    "Stock quantity cannot be negative"
            );
        }

        // Reorder level validation
        if (inventory.getReorderLevel() == null ||
                inventory.getReorderLevel() < 0) {

            throw new BadRequestException(
                    "Reorder level cannot be negative"
            );
        }

        // Duplicate Medicine + Branch validation
        if (repository.existsByMedicine_MedicineIdAndBranch_BranchId(
                inventory.getMedicineId(),
                inventory.getBranchId())) {

            throw new DuplicateResourceException(
                    "Inventory already exists for this medicine and branch"
            );
        }

        // Set last updated automatically
        if (inventory.getLastUpdated() == null) {
            inventory.setLastUpdated(LocalDateTime.now());
        }

        InventoryEntity entity =
                mapper.map(inventory, InventoryEntity.class);

        // Set Medicine relationship
        entity.setMedicine(medicine);

        // Set Branch relationship
        entity.setBranch(branch);

        entity.setLastUpdated(
                inventory.getLastUpdated()
        );

        repository.save(entity);
    }

    @Override
    public Inventory searchByInventoryId(Integer inventoryId) {

        InventoryEntity inventoryEntity =
                repository.findById(inventoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inventory not found"
                                ));

        return mapper.map(
                inventoryEntity,
                Inventory.class
        );
    }

    @Override
    public List<Inventory> searchByMedicineId(Integer medicineId) {

        List<Inventory> inventories = new ArrayList<>();

        repository.findByMedicine_MedicineId(medicineId)
                .forEach(inventory -> {

                    Inventory inventoryDto =
                            mapper.map(
                                    inventory,
                                    Inventory.class
                            );

                    if (inventory.getMedicine() != null) {
                        inventoryDto.setMedicineId(
                                inventory.getMedicine().getMedicineId()
                        );
                    }

                    if (inventory.getBranch() != null) {
                        inventoryDto.setBranchId(
                                inventory.getBranch().getBranchId()
                        );
                    }

                    inventories.add(inventoryDto);
                });

        return inventories;
    }

    @Override
    public List<Inventory> searchByBranchId(Integer branchId) {

        List<Inventory> inventories = new ArrayList<>();

        repository.findByBranch_BranchId(branchId)
                .forEach(inventory -> {

                    Inventory inventoryDto =
                            mapper.map(
                                    inventory,
                                    Inventory.class
                            );

                    if (inventory.getMedicine() != null) {
                        inventoryDto.setMedicineId(
                                inventory.getMedicine().getMedicineId()
                        );
                    }

                    if (inventory.getBranch() != null) {
                        inventoryDto.setBranchId(
                                inventory.getBranch().getBranchId()
                        );
                    }

                    inventories.add(inventoryDto);
                });

        return inventories;
    }

    @Override
    public void deleteByInventoryId(Integer inventoryId) {

        if (!repository.existsById(inventoryId)) {
            throw new RuntimeException(
                    "Inventory not found"
            );
        }

        repository.deleteById(inventoryId);
    }

    @Override
    public void updateInventory(Inventory inventory) {

        // Inventory ID validation
        if (inventory.getInventoryId() == null) {
            throw new BadRequestException(
                    "Inventory ID is required"
            );
        }

        // Check inventory exists
        InventoryEntity existingInventory =
                repository.findById(
                        inventory.getInventoryId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found"
                        ));

        // Medicine ID validation
        if (inventory.getMedicineId() == null) {
            throw new BadRequestException(
                    "Medicine is required"
            );
        }

        MedicineEntity medicine = medicineRepository
                .findById(inventory.getMedicineId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Medicine not found"
                        )
                );

        // Branch ID validation
        if (inventory.getBranchId() == null) {
            throw new BadRequestException(
                    "Branch is required"
            );
        }

        BranchEntity branch = branchRepository
                .findById(inventory.getBranchId())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Branch not found"
                        )
                );

        // Stock quantity validation
        if (inventory.getStockQuantity() == null ||
                inventory.getStockQuantity() < 0) {

            throw new BadRequestException(
                    "Stock quantity cannot be negative"
            );
        }

        // Reorder level validation
        if (inventory.getReorderLevel() == null ||
                inventory.getReorderLevel() < 0) {

            throw new BadRequestException(
                    "Reorder level cannot be negative"
            );
        }

        // Duplicate Medicine + Branch validation
        if (repository
                .existsByMedicine_MedicineIdAndBranch_BranchIdAndInventoryIdNot(
                        inventory.getMedicineId(),
                        inventory.getBranchId(),
                        inventory.getInventoryId()
                )) {

            throw new DuplicateResourceException(
                    "Inventory already exists for this medicine and branch"
            );
        }

        // Update existing entity
        existingInventory.setMedicine(medicine);
        existingInventory.setBranch(branch);
        existingInventory.setStockQuantity(
                inventory.getStockQuantity()
        );
        existingInventory.setReorderLevel(
                inventory.getReorderLevel()
        );
        existingInventory.setLastUpdated(
                LocalDateTime.now()
        );

        repository.save(existingInventory);
    }

    @Override
    public List<Inventory> getLowStockInventory() {

        List<Inventory> lowStock = new ArrayList<>();

        repository.findAll().forEach(inventory -> {

            if (inventory.getStockQuantity() <=
                    inventory.getReorderLevel()) {

                Inventory inventoryDto =
                        mapper.map(
                                inventory,
                                Inventory.class
                        );

                if (inventory.getMedicine() != null) {
                    inventoryDto.setMedicineId(
                            inventory.getMedicine().getMedicineId()
                    );
                }

                if (inventory.getBranch() != null) {
                    inventoryDto.setBranchId(
                            inventory.getBranch().getBranchId()
                    );
                }

                lowStock.add(inventoryDto);
            }

        });

        return lowStock;
    }
}