package com.jarq.system.managers;

import java.io.IOException;

public interface IContentWriter<T> {

    boolean writeContent(String filePath, T content) throws IOException;
}
