package com.adplatform.adsearch.index.interest;

import com.adplatform.adsearch.index.IndexAware;
import com.adplatform.adsearch.utils.CommonUtils;
import com.alibaba.nacos.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static final Map<String, Set<Long>> itUnitMap;
    private static final Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex before add: {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for(Long unitId: value) {
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("UnitItIndex after add: {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex before delete: {}", unitItMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for(Long unitId: value) {
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.remove(key);
        }
        log.info("UnitItIndex after delete: {}", unitItMap);
    }

    public boolean match(Long unitId, List<String> itTags) {
        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> unitKeywords = unitItMap.get(unitId);
            return unitKeywords.containsAll(itTags);
        }

        return false;
    }
}
