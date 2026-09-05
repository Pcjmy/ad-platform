package com.adplatform.adsearch.sender;

import com.adplatform.adsearch.mysql.dto.MySqlRowData;

public interface ISender {

    void sender(MySqlRowData rowData);
}
