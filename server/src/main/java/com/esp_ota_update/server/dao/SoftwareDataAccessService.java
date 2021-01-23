package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Software;
import com.esp_ota_update.server.model.SoftwareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository("H2-Software")
public class SoftwareDataAccessService implements SoftwareDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SoftwareDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertSoftware(Software software) {
        final String sql =
                "INSERT INTO software (version, file, md5, created_at, device_id, previous_version_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                software.getVersion(),
                software.getFile(),
                software.getMd5(),
                software.getCreatedAt(),
                software.getDeviceId(),
                software.getPreviousVersionId()
        );
    }

    @Override
    public List<Software> selectAllSoftware() {
        final String sql = "SELECT id, version, file, md5, created_at, device_id, previous_version_id FROM software";

        return jdbcTemplate.query(sql, new SoftwareMapper());
    }

    @Override
    public List<Software> selectSoftwareById(int id) {
        final String sql = "SELECT id, version, file, md5, created_at, device_id, previous_version_id"
                + " FROM software WHERE id = ?";

        return jdbcTemplate.query(sql, new SoftwareMapper(), id);
    }

    @Override
    public List<Software> selectSoftwareByDeviceId(int deviceId) {
        final String sql = "SELECT id, version, file, md5, created_at, device_id, previous_version_id"
                + " FROM software WHERE device_id = ?";

        return jdbcTemplate.query(sql, new SoftwareMapper(), deviceId);
    }

    @Override
    public List<Software> selectLatestSoftwareByDeviceId(int deviceId) {
        final String sql = "SELECT id, version, file, md5, created_at, device_id, previous_version_id"
                + " FROM software WHERE device_id = ? LIMIT 1";

        return jdbcTemplate.query(sql, new SoftwareMapper(), deviceId);
    }

    @Override
    public int deleteSoftwareById(int id) {
        final String sql = "DELETE FROM software WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateSoftware(Software software) {
        final String sql = "UPDATE software SET version = ?, file = ?, md5 = ?, device_id = ?, "
                + "previous_version_id = ? WHERE ID = ?";

        return jdbcTemplate.update(
                sql,
                software.getVersion(),
                software.getFile(),
                software.getMd5(),
                software.getDeviceId(),
                software.getPreviousVersionId(),
                software.getId()
        );
    }
}


