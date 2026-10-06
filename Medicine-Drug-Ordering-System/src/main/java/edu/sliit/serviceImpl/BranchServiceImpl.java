package edu.sliit.serviceImpl;

import edu.sliit.dto.Branch;
import edu.sliit.entity.BranchEntity;
import edu.sliit.entity.BranchManagerEntity;
import edu.sliit.repository.BranchManagerRepository;
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
    final BranchManagerRepository branchManagerRepository;
    final ModelMapper mapper;

    @Override
    public List<Branch> getBranches() {

        List<Branch> branches = new ArrayList<>();

        repository.findAll().forEach(branchEntity -> {

            Branch branch =
                    mapper.map(branchEntity, Branch.class);

            if (branchEntity.getManager() != null) {
                branch.setManagerId(
                        branchEntity.getManager().getBranchManagerId()
                );
            }

            branches.add(branch);
        });

        return branches;
    }

    @Override
    public void addBranch(Branch branch) {

        BranchEntity entity =
                mapper.map(branch, BranchEntity.class);

        BranchManagerEntity manager =
                branchManagerRepository
                        .findById(branch.getManagerId())
                        .orElseThrow(() ->
                                new RuntimeException("Branch Manager not found"));

        entity.setManager(manager);

        repository.save(entity);
    }

    @Override
    public Branch searchByBranchId(Integer branchId) {

        BranchEntity entity =
                repository.findById(branchId)
                        .orElseThrow(() ->
                                new RuntimeException("Branch not found"));

        Branch branch =
                mapper.map(entity, Branch.class);

        if (entity.getManager() != null) {
            branch.setManagerId(
                    entity.getManager().getBranchManagerId()
            );
        }

        return branch;
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

        BranchEntity entity =
                mapper.map(branch, BranchEntity.class);

        BranchManagerEntity manager =
                branchManagerRepository
                        .findById(branch.getManagerId())
                        .orElseThrow(() ->
                                new RuntimeException("Branch Manager not found"));

        entity.setManager(manager);

        repository.save(entity);
    }
}