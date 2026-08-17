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

    /**
     * 导出全部表数据到指定目录
     */
    public void dumpAll(String dir) throws IOException {
        dumpAdPlanTable(dir + DConstant.AD_PLAN);
        dumpAdUnitTable(dir + DConstant.AD_UNIT);
        dumpAdCreativeTable(dir + DConstant.AD_CREATIVE);
        dumpAdCreativeUnitTable(dir + DConstant.AD_CREATIVE_UNIT);
        dumpAdUnitKeywordTable(dir + DConstant.AD_UNIT_KEYWORD);
        dumpAdUnitItTable(dir + DConstant.AD_UNIT_IT);
        dumpAdUnitDistrictTable(dir + DConstant.AD_UNIT_DISTRICT);
    }

    private void dumpAdPlanTable(String filename) throws IOException {
        List<AdPlan> plans = adPlanMapper.selectList(new LambdaQueryWrapper<AdPlan>()
                .eq(AdPlan::getPlanStatus, CommonStatus.VALID.getStatus()));
        List<AdPlanTable> tables = plans.stream()
                .map(p -> new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(),
                        p.getStartDate(), p.getEndDate()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdUnitTable(String filename) throws IOException {
        List<AdUnit> units = adUnitMapper.selectList(new LambdaQueryWrapper<AdUnit>()
                .eq(AdUnit::getUnitStatus, CommonStatus.VALID.getStatus()));
        List<AdUnitTable> tables = units.stream()
                .map(u -> new AdUnitTable(u.getId(), u.getUnitStatus(),
                        u.getPositionType(), u.getPlanId()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdCreativeTable(String filename) throws IOException {
        List<Creative> creatives = creativeMapper.selectList(null);
        List<AdCreativeTable> tables = creatives.stream()
                .map(c -> new AdCreativeTable(c.getId(), c.getName(), c.getType(),
                        c.getMaterialType(), c.getHeight(), c.getWidth(),
                        c.getAuditStatus(), c.getUrl()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdCreativeUnitTable(String filename) throws IOException {
        List<CreativeUnit> relations = creativeUnitMapper.selectList(null);
        List<AdCreativeUnitTable> tables = relations.stream()
                .map(r -> new AdCreativeUnitTable(r.getCreativeId(), r.getUnitId()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdUnitKeywordTable(String filename) throws IOException {
        List<AdUnitKeyword> keywords = adUnitKeywordMapper.selectList(null);
        List<AdUnitKeywordTable> tables = keywords.stream()
                .map(k -> new AdUnitKeywordTable(k.getUnitId(), k.getKeyword()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdUnitItTable(String filename) throws IOException {
        List<AdUnitIt> its = adUnitItMapper.selectList(null);
        List<AdUnitItTable> tables = its.stream()
                .map(i -> new AdUnitItTable(i.getUnitId(), i.getItTag()))
                .toList();
        write(filename, tables);
    }

    private void dumpAdUnitDistrictTable(String filename) throws IOException {
        List<AdUnitDistrict> districts = adUnitDistrictMapper.selectList(null);
        List<AdUnitDistrictTable> tables = districts.stream()
                .map(d -> new AdUnitDistrictTable(d.getUnitId(), d.getProvince(), d.getCity()))
                .toList();
        write(filename, tables);
    }

    private void write(String filename, List<?> rows) throws IOException {
        File file = new File(filename);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("create dir failed: " + parentDir.getAbsolutePath());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Object row : rows) {
                writer.write(JSON.toJSONString(row));
                writer.newLine();
            }
        }
        log.info("dump {} rows to {}", rows.size(), filename);
    }
}
