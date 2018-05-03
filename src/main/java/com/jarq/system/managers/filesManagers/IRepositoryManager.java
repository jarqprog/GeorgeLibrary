package com.jarq.system.managers.filesManagers;

import com.jarq.system.models.content.IContent;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;

import java.io.IOException;

public interface IRepositoryManager {

    // return true if path exists
    boolean hasFile(IContent content);

    // return true if path exists
    boolean hasFile(String filepath);

    boolean createFile(IContent content) throws IOException;

    boolean removeFile(IContent content) throws IOException;

    boolean removeTextDirectory(IText text) throws IOException;

    boolean removeRepository(IRepository repository) throws IOException;
}
