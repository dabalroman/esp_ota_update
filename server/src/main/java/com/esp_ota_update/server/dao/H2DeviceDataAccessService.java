package com.esp_ota_update.server.dao;

import com.esp_ota_update.server.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("SqlResolve")
@Repository("H2")
public class H2DeviceDataAccessService implements DeviceDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2DeviceDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertDevice(UUID id, Device device) {
        final String sql = "INSERT INTO device (id, name) VALUES (?, ?)";

        return jdbcTemplate.update(sql, id, device.getName());
    }

    @Override
    public List<Device> selectAllDevices() {
        final String sql = "SELECT id, name FROM device";

        return jdbcTemplate.query(sql, ((resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Device(id, name);
        }));
    }

    @Override
    public Optional<Device> selectDeviceById(UUID id) {
        final String sql = "SELECT id, name FROM device WHERE id = ?";

        Device device = jdbcTemplate.queryForObject(sql, ((resultSet, i) -> {
            UUID deviceId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Device(deviceId, name);
        }), id);

        return Optional.ofNullable(device);
    }

    @Override
    public int deleteDeviceById(UUID id) {
        final String sql = "DELETE FROM device WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateDeviceById(UUID id, Device device) {
        final String sql = "UPDATE DEVICE SET NAME = ? WHERE ID = ?";

        return jdbcTemplate.update(sql, device.getName(), id);
    }
}


