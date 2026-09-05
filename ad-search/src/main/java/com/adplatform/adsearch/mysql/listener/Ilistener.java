package com.adplatform.adsearch.mysql.listener;

import com.adplatform.adsearch.mysql.dto.BinlogRowData;

public interface Ilistener {

    void register();

    void onEvent(BinlogRowData binlogRowData);
}
