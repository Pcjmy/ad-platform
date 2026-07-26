package com.adplatform.adsponsor.mapper;

import com.adplatform.adsponsor.entity.AdUnit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AdUnitMapper extends BaseMapper<AdUnit> {

    default AdUnit findByPlanIdAndUnitName(Long planId, String unitName) {
        return selectOne(new LambdaQueryWrapper<AdUnit>()
                .eq(AdUnit::getPlanId, planId)
                .eq(AdUnit::getUnitName, unitName));
    }

    default List<AdUnit> findAllByUnitStatus(Integer unitStatus) {
        return selectList(new LambdaQueryWrapper<AdUnit>()
                .eq(AdUnit::getUnitStatus, unitStatus));
    }
}
