package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.service.BranchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    @Test
    void getAllBranches_shouldCallServiceAndReturnOk() {
        // Act
        ResponseEntity<?> response = branchController.getAllBranches();

        // Assert
        verify(branchService).getAllActiveBranches();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}