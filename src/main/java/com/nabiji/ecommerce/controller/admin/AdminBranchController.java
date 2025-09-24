package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.request.CreateBranchRequest;
import com.nabiji.ecommerce.dto.request.UpdateBranchRequest;
import com.nabiji.ecommerce.dto.response.BranchResponse;
import com.nabiji.ecommerce.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/branches")
public class AdminBranchController {

    private final BranchService branchService;

    public AdminBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody CreateBranchRequest request) {
        BranchResponse newBranch = branchService.createBranch(request);
        return new ResponseEntity<>(newBranch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponse> updateBranch(@PathVariable Long id, @Valid @RequestBody UpdateBranchRequest request) {
        BranchResponse updatedBranch = branchService.updateBranch(id, request);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}