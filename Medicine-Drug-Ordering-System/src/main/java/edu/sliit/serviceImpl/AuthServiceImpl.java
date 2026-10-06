package edu.sliit.serviceImpl;

import edu.sliit.dto.LoginRequest;
import edu.sliit.dto.LoginResponse;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.entity.CustomerSupportOfficerEntity;
import edu.sliit.entity.MarketingOfficerEntity;
import edu.sliit.entity.PharmacistEntity;
import edu.sliit.entity.PharmacyManagerEntity;
import edu.sliit.repository.CustomerRepository;
import edu.sliit.repository.CustomerSupportOfficerRepository;
import edu.sliit.repository.MarketingOfficerRepository;
import edu.sliit.repository.PharmacistRepository;
import edu.sliit.repository.PharmacyManagerRepository;
import edu.sliit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import edu.sliit.dto.Customer;
import edu.sliit.entity.CustomerEntity;
import edu.sliit.dto.StaffRegisterRequest;
import edu.sliit.entity.PharmacistEntity;
import edu.sliit.entity.PharmacyManagerEntity;
import edu.sliit.entity.MarketingOfficerEntity;
import edu.sliit.entity.CustomerSupportOfficerEntity;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final PharmacistRepository pharmacistRepository;
    private final PharmacyManagerRepository pharmacyManagerRepository;
    private final MarketingOfficerRepository marketingOfficerRepository;
    private final CustomerSupportOfficerRepository customerSupportOfficerRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Customer
        var customer = customerRepository
                .findByUsername(request.getUsername());

        if (customer.isPresent()
                && customer.get().getPassword().equals(request.getPassword())) {

            CustomerEntity entity = customer.get();

            return new LoginResponse(
                    entity.getCustomerId(),
                    entity.getUsername(),
                    "CUSTOMER"
            );
        }

        // Pharmacist
        var pharmacist = pharmacistRepository
                .findByUsername(request.getUsername());

        if (pharmacist.isPresent()
                && pharmacist.get().getPassword().equals(request.getPassword())) {

            PharmacistEntity entity = pharmacist.get();

            return new LoginResponse(
                    entity.getPharmacistId(),
                    entity.getUsername(),
                    "PHARMACIST"
            );
        }

        // Pharmacy Manager
        var manager = pharmacyManagerRepository
                .findByUsername(request.getUsername());

        if (manager.isPresent()
                && manager.get().getPassword().equals(request.getPassword())) {

            PharmacyManagerEntity entity = manager.get();

            return new LoginResponse(
                    entity.getManagerId(),
                    entity.getUsername(),
                    "PHARMACY_MANAGER"
            );
        }

        // Marketing Officer
        var marketingOfficer = marketingOfficerRepository
                .findByUsername(request.getUsername());

        if (marketingOfficer.isPresent()
                && marketingOfficer.get().getPassword().equals(request.getPassword())) {

            MarketingOfficerEntity entity = marketingOfficer.get();

            return new LoginResponse(
                    entity.getMarketingOfficerId(),
                    entity.getUsername(),
                    "MARKETING_OFFICER"
            );
        }

        // Customer Support Officer
        var supportOfficer = customerSupportOfficerRepository
                .findByUsername(request.getUsername());

        if (supportOfficer.isPresent()
                && supportOfficer.get().getPassword().equals(request.getPassword())) {

            CustomerSupportOfficerEntity entity = supportOfficer.get();

            return new LoginResponse(
                    entity.getSupportOfficerId(),
                    entity.getUsername(),
                    "CUSTOMER_SUPPORT_OFFICER"
            );
        }

        throw new RuntimeException("Invalid username or password");
    }

    @Override
    public void registerCustomer(Customer customer) {

        if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        CustomerEntity entity = new CustomerEntity();

        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setEmail(customer.getEmail());
        entity.setPhoneNo(customer.getPhoneNo());
        entity.setAddress(customer.getAddress());
        entity.setUsername(customer.getUsername());
        entity.setPassword(customer.getPassword());
        entity.setStatus("ACTIVE");

        customerRepository.save(entity);
    }

    @Override
    public void registerStaff(StaffRegisterRequest request) {

        String role = request.getRole().toUpperCase();

        // Check username across all staff tables
        if (pharmacistRepository.findByUsername(request.getUsername()).isPresent()
                || pharmacyManagerRepository.findByUsername(request.getUsername()).isPresent()
                || marketingOfficerRepository.findByUsername(request.getUsername()).isPresent()
                || customerSupportOfficerRepository.findByUsername(request.getUsername()).isPresent()) {

            throw new RuntimeException("Username already exists");
        }

        switch (role) {

            case "PHARMACIST":

                PharmacistEntity pharmacist = new PharmacistEntity();

                pharmacist.setFirstName(request.getFirstName());
                pharmacist.setLastName(request.getLastName());
                pharmacist.setEmail(request.getEmail());
                pharmacist.setPhoneNo(request.getPhoneNo());
                pharmacist.setLicenseNo(request.getLicenseNo());
                pharmacist.setUsername(request.getUsername());
                pharmacist.setPassword(request.getPassword());
                pharmacist.setStatus("ACTIVE");

                pharmacistRepository.save(pharmacist);
                break;


            case "PHARMACY_MANAGER":

                PharmacyManagerEntity manager = new PharmacyManagerEntity();

                manager.setFirstName(request.getFirstName());
                manager.setLastName(request.getLastName());
                manager.setEmail(request.getEmail());
                manager.setPhoneNo(request.getPhoneNo());
                manager.setUsername(request.getUsername());
                manager.setPassword(request.getPassword());
                manager.setStatus("ACTIVE");

                pharmacyManagerRepository.save(manager);
                break;


            case "MARKETING_OFFICER":

                MarketingOfficerEntity marketingOfficer =
                        new MarketingOfficerEntity();

                marketingOfficer.setFirstName(request.getFirstName());
                marketingOfficer.setLastName(request.getLastName());
                marketingOfficer.setEmail(request.getEmail());
                marketingOfficer.setPhoneNo(request.getPhoneNo());
                marketingOfficer.setUsername(request.getUsername());
                marketingOfficer.setPassword(request.getPassword());
                marketingOfficer.setStatus("ACTIVE");

                marketingOfficerRepository.save(marketingOfficer);
                break;


            case "CUSTOMER_SUPPORT_OFFICER":

                CustomerSupportOfficerEntity supportOfficer =
                        new CustomerSupportOfficerEntity();

                supportOfficer.setFirstName(request.getFirstName());
                supportOfficer.setLastName(request.getLastName());
                supportOfficer.setEmail(request.getEmail());
                supportOfficer.setPhoneNo(request.getPhoneNo());
                supportOfficer.setUsername(request.getUsername());
                supportOfficer.setPassword(request.getPassword());
                supportOfficer.setStatus("ACTIVE");

                customerSupportOfficerRepository.save(supportOfficer);
                break;


            default:
                throw new RuntimeException("Invalid staff role");
        }
    }



}