package com.adplatform.adsponsor.controller;

import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.service.IAdPlanService;
import com.adplatform.adsponsor.vo.request.AdPlanGetRequest;
import com.adplatform.adsponsor.vo.request.AdPlanRequest;
import com.adplatform.adsponsor.vo.response.AdPlanResponse;
import com.adplatform.common.exception.AdException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/adPlan")
public class AdPlanController {

    private final IAdPlanService adPlanService;

    public AdPlanController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/create")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor createAdPlan request: {}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/get")
    public List<AdPlan> getAdPlanById(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor getAdPlanById request: {}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PutMapping("/update")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor updateAdPlan request: {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    @DeleteMapping("/delete")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor deleteAdPlan request: {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
