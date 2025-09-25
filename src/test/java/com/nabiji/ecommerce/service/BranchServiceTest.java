package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.CreateBranchRequest;
import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.BranchMapper;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.service.impl.BranchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchServiceImpl branchService;

    @Test
    void createBranch_shouldSaveAndReturnBranch() {
        // Arrange
        CreateBranchRequest request = new CreateBranchRequest();
        request.setName("Test Branch");
        request.setAddress("Test Address");

        when(branchRepository.save(any(Branch.class))).thenReturn(new Branch());

        // Act
        branchService.createBranch(request);

        // Assert
        verify(branchRepository, times(1)).save(any(Branch.class));
        verify(branchMapper, times(1)).toBranchResponse(any(Branch.class));
    }

    @Test
    void deleteBranch_shouldSetInactive_whenBranchExists() {
        // Arrange
        Branch branch = new Branch();
        branch.setActive(true);
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        // Act
        branchService.deleteBranch(1L);

        // Assert
        verify(branchRepository, times(1)).save(branch);
        assert(!branch.isActive());
    }

    @Test
    void deleteBranch_shouldThrowException_whenBranchNotFound() {
        // Arrange
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            branchService.deleteBranch(1L);
        });
        verify(branchRepository, never()).save(any(Branch.class));
    }
}