package edu.sliit.serviceImpl;

import edu.sliit.dto.PharmacyManager;
import edu.sliit.entity.PharmacyManagerEntity;
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

        if (manager.getStatus() == null) {
            manager.setStatus("ACTIVE");
        }

        PharmacyManagerEntity entity =
                mapper.map(manager, PharmacyManagerEntity.class);

        repository.save(entity);
    }

    @Override
    public void updatePharmacyManager(PharmacyManager manager) {

        PharmacyManagerEntity entity =
                mapper.map(manager, PharmacyManagerEntity.class);

        repository.save(entity);
    }

    @Override
    public void deleteByManagerId(Integer managerId) {

        if (!repository.existsById(managerId)) {
            throw new RuntimeException("Pharmacy Manager not found");
        }

        repository.deleteById(managerId);
    }
}