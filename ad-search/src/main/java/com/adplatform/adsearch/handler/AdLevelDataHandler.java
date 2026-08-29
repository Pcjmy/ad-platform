package com.adplatform.adsearch.handler;

import com.adplatform.adsearch.index.DataTable;
import com.adplatform.adsearch.index.IndexAware;
import com.adplatform.adsearch.index.adplan.AdPlanIndex;
import com.adplatform.adsearch.index.adplan.AdPlanObject;
import com.adplatform.adsearch.index.adunit.AdUnitIndex;
import com.adplatform.adsearch.index.adunit.AdUnitObject;
import com.adplatform.adsearch.index.creative.CreativeIndex;
import com.adplatform.adsearch.index.creative.CreativeObject;
import com.adplatform.adsearch.index.creative.CreativeUnitIndex;
import com.adplatform.adsearch.index.creative.CreativeUnitObject;
import com.adplatform.adsearch.index.district.UnitDistrictIndex;
import com.adplatform.adsearch.index.interest.UnitItIndex;
import com.adplatform.adsearch.index.keyword.UnitKeywordIndex;
import com.adplatform.adsearch.mysql.constant.OpType;
import com.adplatform.adsearch.utils.CommonUtils;
import com.adplatform.common.dump.table.*;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType type) {
        AdPlanObject adPlanObject = DataTable.of(
                AdPlanIndex.class
        ).get(unitTable.getPlanId());
        if (adPlanObject == null) {
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return ;
        }
        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );
        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitTable.getUnitId(),
                unitObject,
                type
        );
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return ;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(
                CreativeIndex.class
        ).get(creativeUnitTable.getAdId());
        if (unitObject == null || creativeObject == null) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeObject));
            return ;
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return ;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
            return ;
        }
        String key = CommonUtils.stringConcat(
                unitDistrictTable.getProvince(),
                unitDistrictTable.getCity()
        );
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return ;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitItTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitItTable index error: {}", unitItTable.getUnitId());
            return ;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitItTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitItIndex.class),
                unitItTable.getItTag(),
                value,
                type
        );
    }

    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return ;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(keywordTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitKeywordTable index error: {}", keywordTable.getUnitId());
            return ;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(keywordTable.getUnitId())
        );
        handleBinlogEvent(
                DataTable.of(UnitKeywordIndex.class),
                keywordTable.getKeyword(),
                value,
                type
        );
    }

    private AdLevelDataHandler() {
    }

    private static <K, V> void handleBinlogEvent(
            IndexAware<K, V> index,
            K key,
            V value,
            OpType type) {
        switch (type) {
            case ADD -> index.add(key, value);
            case UPDATE -> index.update(key, value);
            case DELETE -> index.delete(key, value);
            default -> log.error("handleBinlogEvent unsupported op type: {}", type);
        }
    }
}
