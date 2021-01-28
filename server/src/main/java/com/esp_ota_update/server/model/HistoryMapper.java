package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryMapper implements RowMapper<History> {

    @Override
    public History mapRow(ResultSet rs, int rowNum) throws SQLException {
        History history = new History(rs.getInt("id"));

        history.setDeviceId(rs.getInt("device_id"));
        history.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        history.setStatus(rs.getInt("status"));

        history.setBaseVersion(rs.getString("base_version"));
        if (rs.wasNull()) {
            history.setBaseVersion(null);
        }

        history.setBaseMd5(rs.getString("base_md5"));
        if (rs.wasNull()) {
            history.setBaseMd5(null);
        }

        history.setBaseFile(rs.getString("base_file"));
        if (rs.wasNull()) {
            history.setBaseFile(null);
        }

        if (rs.getTimestamp("base_created_at") != null) {
            history.setBaseCreatedAt(rs.getTimestamp("base_created_at").toLocalDateTime());
        } else {
            history.setBaseCreatedAt(null);
        }

        history.setToVersion(rs.getString("target_version"));
        if (rs.wasNull()) {
            history.setToVersion(null);
        }

        history.setToMd5(rs.getString("target_md5"));
        if (rs.wasNull()) {
            history.setToMd5(null);
        }

        history.setToFile(rs.getString("target_file"));
        if (rs.wasNull()) {
            history.setToFile(null);
        }

        if (rs.getTimestamp("target_created_at") != null) {
            history.setToCreatedAt(rs.getTimestamp("target_created_at").toLocalDateTime());
        } else {
            history.setToCreatedAt(null);
        }

        return history;
    }
}
