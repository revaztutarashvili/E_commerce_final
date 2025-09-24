// 📂 src/main/java/com/nabiji/ecommerce/controller/admin/AdminInventoryController.java
package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.request.AddInventoryRequest;
import com.nabiji.ecommerce.dto.request.UpdateInventoryRequest;
import com.nabiji.ecommerce.dto.response.InventoryResponse;
import com.nabiji.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/inventory")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    public AdminInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> addInventory(@Valid @RequestBody AddInventoryRequest request) {
        InventoryResponse response = inventoryService.addInventory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Long id, @Valid @RequestBody UpdateInventoryRequest request) {
        InventoryResponse response = inventoryService.updateInventory(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeInventory(@PathVariable Long id) {
        inventoryService.removeInventory(id);
        return ResponseEntity.noContent().build();
    }
}