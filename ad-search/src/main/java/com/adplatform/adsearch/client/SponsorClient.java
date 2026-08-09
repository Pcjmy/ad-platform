package com.adplatform.adsearch.client;

import com.adplatform.adsearch.client.vo.AdPlan;
import com.adplatform.adsearch.client.vo.AdPlanGetRequest;
import com.adplatform.common.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "ad-sponsor", fallback = SponsorClientFallback.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/ad-plan/get", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
