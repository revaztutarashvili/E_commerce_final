package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.ProductDetailsResponse;
import com.nabiji.ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    /**
     * Retrieves a list of products available in a specific branch.
     * @param branchId The ID of the branch.
     * @return A list of DTOs representing products in the specified branch.
     */
    List<ProductResponse> getProductsByBranch(Long branchId);

    /**
     * Retrieves detailed information for a specific product in a given branch.
     * @param productId The ID of the product.
     * @param branchId The ID of the branch.
     * @return A DTO with detailed product information including branch-specific price and quantity.
     */
    ProductDetailsResponse getProductDetails(Long productId, Long branchId);
}