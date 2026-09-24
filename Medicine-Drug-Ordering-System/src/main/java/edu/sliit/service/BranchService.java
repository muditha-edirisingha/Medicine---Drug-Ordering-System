package edu.sliit.service;

import edu.sliit.dto.Branch;

import java.util.List;

public interface BranchService {

    List<Branch> getBranches();

    void addBranch(Branch branch);

    Branch searchByBranchId(Integer branchId);

    List<Branch> searchByBranchName(String branchName);

    void deleteByBranchId(Integer branchId);

    void updateBranch(Branch branch);
}
