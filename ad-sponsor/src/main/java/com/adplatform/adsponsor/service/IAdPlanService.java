package com.adplatform.adsponsor.service;

import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.vo.request.AdPlanRequest;
import com.adplatform.adsponsor.vo.response.AdPlanResponse;
import com.adplatform.common.exception.AdException;

import java.util.List;

public interface IAdPlanService {

    /**
     * 创建推广计划
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     */
    List<AdPlan> getAdPlanByIds(AdPlanRequest request) throws AdException;

    /**
     * 更新推广计划
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
