package com.esp_ota_update.server.model;

import java.time.LocalDateTime;

public class Update {
    private final Integer id;

    private Integer status;
    private Integer deviceId;
    private Integer software_to;
    private Integer software_from;

    private LocalDateTime timestamp;

    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_OK = 1;
    public static final Integer STATUS_ERROR = 2;

    public Update(){
        this(0);

        this.timestamp = LocalDateTime.now();
        this.status = STATUS_PENDING;
    }

    public Update(Integer id) {
        this.id = id;
    }

    public boolean applyUpdate(Update update){
        if(update.getId() != 0){
            return false;
        }

        if(update.getDeviceId() != null){
            this.deviceId = update.getDeviceId();
        }

        if(update.getSoftware_from() != null){
            this.software_from = update.getSoftware_from();
        }

        if(update.getSoftware_to() != null){
            this.software_to = update.getSoftware_to();
        }

        return true;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSoftware_to() {
        return software_to;
    }

    public void setSoftware_to(Integer software_to) {
        this.software_to = software_to;
    }

    public Integer getSoftware_from() {
        return software_from;
    }

    public void setSoftware_from(Integer software_from) {
        this.software_from = software_from;
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
}
