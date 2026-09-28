package edu.sliit.serviceImpl;

import edu.sliit.dto.Branch;
import edu.sliit.entity.BranchEntity;
import edu.sliit.repository.BranchRepository;
import edu.sliit.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import edu.sliit.entity.PharmacyManagerEntity;
import edu.sliit.repository.PharmacyManagerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    final BranchRepository repository;
    final PharmacyManagerRepository pharmacyManagerRepository;
    final ModelMapper mapper;

    @Override
    public List<Branch> getBranches() {

        List<Branch> branches = new ArrayList<>();

        repository.findAll().forEach(branchEntity -> {

            Branch branch =
                    mapper.map(branchEntity, Branch.class);

            if (branchEntity.getManager() != null) {
                branch.setManagerId(
                        branchEntity.getManager().getManagerId()
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

        PharmacyManagerEntity manager =
                pharmacyManagerRepository
                        .findById(branch.getManagerId())
                        .orElseThrow(() ->
                                new RuntimeException("Pharmacy Manager not found"));

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
                    entity.getManager().getManagerId()
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

        PharmacyManagerEntity manager =
                pharmacyManagerRepository
                        .findById(branch.getManagerId())
                        .orElseThrow(() ->
                                new RuntimeException("Pharmacy Manager not found"));

        entity.setManager(manager);

        repository.save(entity);
    }
}
