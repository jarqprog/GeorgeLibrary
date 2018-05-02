package com.jarq.system.helpers.repositoryPath;

import com.jarq.system.enums.FileExtension;
import com.jarq.system.enums.RepositoriesPath;
import com.jarq.system.models.text.IText;

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
    public String buildFullPath(IText text) {
//        return String.format("%s%s%s%s%s%s%s%s%s",
//                        repositoryPath,
//                        userId,
//                        separator,
//                        repositoryId,
//                        separator,
//                        textId,
//                        separator,
//                        buildFilename(textModificationDate),
//                        fileExtension);

        return "";
    }

    @Override
    public String buildTextDirectory(IText text) {
        return null;
    }

    @Override
    public String buildRepoDirectory(IText text) {
        return null;
    }

    @Override
    public String buildUserDirectory(IText text) {
        return null;
    }

    private String buildFilename(String textModificationDate) {
        // replace all non alphanumeric with underscore to use as filename
        String underscore = "_";
        return textModificationDate
                .replaceAll(" ", underscore)
                .replaceAll("[^A-Za-z0-9]", underscore);
    }
}
