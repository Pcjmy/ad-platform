package com.adplatform.adsponsor.mapper.unit_condition;

import com.adplatform.adsponsor.entity.unit_condition.AdUnitDistrict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AdUnitDistrictMapper extends BaseMapper<AdUnitDistrict> {

    default List<AdUnitDistrict> insertBatch(List<AdUnitDistrict> unitDistricts) {
        unitDistricts.forEach(this::insert);
        return unitDistricts;
    }
}
