package com.adplatform.adsearch.mysql.listener;

import com.adplatform.adsearch.mysql.constant.Constant;
import com.adplatform.adsearch.mysql.constant.OpType;
import com.adplatform.adsearch.mysql.dto.BinlogRowData;
import com.adplatform.adsearch.mysql.dto.MySqlRowData;
import com.adplatform.adsearch.mysql.dto.TableTemplate;
import com.adplatform.adsearch.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "")
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    private IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> {
            aggregationListener.register(v, k, this);
        });
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (fieldList == null) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return ;
        }

        for (Map<String, String> afterMap: eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry: afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
