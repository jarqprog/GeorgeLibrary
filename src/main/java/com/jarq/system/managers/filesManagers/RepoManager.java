package com.jarq.system.managers.filesManagers;

import com.jarq.system.helpers.repositoryPath.IRepositoryPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepoManager implements IRepoManager {

    private final IRepositoryPath repositoryPath;

    public static IRepoManager getInstance(IRepositoryPath repositoryPath) {
        return new RepoManager(repositoryPath);
    }

    private RepoManager(IRepositoryPath repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    @Override
    public boolean inspect(String fullFilepath) {
        Path path = Paths.get(fullFilepath);
        return checkIfPathExists(path);
    }

    @Override
    public boolean create(String fullFilepath) throws IOException {
        if (! inspect(fullFilepath) ) {
            File path = new File(fullFilepath);
            return path.getParentFile().mkdirs() && path.createNewFile();
        }
        return false;
    }

    @Override
    public boolean demolish(String fullFilepath) throws IOException {
        File targetFile = new File(fullFilepath);
        return deleteDirectory(targetFile);
    }

    private boolean checkIfPathExists(Path path) {
        return  Files.isRegularFile(path) &
                Files.isReadable(path);
    }

    private boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }

            }
        }
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();

    }



}
