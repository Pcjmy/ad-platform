package com.adplatform.adsearch.handler;

import com.adplatform.adsearch.index.DataTable;
import com.adplatform.adsearch.index.IndexAware;
import com.adplatform.adsearch.index.adplan.AdPlanIndex;
import com.adplatform.adsearch.index.adplan.AdPlanObject;
import com.adplatform.adsearch.index.creative.CreativeIndex;
import com.adplatform.adsearch.index.creative.CreativeObject;
import com.adplatform.adsearch.mysql.constant.OpType;
import com.adplatform.common.dump.table.AdCreativeTable;
import com.adplatform.common.dump.table.AdPlanTable;
import lombok.extern.slf4j.Slf4j;

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
