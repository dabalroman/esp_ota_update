package com.esp_ota_update.server.model;

import com.esp_ota_update.server.util.MD5Checksum;

import java.time.LocalDateTime;

public class Software {
    private final Integer id;

    private Integer deviceId;
    private Integer previousVersionId;

    private String version;
    private String file;
    private String md5;

    private LocalDateTime createdAt;

    //JSON constructor
    public Software() {
        this(0);
    }

    public Software(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * @param update Software
     * @return true on success
     */
    public boolean applyUpdate(Software update) {
        if (update.getId() != 0) {
            return false;
        }

        if (update.getDeviceId() != null) {
            this.deviceId = update.getDeviceId();
        }

        if (update.getFile() != null) {
            this.file = update.getFile();
            this.md5 = this.calculateFileHash(this.file);
        }

        if (update.getVersion() != null) {
            this.version = update.getVersion();
        }

        if (update.getPreviousVersionId() != null) {
            this.previousVersionId = update.getPreviousVersionId();
        }

        return true;
    }

    private String calculateFileHash(String filePath) {
        try {
            return MD5Checksum.get(filePath);
        } catch (Exception e) {
            return "md5-error";
        }
    }

    public Integer getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(Integer previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }
}
