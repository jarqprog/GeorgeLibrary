package com.jarq.system.managers.filesManagers;

import java.io.IOException;

public interface IRepoManager {

    // return true if path exists
    boolean inspect(String fullFilepath);

    boolean create(String fullFilepath) throws IOException;

    boolean demolish(String fullFilepath) throws IOException;
}
