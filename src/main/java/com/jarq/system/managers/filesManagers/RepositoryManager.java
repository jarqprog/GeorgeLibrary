package com.jarq.system.managers.filesManagers;

import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.models.content.IContent;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositoryManager implements IRepositoryManager {

    private final IRepositoryPath repositoryPath;

    public static IRepositoryManager getInstance(IRepositoryPath repositoryPath) {
        return new RepositoryManager(repositoryPath);
    }

    private RepositoryManager(IRepositoryPath repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    @Override
    public boolean hasFile(IContent content) {
        String path = content.getFilepath();
        return hasFile(path);
    }

    @Override
    public boolean hasFile(String fullFilepath) {
        Path path = Paths.get(fullFilepath);
        return checkIfPathExists(path);
    }

    @Override
    public boolean createFile(IContent content) throws IOException {
        String path = content.getFilepath();
        return create(path);
    }

    @Override
    public boolean removeFile(IContent content) throws IOException {
        String path = content.getFilepath();
        return false;
    }

    @Override
    public boolean removeTextDirectory(IText text) throws IOException {

        String path = repositoryPath.textDir(text);

        ///


        return false;
    }

    @Override
    public boolean removeRepository(IRepository repository) throws IOException {

        String path = repositoryPath.repositoryDir(repository);

        return false;
    }

    private boolean create(String fullFilepath) throws IOException {
        if (! hasFile(fullFilepath) ) {
            File path = new File(fullFilepath);
            return path.getParentFile().mkdirs() && path.createNewFile();
        }
        return false;
    }


    private boolean checkIfPathExists(Path path) {
        return  Files.isRegularFile(path) &
                Files.isReadable(path);
    }

    private boolean deleteDirectory(File dir) {
//        if (dir.isDirectory()) {
//            File[] children = dir.listFiles();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDirectory(children[i]);
//                if (!success) {
//                    return false;
//                }
//
//            }
//        }
//        System.out.println("removing file or directory : " + dir.getName());
//        return dir.delete();

        return false;

    }



}
