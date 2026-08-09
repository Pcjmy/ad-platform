package com.adplatform.adsponsor.controller;

import com.adplatform.adsponsor.service.ICreativeService;
import com.adplatform.adsponsor.vo.request.CreativeRequest;
import com.adplatform.adsponsor.vo.response.CreativeResponse;
import com.adplatform.common.exception.AdException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/creative")
public class CreativeController {

    private final ICreativeService creativeService;

    public CreativeController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create")
    public CreativeResponse creativeCreative(@RequestBody  CreativeRequest request) throws AdException {
        log.info("ad-sponsor creativeCreative request: {}", JSON.toJSONString(request));
        return creativeService.creativeCreative(request);
    }
}
