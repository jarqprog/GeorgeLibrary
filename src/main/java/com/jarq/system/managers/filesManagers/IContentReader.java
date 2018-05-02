package com.jarq.system.managers;

import java.io.IOException;

public interface IContentReader<T> {

    T readContent(String filePath) throws IOException;
}
