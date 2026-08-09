package com.adplatform.adsponsor.controller;

import com.adplatform.adsponsor.service.IAdUnitService;
import com.adplatform.adsponsor.vo.request.*;
import com.adplatform.adsponsor.vo.response.*;
import com.adplatform.common.exception.AdException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/adunit")
public class AdUnitController {

    private final IAdUnitService adUnitService;

    public AdUnitController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @RequestMapping("/create")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor createUnit request: {}", JSON.toJSONString(request));
        return adUnitService.createUnit(request);
    }

    @PostMapping("/keyword/create")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor createUnitKeyword request: {}", JSON.toJSONString(request));
        return adUnitService.createUnitKeyword(request);
    }

    @PostMapping("/it/create")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor createUnitIt request: {}", JSON.toJSONString(request));
        return adUnitService.createUnitIt(request);
    }

    @PostMapping("/district/create")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor createUnitDistrict request: {}", JSON.toJSONString(request));
        return adUnitService.createUnitDistrict(request);
    }

    @PostMapping("/creativeUnit/create")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor createCreativeUnit request: {}", JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }
}
