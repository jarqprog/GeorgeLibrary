package com.jarq.system.log;

import java.io.IOException;

public interface ILogWriter<T> {

    boolean write(T log) throws IOException;

}
