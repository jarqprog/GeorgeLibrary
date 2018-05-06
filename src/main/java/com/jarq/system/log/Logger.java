package com.jarq.system.log;

import com.jarq.system.helpers.datetimer.IDateTimer;

import java.io.IOException;

public class Logger implements ILog {

    private final IDateTimer dateTimer;
    private final ILogWriter<String> writer;

    public static ILog getInstance(IDateTimer dateTimer,
                                   ILogWriter<String> writer) {
        return new Logger(dateTimer, writer);
    }

    private Logger(IDateTimer dateTimer, ILogWriter<String> writer) {
        this.dateTimer = dateTimer;
        this.writer = writer;
    }

    @Override
    public boolean log(String message) {
        String messageSeparator = "\n========report========\n";
        StringBuilder sb = new StringBuilder(messageSeparator);
        sb.append("Time: ");
        sb.append(dateTimer.getCurrentDateTime());
        sb.append("\n");
        sb.append("Report: ");
        sb.append(message);

        try {
            return writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("log failure, can't write to file:");
            e.printStackTrace();
            return false;
        }
    }
}
