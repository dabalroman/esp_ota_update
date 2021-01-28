package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.History;
import com.esp_ota_update.server.model.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository("H2-History")
public class HistoryDataAccessService implements HistoryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<History> selectHistoryByDeviceId(int deviceId) {
        final String sql = "SELECT `UPDATE`.ID, `UPDATE`.DEVICE_ID,`UPDATE`.TIMESTAMP,`UPDATE`.STATUS," +
                " BASE.VERSION AS BASE_VERSION, BASE.MD5 AS BASE_MD5," +
                " BASE.FILE AS BASE_FILE, BASE.CREATED_AT AS BASE_CREATED_AT," +
                " TARGET.VERSION AS TARGET_VERSION, TARGET.MD5 AS TARGET_MD5," +
                " TARGET.FILE AS TARGET_FILE, TARGET.CREATED_AT AS TARGET_CREATED_AT" +
                " FROM `UPDATE`" +
                " LEFT JOIN SOFTWARE TARGET ON TARGET.ID = `UPDATE`.SOFTWARE_TO" +
                " LEFT JOIN SOFTWARE BASE ON BASE.ID = `UPDATE`.SOFTWARE_FROM" +
                " WHERE `UPDATE`.DEVICE_ID = ?" +
                " ORDER BY `UPDATE`.ID DESC";

        return jdbcTemplate.query(sql, new HistoryMapper(), deviceId);
    }
}


