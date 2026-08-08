package com.adplatform.adsponsor.controller;

import com.adplatform.adsponsor.service.IUserService;
import com.adplatform.adsponsor.vo.request.CreateUserRequest;
import com.adplatform.adsponsor.vo.response.CreateUserResponse;
import com.adplatform.common.exception.AdException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor createUser request: {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
