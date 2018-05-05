package com.jarq.system.log;

import com.jarq.system.enums.LogPath;
import com.jarq.system.helpers.datetimer.IDateTimer;

public class Logger implements ILog {

    private final String logPath;
    private final IDateTimer dateTimer;

    private Logger(LogPath logPath)

    @Override
    public boolean log(String message) {
        return false;
    }
}
