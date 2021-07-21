package com.dowload.logs.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.dowload.logs.app.services.DownloadServicesSSH;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/download-logs")
@Api(value = "Logs", description = "Everything download logs", tags = { "Logs" })
public class DownloadFileSSHController {

    @Autowired
    private DownloadServicesSSH service;

    @ApiOperation(value = "Everything download logs", tags = { "Logs" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = StreamingResponseBody.class), @ApiResponse(code = 500, message = "Failure") })
    @GetMapping(value = "", produces = { /*"application/zip", */MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<Object> donwload( @RequestParam(defaultValue = "ws", required = true) String folder,
            @RequestParam(defaultValue = "cebalrai.reddipres.cl", required = true) String host, @RequestParam(defaultValue = "ws.zip", required = true) String zipName ) {
        return service.downloadLogs(folder, host, zipName);
    }
}
