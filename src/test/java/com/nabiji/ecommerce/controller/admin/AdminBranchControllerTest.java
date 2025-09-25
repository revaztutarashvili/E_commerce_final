package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.request.CreateBranchRequest;
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

// ვიყენებთ მხოლოდ Mockito-ს, Spring-ის გარეშე
@ExtendWith(MockitoExtension.class)
class AdminBranchControllerPureMockitoTest {

    @Mock
    private BranchService branchService; // ვქმნით სერვისის mock-ს

    @InjectMocks
    private AdminBranchController adminBranchController; // ვქმნით კონტროლერის ინსტანციას და "ვუსვამთ" mock-ს

    @Test
    void createBranch_shouldCallServiceAndReturnCreated() {
        // Arrange
        CreateBranchRequest request = new CreateBranchRequest();
        request.setName("New Branch");

        // Act
        // პირდაპირ ვიძახებთ კონტროლერის მეთოდს, MockMvc-ის გარეშე
        ResponseEntity<?> response = adminBranchController.createBranch(request);

        // Assert
        // ვამოწმებთ, რომ სერვისის მეთოდი გამოიძახა
        verify(branchService).createBranch(request);
        // ვამოწმებთ, რომ მეთოდმა დააბრუნა სწორი HTTP სტატუსი
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}