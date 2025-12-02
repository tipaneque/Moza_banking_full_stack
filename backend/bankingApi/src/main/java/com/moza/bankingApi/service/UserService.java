package com.moza.bankingApi.service;

import com.moza.bankingApi.dto.request.UserRequest;
import com.moza.bankingApi.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
}
