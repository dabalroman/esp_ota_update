package com.esp_ota_update.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Device {

    private final UUID id;
    private final String name;

    public Device(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
