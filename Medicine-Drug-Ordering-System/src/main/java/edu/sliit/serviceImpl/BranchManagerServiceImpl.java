package edu.sliit.serviceImpl;

import edu.sliit.dto.BranchManager;
import edu.sliit.entity.BranchManagerEntity;
import edu.sliit.repository.BranchManagerRepository;
import edu.sliit.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchManagerServiceImpl implements BranchManagerService {

    private final BranchManagerRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<BranchManager> getAllBranchManagers() {

        List<BranchManager> managers = new ArrayList<>();

        repository.findAll().forEach(entity -> {
            BranchManager manager =
                    mapper.map(entity, BranchManager.class);

            managers.add(manager);
        });

        return managers;
    }
}