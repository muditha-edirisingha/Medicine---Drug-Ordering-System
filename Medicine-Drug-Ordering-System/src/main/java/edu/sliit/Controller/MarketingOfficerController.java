package edu.sliit.Controller;

import edu.sliit.dto.MarketingOfficer;
import edu.sliit.service.MarketingOfficerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marketing-officer")
@RequiredArgsConstructor
public class MarketingOfficerController {

    final MarketingOfficerService service;

    @GetMapping("/get-all")
    public List<MarketingOfficer> getMarketingOfficers() {
        return service.getMarketingOfficers();
    }

    @GetMapping("/search-by-marketing-officer-id/{marketingOfficerId}")
    public MarketingOfficer searchByMarketingOfficerId(
            @PathVariable Integer marketingOfficerId) {

        return service.searchByMarketingOfficerId(marketingOfficerId);
    }

    @GetMapping("/search-by-first-name/{firstName}")
    public List<MarketingOfficer> searchByFirstName(
            @PathVariable String firstName) {

        return service.searchByFirstName(firstName);
    }

    @GetMapping("/search-by-email/{email}")
    public List<MarketingOfficer> searchByEmail(
            @PathVariable String email) {

        return service.searchByEmail(email);
    }

    @GetMapping("/search-by-status/{status}")
    public List<MarketingOfficer> searchByStatus(
            @PathVariable String status) {

        return service.searchByStatus(status);
    }

    @PostMapping("/add")
    public String addMarketingOfficer(
            @RequestBody MarketingOfficer marketingOfficer) {

        service.addMarketingOfficer(marketingOfficer);

        return "Marketing Officer added successfully";
    }

    @PutMapping("/update")
    public String updateMarketingOfficer(
            @RequestBody MarketingOfficer marketingOfficer) {

        service.updateMarketingOfficer(marketingOfficer);

        return "Marketing Officer updated successfully";
    }

    @DeleteMapping("/delete/{marketingOfficerId}")
    public String deleteMarketingOfficer(
            @PathVariable Integer marketingOfficerId) {

        service.deleteByMarketingOfficerId(marketingOfficerId);

        return "Marketing Officer deleted successfully";
    }
}