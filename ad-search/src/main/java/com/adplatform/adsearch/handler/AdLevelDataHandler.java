package com.adplatform.adsearch.handler;

import com.adplatform.adsearch.index.IndexAware;
import com.adplatform.adsearch.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdLevelDataHandler {

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
