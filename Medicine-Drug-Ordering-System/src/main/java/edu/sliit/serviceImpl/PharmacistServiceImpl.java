package edu.sliit.serviceImpl;

import edu.sliit.dto.Pharmacist;
import edu.sliit.entity.PharmacistEntity;
import edu.sliit.exception.DuplicateResourceException;
import edu.sliit.repository.PharmacistRepository;
import edu.sliit.service.PharmacistService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacistServiceImpl implements PharmacistService {

    final PharmacistRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Pharmacist> getPharmacists() {

        List<Pharmacist> pharmacists = new ArrayList<>();

        repository.findAll().forEach(pharmacistEntity -> {

            Pharmacist pharmacist =
                    mapper.map(pharmacistEntity, Pharmacist.class);

            pharmacists.add(pharmacist);
        });

        return pharmacists;
    }

    @Override
    public Pharmacist searchByPharmacistId(Integer pharmacistId) {

        PharmacistEntity entity =
                repository.findById(pharmacistId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Pharmacist not found"
                                ));

        return mapper.map(entity, Pharmacist.class);
    }

    @Override
    public List<Pharmacist> searchByFirstName(String firstName) {

        List<Pharmacist> pharmacists = new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(pharmacistEntity -> {

                    pharmacists.add(
                            mapper.map(
                                    pharmacistEntity,
                                    Pharmacist.class
                            )
                    );
                });

        return pharmacists;
    }

    @Override
    public List<Pharmacist> searchByEmail(String email) {

        List<Pharmacist> pharmacists = new ArrayList<>();

        repository.findByEmailContainingIgnoreCase(email)
                .forEach(pharmacistEntity -> {

                    pharmacists.add(
                            mapper.map(
                                    pharmacistEntity,
                                    Pharmacist.class
                            )
                    );
                });

        return pharmacists;
    }

    @Override
    public List<Pharmacist> searchByStatus(String status) {

        List<Pharmacist> pharmacists = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(pharmacistEntity -> {

                    pharmacists.add(
                            mapper.map(
                                    pharmacistEntity,
                                    Pharmacist.class
                            )
                    );
                });

        return pharmacists;
    }

    @Override
    public void addPharmacist(Pharmacist pharmacist) {

        // Username required
        if (pharmacist.getUsername() == null ||
                pharmacist.getUsername().trim().isEmpty()) {

            throw new RuntimeException(
                    "Username is required"
            );
        }

        // Username duplicate
        if (repository.existsByUsername(
                pharmacist.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        // Email required
        if (pharmacist.getEmail() == null ||
                pharmacist.getEmail().trim().isEmpty()) {

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // Email format
        if (!pharmacist.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new RuntimeException(
                    "Invalid email format"
            );
        }

        // Email duplicate
        if (repository.existsByEmail(
                pharmacist.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        // Phone required
        if (pharmacist.getPhoneNo() == null ||
                pharmacist.getPhoneNo().trim().isEmpty()) {

            throw new RuntimeException(
                    "Phone number is required"
            );
        }

        // Sri Lankan phone number
        if (!pharmacist.getPhoneNo().matches(
                "^0\\d{9}$")) {

            throw new RuntimeException(
                    "Invalid Sri Lankan phone number"
            );
        }

        // Default status
        if (pharmacist.getStatus() == null ||
                pharmacist.getStatus().trim().isEmpty()) {

            pharmacist.setStatus("ACTIVE");
        }

        // Status validation
        if (!isValidStatus(
                pharmacist.getStatus())) {

            throw new RuntimeException(
                    "Invalid pharmacist status. Allowed values: ACTIVE, INACTIVE"
            );
        }

        PharmacistEntity entity =
                mapper.map(
                        pharmacist,
                        PharmacistEntity.class
                );

        repository.save(entity);
    }

    @Override
    public void updatePharmacist(Pharmacist pharmacist) {

        if (pharmacist.getPharmacistId() == null ||
                !repository.existsById(
                        pharmacist.getPharmacistId())) {

            throw new RuntimeException(
                    "Pharmacist not found"
            );
        }

        // Username required
        if (pharmacist.getUsername() == null ||
                pharmacist.getUsername().trim().isEmpty()) {

            throw new RuntimeException(
                    "Username is required"
            );
        }

        // Check username belongs to another pharmacist
        PharmacistEntity existingUsername =
                repository.findByUsername(
                        pharmacist.getUsername()
                ).orElse(null);

        if (existingUsername != null &&
                !existingUsername.getPharmacistId()
                        .equals(pharmacist.getPharmacistId())) {

            throw new DuplicateResourceException(
                    "Username already exists"
            );
        }

        // Email required
        if (pharmacist.getEmail() == null ||
                pharmacist.getEmail().trim().isEmpty()) {

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // Email format
        if (!pharmacist.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new RuntimeException(
                    "Invalid email format"
            );
        }

        // Check email belongs to another pharmacist
        List<PharmacistEntity> sameEmail =
                repository.findByEmailContainingIgnoreCase(
                        pharmacist.getEmail()
                );

        for (PharmacistEntity entity : sameEmail) {

            if (!entity.getPharmacistId()
                    .equals(pharmacist.getPharmacistId())) {

                throw new DuplicateResourceException(
                        "Email already exists"
                );
            }
        }

        // Phone required
        if (pharmacist.getPhoneNo() == null ||
                pharmacist.getPhoneNo().trim().isEmpty()) {

            throw new RuntimeException(
                    "Phone number is required"
            );
        }

        // Sri Lankan phone number
        if (!pharmacist.getPhoneNo().matches(
                "^0\\d{9}$")) {

            throw new RuntimeException(
                    "Invalid Sri Lankan phone number"
            );
        }

        // Status validation
        if (pharmacist.getStatus() == null ||
                !isValidStatus(
                        pharmacist.getStatus())) {

            throw new RuntimeException(
                    "Invalid pharmacist status. Allowed values: ACTIVE, INACTIVE"
            );
        }

        PharmacistEntity entity =
                mapper.map(
                        pharmacist,
                        PharmacistEntity.class
                );

        repository.save(entity);
    }

    @Override
    public void deleteByPharmacistId(
            Integer pharmacistId) {

        if (!repository.existsById(pharmacistId)) {

            throw new RuntimeException(
                    "Pharmacist not found"
            );
        }

        repository.deleteById(pharmacistId);
    }

    private boolean isValidStatus(String status) {

        return status.equalsIgnoreCase("ACTIVE") ||
                status.equalsIgnoreCase("INACTIVE");
    }
}