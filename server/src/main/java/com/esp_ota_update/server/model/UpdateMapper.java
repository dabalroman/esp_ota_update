package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateMapper implements RowMapper<Update> {

    @Override
    public Update mapRow(ResultSet rs, int rowNum) throws SQLException {
        Update update = new Update(rs.getInt("id"));

        update.setStatus(rs.getInt("status"));
        update.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());

        update.setSoftware_to(rs.getInt("software_to"));
        if (rs.wasNull()) {
            update.setSoftware_to(null);
        }

        update.setDeviceId(rs.getInt("device_id"));
        if (rs.wasNull()) {
            update.setDeviceId(null);
        }

        update.setSoftware_from(rs.getInt("software_from"));
        if (rs.wasNull()) {
            update.setSoftware_from(null);
        }

        return update;
    }
}
