package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.BalanceResponse;
import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.security.UserPrincipal;

public interface UserService {

    /**
     * Retrieves the profile of the currently authenticated user.
     * @param currentUser The principal object representing the logged-in user.
     * @return A DTO containing the user's profile information.
     */
    UserProfileResponse getUserProfile(UserPrincipal currentUser);

    /**
     * Retrieves the current balance of the authenticated user.
     * @param currentUser The principal object representing the logged-in user.
     * @return A DTO containing the user's current balance.
     */
    BalanceResponse getCurrentBalance(UserPrincipal currentUser);
}