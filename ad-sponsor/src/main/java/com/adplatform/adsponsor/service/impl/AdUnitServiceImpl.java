package com.adplatform.adsponsor.service.impl;

import com.adplatform.adsponsor.constant.Constants;
import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.entity.AdUnit;
import com.adplatform.adsponsor.entity.unit_condition.AdUnitDistrict;
import com.adplatform.adsponsor.entity.unit_condition.AdUnitIt;
import com.adplatform.adsponsor.entity.unit_condition.AdUnitKeyword;
import com.adplatform.adsponsor.entity.unit_condition.CreativeUnit;
import com.adplatform.adsponsor.mapper.AdPlanMapper;
import com.adplatform.adsponsor.mapper.AdUnitMapper;
import com.adplatform.adsponsor.mapper.CreativeMapper;
import com.adplatform.adsponsor.mapper.unit_condition.AdUnitDistrictMapper;
import com.adplatform.adsponsor.mapper.unit_condition.AdUnitItMapper;
import com.adplatform.adsponsor.mapper.unit_condition.AdUnitKeywordMapper;
import com.adplatform.adsponsor.mapper.unit_condition.CreativeUnitMapper;
import com.adplatform.adsponsor.service.IAdUnitService;
import com.adplatform.adsponsor.vo.request.*;
import com.adplatform.adsponsor.vo.response.*;
import com.adplatform.common.exception.AdException;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanMapper planMapper;
    private final AdUnitMapper unitMapper;
    private final AdUnitKeywordMapper unitKeywordMapper;
    private final AdUnitItMapper unitItMapper;
    private final AdUnitDistrictMapper unitDistrictMapper;
    private final CreativeMapper creativeMapper;
    private final CreativeUnitMapper creativeUnitMapper;

    public AdUnitServiceImpl(AdPlanMapper planMapper,
                             AdUnitMapper unitMapper,
                             AdUnitKeywordMapper unitKeywordMapper,
                             AdUnitItMapper unitItMapper,
                             AdUnitDistrictMapper unitDistrictMapper,
                             CreativeMapper creativeMapper,
                             CreativeUnitMapper creativeUnitMapper) {
        this.planMapper = planMapper;
        this.unitMapper = unitMapper;
        this.unitKeywordMapper = unitKeywordMapper;
        this.unitItMapper = unitItMapper;
        this.unitDistrictMapper = unitDistrictMapper;
        this.creativeMapper = creativeMapper;
        this.creativeUnitMapper = creativeUnitMapper;
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

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .toList();

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordMapper.insertBatch(unitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .toList();
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .toList();
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItMapper.insertBatch(unitIts).stream()
                .map(AdUnitIt::getId)
                .toList();
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistrictList().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .toList();
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistrictList().forEach(i -> unitDistricts.add(
                new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
        ));
        List<Long> ids = unitDistrictMapper.insertBatch(unitDistricts).stream()
                .map(AdUnitDistrict::getId)
                .toList();
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .toList();
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .toList();
        if (!(isRelatedUnitExist(unitIds) && isRelatedCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));
        List<Long> ids = creativeUnitMapper.insertBatch(creativeUnits)
                .stream()
                .map(CreativeUnit::getId)
                .toList();

        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitMapper.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeMapper.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
