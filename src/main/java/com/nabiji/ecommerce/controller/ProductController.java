package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.response.ProductDetailsResponse;
import com.nabiji.ecommerce.dto.response.ProductResponse;
import com.nabiji.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint to get all products in a specific branch.
     * @param branchId The ID of the branch.
     * @return ResponseEntity with a list of products.
     */
    @GetMapping("/branches/{branchId}/products")
    public ResponseEntity<List<ProductResponse>> getProductsInBranch(@PathVariable Long branchId) {
        List<ProductResponse> products = productService.getProductsByBranch(branchId);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint to get detailed information about a single product in a specific branch.
     * @param productId The ID of the product.
     * @param branchId The ID of the branch, passed as a request parameter.
     * @return ResponseEntity with detailed product information.
     */
    @GetMapping("/products/{productId}/details")
    public ResponseEntity<ProductDetailsResponse> getProductDetails(@PathVariable Long productId, @RequestParam Long branchId) {
        ProductDetailsResponse productDetails = productService.getProductDetails(productId, branchId);
        return ResponseEntity.ok(productDetails);
    }
}