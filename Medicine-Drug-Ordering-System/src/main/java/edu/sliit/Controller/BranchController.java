package edu.sliit.Controller;

import edu.sliit.dto.Branch;
import edu.sliit.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {
    final BranchService service;

    @GetMapping("/get-all")
    public List<Branch> getBranches() {

        return service.getBranches();
    }

    @GetMapping("/search-by-id/{branchId}")
    public Branch searchByBranchId(
            @PathVariable Integer branchId) {

        return service.searchByBranchId(branchId);
    }

    @GetMapping("/search-by-name/{branchName}")
    public List<Branch> searchByBranchName(
            @PathVariable String branchName) {

        return service.searchByBranchName(branchName);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBranch(
            @RequestBody Branch branch) {

        service.addBranch(branch);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateBranch(
            @RequestBody Branch branch) {

        service.updateBranch(branch);
    }

    @DeleteMapping("/delete/{branchId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBranch(
            @PathVariable Integer branchId) {

        service.deleteByBranchId(branchId);
    }

}
