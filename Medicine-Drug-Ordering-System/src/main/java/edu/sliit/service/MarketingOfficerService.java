package edu.sliit.service;

import edu.sliit.dto.MarketingOfficer;

import java.util.List;

public interface MarketingOfficerService {

    List<MarketingOfficer> getMarketingOfficers();

    MarketingOfficer searchByMarketingOfficerId(Integer marketingOfficerId);

    List<MarketingOfficer> searchByFirstName(String firstName);

    List<MarketingOfficer> searchByEmail(String email);

    List<MarketingOfficer> searchByStatus(String status);

    void addMarketingOfficer(MarketingOfficer marketingOfficer);

    void updateMarketingOfficer(MarketingOfficer marketingOfficer);

    void deleteByMarketingOfficerId(Integer marketingOfficerId);
}