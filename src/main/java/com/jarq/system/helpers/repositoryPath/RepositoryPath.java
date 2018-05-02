package com.jarq.system.helpers.repositoryPath;

import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;

import java.io.File;

public class RepositoryPath implements IRepositoryPath {

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
    public String build(int userId, int repositoryId, int textId, String textModificationDate) {
        return String.format("%s%s%s%s%s%s%s%s%s",
                        repositoryPath,
                        userId,
                        separator,
                        repositoryId,
                        separator,
                        textId,
                        separator,
                        buildFilename(textModificationDate),
                        fileExtension);
    }

    private String buildFilename(String textModificationDate) {
        // replace all non alphanumeric with underscore to use as filename
        String underscore = "_";
        return textModificationDate
                .replaceAll(" ", underscore)
                .replaceAll("[^A-Za-z0-9]", underscore);
    }
}
