package com.jarq.system.service.text;

import com.jarq.system.service.IService;

public interface ITextService extends IService {

    String createText(int repositoryId, String title);

    String importText(int textId);

    String[] importTextsByRepository(int repositoryId);

    String[] importTextsByUser(int userId);

    String changeTitle(int textId, String title);

    String removeText(int textId);
}
