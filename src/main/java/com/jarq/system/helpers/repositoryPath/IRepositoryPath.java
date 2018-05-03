package com.jarq.system.helpers.repositoryPath;

import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.user.IUser;

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

    // "src/main/resources/repositories/1/2/1/20180501102159.repo" (path to file with content)
    String content(IText text);

    // "src/main/resources/repositories/1/2/1/" (path to directory with contents files)
    String textDir(IText text);

    // "src/main/resources/repositories/1/2/" (path to repository with texts)
    String repositoryDir(IRepository repository);

    // eg. return "src/main/resources/repositories/1/" (path to all user's repositories)
    String userDir(IUser user);
}
