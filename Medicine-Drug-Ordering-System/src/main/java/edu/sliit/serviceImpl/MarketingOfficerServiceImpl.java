package edu.sliit.serviceImpl;

import edu.sliit.dto.MarketingOfficer;
import edu.sliit.entity.MarketingOfficerEntity;
import edu.sliit.repository.MarketingOfficerRepository;
import edu.sliit.service.MarketingOfficerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketingOfficerServiceImpl implements MarketingOfficerService {

    final MarketingOfficerRepository repository;
    final ModelMapper mapper;

    @Override
    public List<MarketingOfficer> getMarketingOfficers() {

        List<MarketingOfficer> officers = new ArrayList<>();

        repository.findAll().forEach(officerEntity -> {

            MarketingOfficer officer =
                    mapper.map(officerEntity, MarketingOfficer.class);

            officers.add(officer);
        });

        return officers;
    }

    @Override
    public MarketingOfficer searchByMarketingOfficerId(
            Integer marketingOfficerId) {

        MarketingOfficerEntity entity =
                repository.findById(marketingOfficerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Marketing Officer not found"));

        return mapper.map(entity, MarketingOfficer.class);
    }

    @Override
    public List<MarketingOfficer> searchByFirstName(String firstName) {

        List<MarketingOfficer> officers = new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(officerEntity -> {

                    officers.add(
                            mapper.map(
                                    officerEntity,
                                    MarketingOfficer.class
                            )
                    );
                });

        return officers;
    }

    @Override
    public List<MarketingOfficer> searchByEmail(String email) {

        List<MarketingOfficer> officers = new ArrayList<>();

        repository.findByEmailContainingIgnoreCase(email)
                .forEach(officerEntity -> {

                    officers.add(
                            mapper.map(
                                    officerEntity,
                                    MarketingOfficer.class
                            )
                    );
                });

        return officers;
    }

    @Override
    public List<MarketingOfficer> searchByStatus(String status) {

        List<MarketingOfficer> officers = new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(officerEntity -> {

                    officers.add(
                            mapper.map(
                                    officerEntity,
                                    MarketingOfficer.class
                            )
                    );
                });

        return officers;
    }

    @Override
    public void addMarketingOfficer(
            MarketingOfficer marketingOfficer) {

        if (marketingOfficer.getStatus() == null) {
            marketingOfficer.setStatus("ACTIVE");
        }

        MarketingOfficerEntity entity =
                mapper.map(
                        marketingOfficer,
                        MarketingOfficerEntity.class
                );

        repository.save(entity);
    }

    @Override
    public void updateMarketingOfficer(
            MarketingOfficer marketingOfficer) {

        MarketingOfficerEntity entity =
                mapper.map(
                        marketingOfficer,
                        MarketingOfficerEntity.class
                );

        repository.save(entity);
    }

    @Override
    public void deleteByMarketingOfficerId(
            Integer marketingOfficerId) {

        if (!repository.existsById(marketingOfficerId)) {
            throw new RuntimeException(
                    "Marketing Officer not found");
        }

        repository.deleteById(marketingOfficerId);
    }
}