package com.adplatform.adsponsor.service.impl;

import com.adplatform.adsponsor.constant.CommonStatus;
import com.adplatform.adsponsor.constant.Constants;
import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.entity.AdUser;
import com.adplatform.adsponsor.mapper.AdUserMapper;
import com.adplatform.adsponsor.mapper.AdPlanMapper;
import com.adplatform.adsponsor.service.IAdPlanService;
import com.adplatform.adsponsor.utils.CommonUtils;
import com.adplatform.adsponsor.vo.request.AdPlanGetRequest;
import com.adplatform.adsponsor.vo.request.AdPlanRequest;
import com.adplatform.adsponsor.vo.response.AdPlanResponse;
import com.adplatform.common.exception.AdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserMapper userMapper;
    private final AdPlanMapper planMapper;

    public AdPlanServiceImpl(AdUserMapper userMapper, AdPlanMapper planMapper) {
        this.userMapper = userMapper;
        this.planMapper = planMapper;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser adUser = userMapper.findById(request.getUserId());
        if (adUser == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan oldPlan = planMapper.findByUserIdAndPlanName(
                request.getUserId(), request.getPlanName()
        );
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdPlan newAdPlan = new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate()));
        planMapper.insert(newAdPlan);

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planMapper.findAllByIdAndUserId(
                request.getIds(), request.getUserId()
        );
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planMapper.findByIdAndUserId(
                request.getId(), request.getUserId()
        );
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(
                    CommonUtils.parseStringDate(request.getStartDate())
            );
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(
                    CommonUtils.parseStringDate(request.getEndDate())
            );
        }
        plan.setUpdateTime(new Date());
        planMapper.updateById(plan);

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planMapper.findByIdAndUserId(
                request.getId(), request.getUserId()
        );
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planMapper.updateById(plan);
    }
}
