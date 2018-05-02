package com.jarq.system.managers.filesManagers;

import java.io.IOException;

public interface IContentWriter<T> {

    boolean writeContent(String filePath, T content) throws IOException;
}
