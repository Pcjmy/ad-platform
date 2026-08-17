package com.adplatform.adsponsor.dump;

import com.adplatform.adsponsor.constant.CommonStatus;
import com.adplatform.adsponsor.entity.AdPlan;
import com.adplatform.adsponsor.entity.AdUnit;
import com.adplatform.adsponsor.entity.Creative;
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
import com.adplatform.common.dump.DConstant;
import com.adplatform.common.dump.table.AdCreativeTable;
import com.adplatform.common.dump.table.AdCreativeUnitTable;
import com.adplatform.common.dump.table.AdPlanTable;
import com.adplatform.common.dump.table.AdUnitDistrictTable;
import com.adplatform.common.dump.table.AdUnitItTable;
import com.adplatform.common.dump.table.AdUnitKeywordTable;
import com.adplatform.common.dump.table.AdUnitTable;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class DumpDataService {

    @Autowired
    private AdPlanMapper adPlanMapper;

    @Autowired
    private AdUnitMapper adUnitMapper;

    @Autowired
    private CreativeMapper creativeMapper;

    @Autowired
    private CreativeUnitMapper creativeUnitMapper;

    @Autowired
    private AdUnitKeywordMapper adUnitKeywordMapper;

    @Autowired
    private AdUnitItMapper adUnitItMapper;

    @Autowired
    private AdUnitDistrictMapper adUnitDistrictMapper;
}
