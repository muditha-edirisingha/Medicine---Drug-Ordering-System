package edu.sliit.serviceImpl;

import edu.sliit.dto.Branch;
import edu.sliit.entity.BranchEntity;
import edu.sliit.repository.BranchRepository;
import edu.sliit.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    final BranchRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Branch> getBranches() {
        List<Branch> branches = new ArrayList<>();

        repository.findAll().forEach(branch -> {

            branches.add(
                    mapper.map(branch, Branch.class)
            );

        });

        return branches;
    }

    @Override
    public void addBranch(Branch branch) {
        repository.save(
                mapper.map(branch, BranchEntity.class)
        );
    }

    @Override
    public Branch searchByBranchId(Integer branchId) {
        BranchEntity branchEntity =
                repository.findById(branchId)
                        .orElseThrow(() ->
                                new RuntimeException("Branch not found"));

        return mapper.map(branchEntity, Branch.class);
    }

    @Override
    public List<Branch> searchByBranchName(String branchName) {
        List<Branch> branches = new ArrayList<>();

        repository.findByBranchNameContainingIgnoreCase(branchName)
                .forEach(branch -> {

                    branches.add(
                            mapper.map(branch, Branch.class)
                    );

                });

        return branches;
    }

    @Override
    public void deleteByBranchId(Integer branchId) {
        if (!repository.existsById(branchId)) {
            throw new RuntimeException("Branch not found");
        }

        repository.deleteById(branchId);
    }

    @Override
    public void updateBranch(Branch branch) {
        if (!repository.existsById(branch.getBranchId())) {
            throw new RuntimeException("Branch not found");
        }

        repository.save(
                mapper.map(branch, BranchEntity.class)
        );
    }
}
