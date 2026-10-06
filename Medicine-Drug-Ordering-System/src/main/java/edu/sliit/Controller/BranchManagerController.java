package edu.sliit.Controller;

import edu.sliit.dto.BranchManager;
import edu.sliit.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/branch-manager")
public class BranchManagerController {

    private final BranchManagerService service;

    @GetMapping("/get-all")
    public List<BranchManager> getAllBranchManagers() {
        return service.getAllBranchManagers();
    }
}