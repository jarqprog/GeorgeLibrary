package com.jarq.system.helpers.repositoryPath;

import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.user.IUser;

import java.io.File;

public class RepositoryPath implements IRepositoryPath {

    /**
     * parses data to proper filepath/directory path
     */

    private final String repositoryPath;
    private final String separator = File.separator;
    private final String fileExtension;

    public static IRepositoryPath getInstance(RepositoriesPath repositoriesPath,
                                              FileExtension fileExtension) {
        return new RepositoryPath(repositoriesPath, fileExtension);
    }

    private RepositoryPath(RepositoriesPath repositoriesPath, FileExtension fileExtension) {
        this.repositoryPath = repositoriesPath.getPath();
        this.fileExtension = fileExtension.getExtension();
    }

    @Override
    public String filepath(IText text) {
        return String.format("%s%s%s",
                        textDir(text),
                        buildFilename(text.getModificationDate()),
                        fileExtension);

    }

    @Override
    public String textDir(IText text) {
        return String.format("%s%s%s%s%s%s%s",
                repositoryPath,
                text.getUserId(),
                separator,
                text.getRepositoryId(),
                separator,
                text.getId(),
                separator);
    }

    @Override
    public String repositoryDir(IRepository repository) {
        return String.format("%s%s%s%s%s",
                repositoryPath,
                repository.getUserId(),
                separator,
                repository.getId(),
                separator);
    }

    @Override
    public String userDir(IUser user) {
        return String.format("%s%s%s",
                repositoryPath,
                user.getId(),
                separator);
    }

    private String buildFilename(String textModificationDate) {
        // replace all non alphanumeric with underscore to use as filename
        String underscore = "_";
        return textModificationDate
                .replaceAll(" ", underscore)
                .replaceAll("[^A-Za-z0-9]", underscore);
    }
}
