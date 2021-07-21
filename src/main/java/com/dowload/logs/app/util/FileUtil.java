package com.dowload.logs.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {

    private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

    private static final String INIT = "init";

    private static final String FINISH = "finish";
    
    private static final String COUNT = "[count] second duration:";

    private FileUtil() throws InstantiationException {
        throw new InstantiationException("Objeto no puede ser instanciado");
    }

    public static void create( File file, InputStream inputStream ) {
        Date initDate = new Date();
        String log = "% [create] filenames[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', INIT, file));

        try (InputStream sshFile = inputStream; OutputStream localFile = new FileOutputStream(file)) {
            int byteRead;
            while ((byteRead = sshFile.read()) != -1) {
                // Write the least-significant byte of int, drop the upper 3 bytes
                localFile.write(byteRead);
            }
        } catch (IOException e) {
            LOGGER.error("Error load file", e);
        } finally {
            LOGGER.info(StringUtils.replaceValues(log, '%', FINISH, file));
            LOGGER.info(StringUtils.concat(COUNT, DateUtil.timeMessage(initDate, new Date())));
        }
    }
    
    public static void delete(List<File> files) {
        Date initDate = new Date();
        String log = "% [deleteFiles] files[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', INIT, files.size()));        
        for (File file : files) {
            try {
                delete(file);
            } catch (IOException e) {
                LOGGER.error(file.getAbsolutePath(), e);
            }
        }
        LOGGER.info(StringUtils.replaceValues(log, '%', FINISH, files.size()));
        LOGGER.info(StringUtils.concat(COUNT, DateUtil.timeMessage(initDate, new Date())));        
    }

    public static void delete( File file ) throws IOException {
        Date initDate = new Date();
        String log = "% [delete] file[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', INIT, file));
        Files.delete(file.toPath());
        LOGGER.info(StringUtils.replaceValues(log, '%', FINISH, file));
        LOGGER.info(StringUtils.concat(COUNT, DateUtil.timeMessage(initDate, new Date())));
    }  
}
