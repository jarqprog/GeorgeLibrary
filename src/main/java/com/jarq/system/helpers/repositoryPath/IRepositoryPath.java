package com.jarq.system.helpers.repositoryPath;

import com.jarq.system.models.text.IText;

public interface IRepositoryPath {

    /**
     * build path with:
     *   "src/main/resources/repositories" (path to repositories)
     * + "userId" (this directory contains all user repositories)
     * + "repositoryId" (this directory contains concrete repository)
     * + "textId" (this directory contains file/files with the same id (stores previous versions of the file))
     * + "text modificationDate" (it's concrete text file)
     */

    // example:
    // if given text with: userId:1, repositoryId:2, textId:1, modificationDate:2018-05-01 10:21:59
    // methods return:

    // "src/main/resources/repositories/1/2/1/20180501102159.repo" (path to file)
    String buildFullPath(IText text);

    // "src/main/resources/repositories/1/2/1/" (path to text files without file)
    String buildTextDirectory(IText text);

    // "src/main/resources/repositories/1/2/" (path to repository)
    String buildRepoDirectory(IText text);

    // eg. return "src/main/resources/repositories/1/" (path to repository)
    String buildUserDirectory(IText text);
}
