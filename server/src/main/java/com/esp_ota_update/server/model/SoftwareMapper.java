package com.esp_ota_update.server.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SoftwareMapper implements RowMapper<Software> {

    @Override
    public Software mapRow(ResultSet rs, int rowNum) throws SQLException {
        //TODO: Fix sql id null is imported as 0
        Software software = new Software(rs.getInt("id"));

        software.setVersion(rs.getString("version"));
        software.setFile(rs.getString("file"));
        software.setMd5(rs.getString("md5"));
        software.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        software.setDeviceId(rs.getInt("device_id"));
        software.setPreviousVersionId(rs.getInt("previous_version_id"));

        return software;
    }
}
