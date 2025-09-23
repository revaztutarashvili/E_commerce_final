package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {

    /**
     * Retrieves a list of all active branches.
     * @return A list of DTOs representing active branches.
     */
    List<BranchResponse> getAllActiveBranches();
}