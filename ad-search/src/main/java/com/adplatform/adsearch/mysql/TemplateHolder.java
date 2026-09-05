package com.adplatform.adsearch.mysql;

import com.adplatform.adsearch.mysql.constant.OpType;
import com.adplatform.adsearch.mysql.dto.ParseTemplate;
import com.adplatform.adsearch.mysql.dto.TableTemplate;
import com.adplatform.adsearch.mysql.dto.Template;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Component
public class TemplateHolder {
    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SCHEMA = "select table_schema, table_name," +
            "column_name, ordinal_position from information_schema.columns "  +
            "where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        Template jsonTemplate = readTemplate(path);
        this.template = ParseTemplate.parse(jsonTemplate);
        loadMeta();
    }

    private Template readTemplate(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);
        if (inStream == null) {
            throw new IllegalStateException("template file not found: " + path);
        }

        try (inStream) {
            return JSON.parseObject(
                    inStream,
                    Charset.defaultCharset(),
                    Template.class
            );
        } catch (IOException ex) {
            throw new UncheckedIOException("fail to read template file: " + path, ex);
        }
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry:
                template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> updateFields = table.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> insertFields = table.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );

            jdbcTemplate.query(SQL_SCHEMA, rs -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String columnName = rs.getString("COLUMN_NAME");

                if ((updateFields != null && updateFields.contains(columnName))
                    || (insertFields != null && insertFields.contains(columnName))
                    || (deleteFields != null && deleteFields.contains(columnName))) {
                    table.getPosMap().put(pos - 1, columnName);
                }
            }, template.getDatabase(), table.getTableName());
        }
    }
}
