package com.adplatform.adsearch.mysql.listener;

import com.adplatform.adsearch.mysql.TemplateHolder;
import com.adplatform.adsearch.mysql.dto.BinlogRowData;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;
    private Map<String, Ilistener> listenerMap = new HashMap<>();
    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    private void register(String dbName, String tableName, Ilistener listener) {
        log.info("registering listener for {} {}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), listener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_DELETE_ROWS && type != EventType.EXT_WRITE_ROWS && type != EventType.EXT_UPDATE_ROWS) {
            return;
        }

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("dbName or tableName is empty");
            return;
        }

        String key = genKey(this.dbName, this.tableName);
        Ilistener listener = this.listenerMap.get(key);

        if (listener == null) {
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event: {}", type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return ;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private BinlogRowData buildRowData(EventData eventData) {
        return null;
    }
}
