package edu.sliit.serviceImpl;

import edu.sliit.dto.Pharmacist;
import edu.sliit.entity.PharmacistEntity;
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

        PharmacistEntity entity = repository.findById(pharmacistId)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacist not found"));

        return mapper.map(entity, Pharmacist.class);
    }

    @Override
    public List<Pharmacist> searchByFirstName(String firstName) {

        List<Pharmacist> pharmacists = new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(pharmacistEntity -> {

                    pharmacists.add(
                            mapper.map(pharmacistEntity, Pharmacist.class)
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
                            mapper.map(pharmacistEntity, Pharmacist.class)
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
                            mapper.map(pharmacistEntity, Pharmacist.class)
                    );
                });

        return pharmacists;
    }

    @Override
    public void addPharmacist(Pharmacist pharmacist) {

        if (pharmacist.getStatus() == null) {
            pharmacist.setStatus("ACTIVE");
        }

        PharmacistEntity entity =
                mapper.map(pharmacist, PharmacistEntity.class);

        repository.save(entity);
    }

    @Override
    public void updatePharmacist(Pharmacist pharmacist) {

        PharmacistEntity entity =
                mapper.map(pharmacist, PharmacistEntity.class);

        repository.save(entity);
    }

    @Override
    public void deleteByPharmacistId(Integer pharmacistId) {

        if (!repository.existsById(pharmacistId)) {
            throw new RuntimeException("Pharmacist not found");
        }

        repository.deleteById(pharmacistId);
    }
}