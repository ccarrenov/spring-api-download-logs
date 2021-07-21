package com.dowload.logs.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dowload.logs.app.ctes.LogMsgCtes;
import com.dowload.logs.app.exception.EmptyFilesException;
import com.dowload.logs.app.model.DataModel;
import com.dowload.logs.app.util.DateUtil;
import com.dowload.logs.app.util.DownloadFileSSHUtil;
import com.dowload.logs.app.util.StringUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import lombok.Getter;

@Service
@Getter
public class DownloadServicesSSH {

    private static final Logger LOGGER = Logger.getLogger(DownloadServicesSSH.class.getName());

    @Autowired
    private ServletContext servletContext;

    public ResponseEntity<Object> downloadLogs( String folder, String host, String zipName ) {
        Date initDate = new Date();
        String log = "% [donwload] folder[%] host[%] zipName[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', LogMsgCtes.INIT, folder, host, zipName));
        File file = new File(zipName);
        InputStreamResource resource = null;
        String basePath = System.getenv("BASE_PATH");
        String user = System.getenv("USER_HOST");
        String pass = System.getenv("PASS_HOST");
        int port = Integer.parseInt(System.getenv("PORT"));
        String pathFolder = basePath + folder + "/logs";

        DataModel data = new DataModel(pathFolder, "log", user, host, port, pass, zipName);
        try (FileInputStream out = new FileInputStream(file)) {
            resource = DownloadFileSSHUtil.downloadCompress(data);
        } catch (JSchException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("ssh error connection.", HttpStatus.BAD_REQUEST);
        } catch (SftpException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Not found origin directory", HttpStatus.NOT_FOUND);
        } catch (EmptyFilesException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Not found zip file", HttpStatus.NOT_FOUND);
        } finally {
            LOGGER.info(StringUtils.replaceValues(log, '%', LogMsgCtes.FINISH, folder, host, zipName));
            LOGGER.info(StringUtils.concat(LogMsgCtes.COUNT, DateUtil.timeMessage(initDate, new Date())));
        }
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

    public static MediaType getMediaTypeForFileName( ServletContext servletContext, String fileName ) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
