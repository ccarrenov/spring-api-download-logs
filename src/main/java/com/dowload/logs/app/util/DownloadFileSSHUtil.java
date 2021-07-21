package com.dowload.logs.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;

import com.dowload.logs.app.ctes.LogMsgCtes;
import com.dowload.logs.app.exception.EmptyFilesException;
import com.dowload.logs.app.model.DataModel;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DownloadFileSSHUtil {

    private static final Logger LOGGER = Logger.getLogger(DownloadFileSSHUtil.class.getName());

    private DownloadFileSSHUtil() throws InstantiationException {
        throw new InstantiationException("Objeto no puede ser instanciado");
    }

    @SuppressWarnings("unchecked")
    public static InputStreamResource downloadCompress( DataModel data ) throws JSchException, SftpException, EmptyFilesException, IOException {
        Date initDate = new Date();
        String log = "% [donwload] data[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', LogMsgCtes.INIT, data));
        JSch jsch = new JSch();
        Session session = jsch.getSession(data.getUser(), data.getHost(), data.getPort());
        session.setPassword(data.getPass());
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        LOGGER.info("Host connected.");
        Channel channel = session.openChannel("sftp");
        LOGGER.info("sftp channel opened and connected.");
        ChannelSftp channelSftp = (ChannelSftp) channel;
        channelSftp.connect();
        channelSftp.cd(data.getPathFolder());
        List<LsEntry> detailFile = channelSftp.ls(".");
        List<File> files = new ArrayList<>();
        for (LsEntry le : detailFile) {
            if (le.getFilename().contains("." + data.getExtentionFile())) {
                File newFile = new File(le.getFilename());
                files.add(newFile);
                FileUtil.create(newFile, channelSftp.get(le.getFilename()));
            }
        }

        if (files.isEmpty())
            throw new EmptyFilesException("Not files with extension ." + data.getExtentionFile());
        ZipUtil.zipper(files, data.getZipName());
        FileUtil.delete(files);
        LOGGER.info(StringUtils.replaceValues(log, '%', LogMsgCtes.FINISH, data));
        LOGGER.info(StringUtils.concat(LogMsgCtes.COUNT, DateUtil.timeMessage(initDate, new Date())));
        return new InputStreamResource(new FileInputStream(new File(data.getZipName())));

    }
}
