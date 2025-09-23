package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.response.BranchResponse;
import com.nabiji.ecommerce.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    /**
     * Public endpoint to get all active branches.
     * @return ResponseEntity with a list of active branches.
     */
    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches() {
        List<BranchResponse> branches = branchService.getAllActiveBranches();
        return ResponseEntity.ok(branches);
    }
}