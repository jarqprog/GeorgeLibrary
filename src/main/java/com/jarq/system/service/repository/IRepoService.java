package com.jarq.system.service.repository;

import com.jarq.system.service.IService;

public interface IRepoService extends IService {

    String createRepository(int userId, String repositoryName);

    String[] getUserRepositories(int userId);

    boolean removeRepository(int repositoryId);
}
