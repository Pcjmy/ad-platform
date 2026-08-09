package com.adplatform.adsearch.client;

import com.adplatform.adsearch.client.vo.AdPlan;
import com.adplatform.adsearch.client.vo.AdPlanGetRequest;
import com.adplatform.common.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientFallback implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "ad-sponsor service error");
    }
}
