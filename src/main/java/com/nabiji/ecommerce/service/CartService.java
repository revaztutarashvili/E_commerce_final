package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.AddToCartRequest;
import com.nabiji.ecommerce.dto.request.UpdateCartRequest;
import com.nabiji.ecommerce.dto.response.CartItemResponse;
import com.nabiji.ecommerce.dto.response.CartResponse;
import com.nabiji.ecommerce.security.UserPrincipal;

public interface CartService {

    /**
     * Adds a product to the user's shopping cart.
     * @param request DTO with product, branch, and quantity info.
     * @param currentUser The currently logged-in user.
     * @return A DTO representing the newly added cart item.
     */
    CartItemResponse addProductToCart(AddToCartRequest request, UserPrincipal currentUser);

    /**
     * Retrieves the full content of the user's shopping cart.
     * @param currentUser The currently logged-in user.
     * @return A DTO representing the entire cart.
     */
    CartResponse getCart(UserPrincipal currentUser);

    /**
     * Updates the quantity of an item in the shopping cart.
     * @param request DTO with cart item ID and new quantity.
     * @param currentUser The currently logged-in user.
     * @return A DTO representing the updated cart item.
     */
    CartItemResponse updateCartItem(UpdateCartRequest request, UserPrincipal currentUser);

    /**
     * Removes an item from the shopping cart.
     * @param itemId The ID of the cart item to remove.
     * @param currentUser The currently logged-in user.
     */
    void removeCartItem(Long itemId, UserPrincipal currentUser);
}