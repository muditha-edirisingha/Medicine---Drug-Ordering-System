package edu.sliit.serviceImpl;

import edu.sliit.dto.PharmacyManager;
import edu.sliit.entity.PharmacyManagerEntity;
import edu.sliit.exception.BadRequestException;
import edu.sliit.exception.DuplicateResourceException;
import edu.sliit.repository.PharmacyManagerRepository;
import edu.sliit.service.PharmacyManagerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyManagerServiceImpl implements PharmacyManagerService {

    final PharmacyManagerRepository repository;
    final ModelMapper mapper;

    @Override
    public List<PharmacyManager> getPharmacyManagers() {

        List<PharmacyManager> managers = new ArrayList<>();

        repository.findAll().forEach(managerEntity -> {

            PharmacyManager manager =
                    mapper.map(managerEntity, PharmacyManager.class);

            managers.add(manager);
        });

        return managers;
    }

    @Override
    public PharmacyManager searchByManagerId(Integer managerId) {

        PharmacyManagerEntity entity =
                repository.findById(managerId)
                        .orElseThrow(() ->
                                new RuntimeException("Pharmacy Manager not found"));

        return mapper.map(entity, PharmacyManager.class);
    }

    @Override
    public List<PharmacyManager> searchByFirstName(String firstName) {

        List<PharmacyManager> managers = new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(managerEntity -> {

                    managers.add(
                            mapper.map(managerEntity, PharmacyManager.class)
                    );
                });

        return managers;
    }

    @Override
    public List<PharmacyManager> searchByEmail(String email) {

        List<PharmacyManager> managers = new ArrayList<>();

        repository.findByEmailContainingIgnoreCase(email)
                .forEach(managerEntity -> {

                    managers.add(
                            mapper.map(managerEntity, PharmacyManager.class)
                    );
                });

        return managers;
    }

    @Override
    public List<PharmacyManager> searchByStatus(String status) {

        List<PharmacyManager> managers = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(managerEntity -> {

                    managers.add(
                            mapper.map(managerEntity, PharmacyManager.class)
                    );
                });

        return managers;
    }

    @Override
    public void addPharmacyManager(PharmacyManager manager) {

        // Username validation
        if (manager.getUsername() == null ||
                manager.getUsername().trim().isEmpty()) {

            throw new BadRequestException("Username is required");
        }

        // Duplicate username validation
        if (repository.existsByUsername(manager.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        // Email validation
        if (manager.getEmail() == null ||
                manager.getEmail().trim().isEmpty()) {

            throw new BadRequestException("Email is required");
        }

        if (!manager.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new BadRequestException("Invalid email format");
        }

        // Duplicate email validation
        if (repository.existsByEmail(manager.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        // Phone validation
        if (manager.getPhoneNo() == null ||
                manager.getPhoneNo().trim().isEmpty()) {

            throw new BadRequestException("Phone number is required");
        }

        if (!manager.getPhoneNo().matches("^0\\d{9}$")) {

            throw new BadRequestException(
                    "Invalid Sri Lankan phone number"
            );
        }

        // Status validation
        if (manager.getStatus() == null ||
                manager.getStatus().trim().isEmpty()) {

            manager.setStatus("ACTIVE");
        }

        if (!manager.getStatus().equalsIgnoreCase("ACTIVE") &&
                !manager.getStatus().equalsIgnoreCase("INACTIVE")) {

            throw new BadRequestException("Invalid status");
        }

        PharmacyManagerEntity entity =
                mapper.map(manager, PharmacyManagerEntity.class);

        repository.save(entity);
    }

    @Override
    public void updatePharmacyManager(PharmacyManager manager) {

        if (manager.getManagerId() == null) {

            throw new BadRequestException(
                    "Pharmacy Manager ID is required"
            );
        }

        PharmacyManagerEntity existing =
                repository.findById(manager.getManagerId())
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Pharmacy Manager not found"
                                ));

        // Username validation
        if (manager.getUsername() == null ||
                manager.getUsername().trim().isEmpty()) {

            throw new BadRequestException("Username is required");
        }

        if (!manager.getUsername()
                .equalsIgnoreCase(existing.getUsername())) {

            if (repository.existsByUsername(manager.getUsername())) {

                throw new DuplicateResourceException(
                        "Username already exists"
                );
            }
        }

        // Email validation
        if (manager.getEmail() == null ||
                manager.getEmail().trim().isEmpty()) {

            throw new BadRequestException("Email is required");
        }

        if (!manager.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new BadRequestException("Invalid email format");
        }

        if (!manager.getEmail()
                .equalsIgnoreCase(existing.getEmail())) {

            if (repository.existsByEmail(manager.getEmail())) {

                throw new DuplicateResourceException(
                        "Email already exists"
                );
            }
        }

        // Phone validation
        if (manager.getPhoneNo() == null ||
                manager.getPhoneNo().trim().isEmpty()) {

            throw new BadRequestException("Phone number is required");
        }

        if (!manager.getPhoneNo().matches("^0\\d{9}$")) {

            throw new BadRequestException(
                    "Invalid Sri Lankan phone number"
            );
        }

        // Status validation
        if (manager.getStatus() == null ||
                manager.getStatus().trim().isEmpty()) {

            manager.setStatus("ACTIVE");
        }

        if (!manager.getStatus().equalsIgnoreCase("ACTIVE") &&
                !manager.getStatus().equalsIgnoreCase("INACTIVE")) {

            throw new BadRequestException("Invalid status");
        }

        PharmacyManagerEntity entity =
                mapper.map(manager, PharmacyManagerEntity.class);

        repository.save(entity);
    }

    @Override
    public void deleteByManagerId(Integer managerId) {

        if (!repository.existsById(managerId)) {

            throw new RuntimeException(
                    "Pharmacy Manager not found"
            );
        }

        repository.deleteById(managerId);
    }
}