package com.dowload.logs.app.ctes;


public final class LogMsgCtes {
    
    private LogMsgCtes() throws InstantiationException {
        throw new InstantiationException("You can't create new instance of DateUtil.");
    }    
    
    public static final String INIT = "init";

    public static final String FINISH = "finish";

    public static final String COUNT = "[count] second duration:";
}
