package com.esp_ota_update.server.api;

import com.esp_ota_update.server.model.Software;
import com.esp_ota_update.server.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/software")
@RestController
public class SoftwareController {

    private final SoftwareService softwareService;

    @Autowired
    public SoftwareController(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    @PostMapping
    public ResponseEntity<Response> addSoftware(@Valid @NonNull @RequestBody Software software) {
        softwareService.addSoftware(software);

        return new Response(true, HttpStatus.OK).get();
    }

    @GetMapping
    public ResponseEntity<Response> getAllSoftware() {
        List<Software> data = softwareService.getAllSoftware();
        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new Response(data.toArray(), true, httpStatus).get();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Response> getSoftwareById(@PathVariable("id") int id) {
        List<Software> data = softwareService.getSoftwareById(id);
        HttpStatus httpStatus = !data.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new Response(data.toArray(), true, httpStatus).get();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Response> deleteSoftwareById(@PathVariable("id") int id) {
        softwareService.deleteSoftware(id);

        return new Response(true, HttpStatus.OK).get();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Response> updateSoftwareById(
            @PathVariable("id") int id,
            @Valid @NonNull @RequestBody Software software
    ) {
        softwareService.updateSoftware(id, software);

        return new Response(true, HttpStatus.OK).get();
    }
}
