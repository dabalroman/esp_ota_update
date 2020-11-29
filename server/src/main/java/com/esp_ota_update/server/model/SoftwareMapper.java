package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SoftwareMapper implements RowMapper<Software> {

    @Override
    public Software mapRow(ResultSet rs, int rowNum) throws SQLException {
        Software software = new Software(rs.getInt("id"));

        software.setVersion(rs.getString("version"));

        software.setFile(rs.getString("file"));
        if (rs.wasNull()) {
            software.setFile(null);
        }

        software.setMd5(rs.getString("md5"));
        if (rs.wasNull()) {
            software.setMd5(null);
        }

        software.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        if (rs.wasNull()) {
            software.setCreatedAt(null);
        }

        software.setDeviceId(rs.getInt("device_id"));
        if (rs.wasNull()) {
            software.setDeviceId(null);
        }

        software.setPreviousVersionId(rs.getInt("previous_version_id"));
        if (rs.wasNull()) {
            software.setPreviousVersionId(null);
        }

        return software;
    }
}
