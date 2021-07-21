package com.dowload.logs.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public final class ZipUtil {

    private static final String INIT = "init";

    private static final String FINISH = "finish";

    private static final Logger LOGGER = Logger.getLogger(ZipUtil.class.getName());

    private ZipUtil() throws InstantiationException {
        throw new InstantiationException("Objeto no puede ser instanciado");
    }

    public static void zipper( List<File> files, String zipfile ) {
        Date initDate = new Date();
        String log = "% [zipper] filenames[%] - zipfile[%]";
        LOGGER.info(StringUtils.replaceValues(log, '%', INIT, files, zipfile));
        byte[] buf = new byte[2048];
        String outFilename = zipfile;
        FileInputStream in = null;
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));) {

            for (File file : files) {
                in = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error to create zip file", e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("Error to create zip file", e);
                }
            LOGGER.info(StringUtils.replaceValues(log, '%', FINISH, files, zipfile));
            LOGGER.info(StringUtils.concat("[count] second duration:", DateUtil.timeMessage(initDate, new Date())));
        }

    }
}
