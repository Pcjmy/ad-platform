package com.adplatform.adsponsor.mapper.unit_condition;

import com.adplatform.adsponsor.entity.unit_condition.CreativeUnit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CreativeUnitMapper extends BaseMapper<CreativeUnit> {
    default List<CreativeUnit> insertBatch(List<CreativeUnit> creativeUnits) {
        creativeUnits.forEach(this::insert);
        return creativeUnits;
    }
}
