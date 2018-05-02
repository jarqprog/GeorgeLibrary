package com.jarq.system.managers.filesManagers;

import java.io.IOException;

public interface IContentReader<T> {

    T readContent(String filePath) throws IOException;
}
