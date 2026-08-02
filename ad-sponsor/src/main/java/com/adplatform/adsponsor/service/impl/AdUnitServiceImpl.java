package com.adplatform.adsponsor.service.impl;

import com.adplatform.adsponsor.constant.Constants;
import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.entity.AdUnit;
import com.adplatform.adsponsor.mapper.AdPlanMapper;
import com.adplatform.adsponsor.mapper.AdUnitMapper;
import com.adplatform.adsponsor.service.IAdUnitService;
import com.adplatform.adsponsor.vo.request.AdUnitRequest;
import com.adplatform.adsponsor.vo.response.AdUnitResponse;
import com.adplatform.common.exception.AdException;
import org.springframework.stereotype.Service;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanMapper planMapper;
    private final AdUnitMapper unitMapper;

    public AdUnitServiceImpl(AdPlanMapper planMapper, AdUnitMapper unitMapper) {
        this.planMapper = planMapper;
        this.unitMapper = unitMapper;
    }

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = planMapper.findById(request.getPlanId());

        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = unitMapper.findByPlanIdAndUnitName(
                request.getPlanId(), request.getUnitName()
        );

        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget());
        unitMapper.insert(newAdUnit);

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }
}
