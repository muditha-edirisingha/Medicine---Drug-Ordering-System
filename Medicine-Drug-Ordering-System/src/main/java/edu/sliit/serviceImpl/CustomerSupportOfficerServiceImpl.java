package edu.sliit.serviceImpl;

import edu.sliit.dto.CustomerSupportOfficer;
import edu.sliit.entity.CustomerSupportOfficerEntity;
import edu.sliit.repository.CustomerSupportOfficerRepository;
import edu.sliit.service.CustomerSupportOfficerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerSupportOfficerServiceImpl
        implements CustomerSupportOfficerService {

    final CustomerSupportOfficerRepository repository;
    final ModelMapper mapper;


    @Override
    public List<CustomerSupportOfficer> getCustomerSupportOfficers() {

        List<CustomerSupportOfficer> officers =
                new ArrayList<>();

        repository.findAll().forEach(officer -> {

            officers.add(
                    mapper.map(
                            officer,
                            CustomerSupportOfficer.class
                    )
            );

        });

        return officers;
    }


    @Override
    public CustomerSupportOfficer searchBySupportOfficerId(
            Integer supportOfficerId) {

        CustomerSupportOfficerEntity entity =
                repository.findById(supportOfficerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer support officer not found"
                                )
                        );

        return mapper.map(
                entity,
                CustomerSupportOfficer.class
        );
    }


    @Override
    public List<CustomerSupportOfficer> searchByFirstName(
            String firstName) {

        List<CustomerSupportOfficer> officers =
                new ArrayList<>();

        repository.findByFirstNameContainingIgnoreCase(firstName)
                .forEach(officer -> {

                    officers.add(
                            mapper.map(
                                    officer,
                                    CustomerSupportOfficer.class
                            )
                    );

                });

        return officers;
    }


    @Override
    public List<CustomerSupportOfficer> searchByEmail(
            String email) {

        List<CustomerSupportOfficer> officers =
                new ArrayList<>();

        repository.findByEmailContainingIgnoreCase(email)
                .forEach(officer -> {

                    officers.add(
                            mapper.map(
                                    officer,
                                    CustomerSupportOfficer.class
                            )
                    );

                });

        return officers;
    }


    @Override
    public List<CustomerSupportOfficer> searchByStatus(
            String status) {

        List<CustomerSupportOfficer> officers =
                new ArrayList<>();

        repository.findByStatusIgnoreCase(status)
                .forEach(officer -> {

                    officers.add(
                            mapper.map(
                                    officer,
                                    CustomerSupportOfficer.class
                            )
                    );

                });

        return officers;
    }


    @Override
    public void addCustomerSupportOfficer(
            CustomerSupportOfficer supportOfficer) {

        if (supportOfficer.getStatus() == null) {
            supportOfficer.setStatus("ACTIVE");
        }

        repository.save(
                mapper.map(
                        supportOfficer,
                        CustomerSupportOfficerEntity.class
                )
        );
    }


    @Override
    public void updateCustomerSupportOfficer(
            CustomerSupportOfficer supportOfficer) {

        if (!repository.existsById(
                supportOfficer.getSupportOfficerId())) {

            throw new RuntimeException(
                    "Customer support officer not found"
            );
        }

        repository.save(
                mapper.map(
                        supportOfficer,
                        CustomerSupportOfficerEntity.class
                )
        );
    }


    @Override
    public void deleteBySupportOfficerId(
            Integer supportOfficerId) {

        if (!repository.existsById(supportOfficerId)) {

            throw new RuntimeException(
                    "Customer support officer not found"
            );
        }

        repository.deleteById(supportOfficerId);
    }
}