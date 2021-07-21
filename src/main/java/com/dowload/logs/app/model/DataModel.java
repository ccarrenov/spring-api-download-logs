package com.dowload.logs.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {

    private String pathFolder;

    private String extentionFile;

    private String user;

    private String host;

    private int port;

    private String pass;

    private String zipName;

    @Override
    public String toString() {
        return "DataModel [pathFolder=" + pathFolder + ", extentionFile=" + extentionFile + ", user=" + user + ", host=" + host + ", port=" + port + ", pass=XXXXXXXX" + ", zipName=" + zipName + "]";
    }

}
