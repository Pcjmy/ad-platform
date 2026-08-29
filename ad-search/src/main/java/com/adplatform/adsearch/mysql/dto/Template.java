package com.adplatform.adsearch.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String databases;
    private List<JsonTable> table;
}
