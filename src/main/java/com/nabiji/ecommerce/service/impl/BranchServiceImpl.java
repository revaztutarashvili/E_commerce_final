// 📂 src/main/java/com/nabiji/ecommerce/service/impl/BranchServiceImpl.java
package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.response.BranchResponse;
import com.nabiji.ecommerce.mapper.BranchMapper; // Import Mapper
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.service.BranchService;
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
}