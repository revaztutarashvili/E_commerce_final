// 📂 src/main/java/com/nabiji/ecommerce/service/impl/BranchServiceImpl.java
package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.request.CreateBranchRequest;
import com.nabiji.ecommerce.dto.request.UpdateBranchRequest;
import com.nabiji.ecommerce.dto.response.BranchResponse;
import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.BranchMapper; // Import Mapper
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.service.BranchService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper; // Inject Mapper

    public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    @Override
    public List<BranchResponse> getAllActiveBranches() {
        return branchRepository.findAllByActiveTrue().stream()
                .map(branchMapper::toBranchResponse) // Use Mapper for conversion
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BranchResponse createBranch(CreateBranchRequest request) {
        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setAddress(request.getAddress());
        branch.setActive(true);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toBranchResponse(savedBranch);
    }

    @Override
    @Transactional
    public BranchResponse updateBranch(Long branchId, UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        if (request.getName() != null) {
            branch.setName(request.getName());
        }
        if (request.getAddress() != null) {
            branch.setAddress(request.getAddress());
        }
        if (request.getActive() != null) {
            branch.setActive(request.getActive());
        }

        Branch updatedBranch = branchRepository.save(branch);
        return branchMapper.toBranchResponse(updatedBranch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        // Soft delete
        branch.setActive(false);
        branchRepository.save(branch);
    }
}