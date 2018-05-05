package com.jarq.system.service.content;

import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.service.Service;

public class ContentService extends Service implements IContentService {

    private final IDaoContent daoContent;
    private final IDaoText daoText;
    private final IRepositoryManager repositoryManager;
    private final IDateTimer dateTimer;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;
    private final IRepositoryPath repositoryPath;

    public static IContentService getInstance(ILog log, IDaoContent daoContent,
                                              IDaoText daoText,
                                              IRepositoryManager repositoryManager,
                                              IDateTimer dateTimer,
                                              IContentReader<String> contentReader,
                                              IContentWriter<String> contentWriter,
                                              IRepositoryPath repositoryPath) {
        return new ContentService(  log, daoContent, daoText, repositoryManager,
                                    dateTimer, contentReader, contentWriter,
                                    repositoryPath);
    }

    private ContentService(ILog log, IDaoContent daoContent,
                           IDaoText daoText,
                           IRepositoryManager repositoryManager,
                           IDateTimer dateTimer,
                           IContentReader<String> contentReader,
                           IContentWriter<String> contentWriter,
                           IRepositoryPath repositoryPath) {
        super(log);
        this.daoContent = daoContent;
        this.daoText = daoText;
        this.repositoryManager = repositoryManager;
        this.dateTimer = dateTimer;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
        this.repositoryPath = repositoryPath;
    }

    @Override
    public String createContent(int textId, String data) {
        return null;
    }

    @Override
    public String createContent(int textId, byte[] data) {
        return null;
    }

    @Override
    public String importContent(int contentId) {
        return null;
    }

    @Override
    public String importContentsData(int contentId) {
        return null;
    }

    @Override
    public byte[] importContentsBytesData(int contentId) {
        return new byte[0];
    }

    @Override
    public boolean changeContentsData(int contentId, String data) {
        return false;
    }

    @Override
    public boolean changeContentsData(int contentId, byte[] data) {
        return false;
    }

    @Override
    public String[] importContentsByText(int textId) {
        return new String[0];
    }

    @Override
    public String removeContent(int contentId) {
        return null;
    }

    @Override
    public String[] removeTextContents(int textId) {
        return new String[0];
    }
}
