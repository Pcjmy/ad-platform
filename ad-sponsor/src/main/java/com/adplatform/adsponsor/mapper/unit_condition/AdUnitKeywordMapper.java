package com.adplatform.adsponsor.mapper.unit_condition;

import com.adplatform.adsponsor.entity.unit_condition.AdUnitKeyword;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AdUnitKeywordMapper extends BaseMapper<AdUnitKeyword> {

  default List<AdUnitKeyword> insertBatch(List<AdUnitKeyword> unitKeywords) {
    unitKeywords.forEach(this::insert);
    return unitKeywords;
  }
}
