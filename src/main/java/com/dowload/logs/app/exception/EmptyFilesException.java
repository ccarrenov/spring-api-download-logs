package com.dowload.logs.app.exception;

public class EmptyFilesException extends Exception {

    private static final long serialVersionUID = -3791175895673271272L;
    
    public EmptyFilesException(String message) {
        super(message);
    }
    
    public EmptyFilesException(String message, Throwable e) {
        super(message, e);
    }    

}
