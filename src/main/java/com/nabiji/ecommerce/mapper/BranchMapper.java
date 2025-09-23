package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.BranchResponse;
import com.nabiji.ecommerce.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public BranchResponse toBranchResponse(Branch branch) {
        if (branch == null) {
            return null;
        }

        BranchResponse response = new BranchResponse();
        response.setId(branch.getId());
        response.setName(branch.getName());
        response.setAddress(branch.getAddress());

        return response;
    }
}