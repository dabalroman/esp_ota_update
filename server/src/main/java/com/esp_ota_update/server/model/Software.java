package com.esp_ota_update.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Software {
    private final int id;

    private Integer deviceId;
    private Integer previousVersionId;

    @NotBlank
    private String version;
    private String file;
    private String md5;

    private LocalDateTime createdAt;

    public Software(int id) {
        this.id = id;
    }

    public Software(@JsonProperty("version") String version) {
        this.id = -1;
        this.version = version;
        this.deviceId = 0;
        this.previousVersionId = 0;
        this.file = "";
        this.md5 = "";
        this.createdAt = LocalDateTime.now();
    }

    public Integer getDeviceId() {
        return deviceId;
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
