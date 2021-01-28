package com.esp_ota_update.server.model;

import java.time.LocalDateTime;

public class History {
    private Integer updateId;
    private Integer deviceId;
    private LocalDateTime timestamp;
    private Integer status;

    private String baseVersion;
    private String baseMd5;
    private String baseFile;
    private LocalDateTime baseCreatedAt;

    private String toVersion;
    private String toMd5;
    private String toFile;
    private LocalDateTime toCreatedAt;

    public History(int updateId) {
        this.updateId = updateId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(String baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String getBaseMd5() {
        return baseMd5;
    }

    public void setBaseMd5(String baseMd5) {
        this.baseMd5 = baseMd5;
    }

    public String getBaseFile() {
        return baseFile;
    }

    public void setBaseFile(String baseFile) {
        this.baseFile = baseFile;
    }

    public LocalDateTime getBaseCreatedAt() {
        return baseCreatedAt;
    }

    public void setBaseCreatedAt(LocalDateTime baseCreatedAt) {
        this.baseCreatedAt = baseCreatedAt;
    }

    public String getToVersion() {
        return toVersion;
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

    public String getToMd5() {
        return toMd5;
    }

    public void setToMd5(String toMd5) {
        this.toMd5 = toMd5;
    }

    public String getToFile() {
        return toFile;
    }

    public void setToFile(String toFile) {
        this.toFile = toFile;
    }

    public LocalDateTime getToCreatedAt() {
        return toCreatedAt;
    }

    public void setToCreatedAt(LocalDateTime toCreatedAt) {
        this.toCreatedAt = toCreatedAt;
    }
}
