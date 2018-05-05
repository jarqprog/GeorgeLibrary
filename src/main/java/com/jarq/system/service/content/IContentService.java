package com.jarq.system.service.content;

import com.jarq.system.service.IService;

public interface IContentService extends IService {

    String createContent(int textId, String data);

    String createContent(int textId, byte[] data);

    String importContent(int contentId);

    String importContentsData(int contentId);

    byte[] importContentsBytesData(int contentId);

    boolean changeContentsData(int contentId, String data);

    boolean changeContentsData(int contentId, byte[] data);

    String[] importContentsByText(int textId);

    String removeContent(int contentId);
}
