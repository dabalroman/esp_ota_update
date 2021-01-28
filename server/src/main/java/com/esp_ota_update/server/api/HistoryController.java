package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.History;
import com.esp_ota_update.server.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/history")
@RestController
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Response> getHistoryByDeviceId(@PathVariable("id") int id) {
        List<History> data = historyService.getHistoryByDeviceId(id);
        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new Response(data.toArray(), true, httpStatus).responseEntity();
    }
}
