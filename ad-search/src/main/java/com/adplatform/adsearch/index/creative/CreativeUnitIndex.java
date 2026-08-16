package com.adplatform.adsearch.index.creative;

import com.adplatform.adsearch.index.IndexAware;
import com.adplatform.adsearch.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    private static final Map<String, CreativeUnitObject> objectMap;
    private static final Map<Long, Set<Long>> creativeUnitMap;
    private static final Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex before add: {}", objectMap);

        objectMap.put(key, value);

        Set<Long> unitIds = CommonUtils.getOrCreate(value.getAdId(), creativeUnitMap, ConcurrentSkipListSet::new);
        unitIds.add(value.getUnitId());

        Set<Long> adIds = CommonUtils.getOrCreate(value.getUnitId(), unitCreativeMap, ConcurrentSkipListSet::new);
        adIds.add(value.getAdId());

        log.info("CreativeUnitIndex after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("creative unit index can not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex before delete: {}", objectMap);

        objectMap.remove(key);

        Set<Long> unitIds = CommonUtils.getOrCreate(value.getAdId(), creativeUnitMap, ConcurrentSkipListSet::new);
        unitIds.remove(value.getUnitId());

        Set<Long> adIds = CommonUtils.getOrCreate(value.getUnitId(), unitCreativeMap, ConcurrentSkipListSet::new);
        adIds.remove(value.getAdId());

        log.info("CreativeUnitIndex after delete: {}", objectMap);
    }
}
