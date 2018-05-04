package com.jarq.system.managers.filesManagers;

import com.jarq.system.models.content.IContent;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.user.IUser;

import java.io.IOException;

public interface IRepositoryManager {

    // return true if path exists
    boolean hasFile(IContent content);

    // return true if path exists
    boolean hasFile(String filepath);

    // return true if path exists
    boolean hasDir(String filepath);

    boolean createFile(IContent content) throws IOException;

    boolean createDir(IUser user);

    boolean createDir(IRepository repository);

    boolean createDir(IText text);

    boolean removeFile(IContent content) throws IOException;

    boolean removeTextDirectory(IText text) throws IOException;

    boolean removeRepository(IRepository repository) throws IOException;

    boolean removeUserRepositories(IUser user) throws IOException;
}
