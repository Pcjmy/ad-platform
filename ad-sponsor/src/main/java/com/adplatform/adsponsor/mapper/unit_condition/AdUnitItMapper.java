package com.adplatform.adsponsor.mapper.unit_condition;

import com.adplatform.adsponsor.entity.unit_condition.AdUnitIt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AdUnitItMapper extends BaseMapper<AdUnitIt> {

    default List<AdUnitIt> insertBatch(List<AdUnitIt> unitIts) {
        unitIts.forEach(this::insert);
        return unitIts;
    }
}
