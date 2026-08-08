package com.adplatform.adsponsor.service;

import com.adplatform.adsponsor.vo.request.CreateUserRequest;
import com.adplatform.adsponsor.vo.response.CreateUserResponse;
import com.adplatform.common.exception.AdException;

public interface IUserService {

    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
