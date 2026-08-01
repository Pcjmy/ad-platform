package com.adplatform.adsponsor.service.impl;

import com.adplatform.adsponsor.constant.Constants;
import com.adplatform.adsponsor.entity.AdUser;
import com.adplatform.adsponsor.mapper.AdUserMapper;
import com.adplatform.adsponsor.service.AdUserService;
import com.adplatform.adsponsor.vo.request.CreateUserRequest;
import com.adplatform.adsponsor.vo.response.CreateUserResponse;
import com.adplatform.common.exception.AdException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class AdUserServiceImpl implements AdUserService {

    private final AdUserMapper adUserMapper;

    public AdUserServiceImpl(AdUserMapper adUserMapper) {
        this.adUserMapper = adUserMapper;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (request == null || !StringUtils.hasText(request.getUsername())) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        String username = request.getUsername().trim();
        AdUser oldUser = adUserMapper.findByUsername(username);
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        AdUser user = new AdUser(username, token);
        adUserMapper.insert(user);

        return new CreateUserResponse(user.getId(), user.getUsername(), user.getToken());
    }
}
