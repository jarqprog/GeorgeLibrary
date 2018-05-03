package com.jarq.system.managers.filesManagers;

import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.models.content.IContent;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.user.IUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static java.nio.file.Files.deleteIfExists;

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
        String filepath = content.getFilepath();
        Path path = Paths.get(filepath);
        return deleteIfExists(path);
    }

    @Override
    public boolean removeTextDirectory(IText text) throws IOException {
        String path = repositoryPath.textDir(text);
        return deletePath(path);
    }

    @Override
    public boolean removeRepository(IRepository repository) throws IOException {
        String path = repositoryPath.repositoryDir(repository);
        return deletePath(path);
    }

    @Override
    public boolean removeUserRepositories(IUser user) throws IOException {
        String path = repositoryPath.userDir(user);
        return deletePath(path);
    }

    private boolean create(String fullFilepath) throws IOException {
        if (! hasFile(fullFilepath) ) {
            File path = new File(fullFilepath);
            path.getParentFile().mkdirs();
            return path.createNewFile();
        }
        return false;
    }

    private boolean checkIfPathExists(Path path) {
        return  Files.isRegularFile(path) &
                Files.isReadable(path);
    }

    private boolean deletePath(String pathToRemove) throws IOException {
        // be careful!
        checkRemovalSecurity(pathToRemove);  // throws exception if repo is in danger
        Path path = Paths.get(pathToRemove);
        return Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile).allMatch(File::delete);
    }

    private void checkRemovalSecurity(String pathToRemove) throws IOException {

        // stop removing if 'border' subdirectory is in danger (or dirs behind 'border')
        String[] pathAsArray = pathToRemove.split(File.separator);
        boolean con1, con2, con3, con4, con5, con6;
        String currentDirectory = pathAsArray[pathAsArray.length-1];
        con1 = currentDirectory.equals("border");
        con2 = currentDirectory.equals("repositories");
        con3 = currentDirectory.equals("testRepositories");
        con4 = currentDirectory.equals("resources");
        con5 = currentDirectory.equals("main");
        con6 = currentDirectory.equals("src");

        if( con1 || con2 || con3 || con4 || con5 || con6 ) {
            throw new IOException("Stop! Can not delete it further, repository in danger!");
        }
    }
}
