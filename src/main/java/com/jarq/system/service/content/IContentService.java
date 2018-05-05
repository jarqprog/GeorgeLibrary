package com.jarq.system.service.content;

import com.jarq.system.service.IService;

public interface IContentService extends IService {

    String createContent(int userId, String content);

    String changeRepositoryName(int repositoryId, String repositoryName);

    String[] getUserRepositories(int userId);

    String removeRepository(int repositoryId);

    String[] removeUserRepositories(int userId);


}
