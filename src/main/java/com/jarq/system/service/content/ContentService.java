package com.jarq.system.service.content;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.helpers.repositoryPath.IRepositoryPath;
import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.content.IContent;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.IText;
import com.jarq.system.service.Service;

import java.io.IOException;

public class ContentService extends Service implements IContentService {

    private final IDaoContent daoContent;
    private final IDaoText daoText;
    private final IDaoRepository daoRepository;
    private final IRepositoryManager repositoryManager;
    private final IDateTimer dateTimer;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;
    private final IRepositoryPath repositoryPath;
    private final String serviceFailure = "something goes wrong with content operation.";

    public static IContentService getInstance(ILog log, IDaoContent daoContent,
                                              IDaoText daoText,
                                              IDaoRepository daoRepository,
                                              IRepositoryManager repositoryManager,
                                              IDateTimer dateTimer,
                                              IContentReader<String> contentReader,
                                              IContentWriter<String> contentWriter,
                                              IRepositoryPath repositoryPath) {
        return new ContentService(  log, daoContent, daoText, daoRepository, repositoryManager,
                                    dateTimer, contentReader, contentWriter,
                                    repositoryPath);
    }

    private ContentService(ILog log, IDaoContent daoContent,
                           IDaoText daoText,
                           IDaoRepository daoRepository,
                           IRepositoryManager repositoryManager,
                           IDateTimer dateTimer,
                           IContentReader<String> contentReader,
                           IContentWriter<String> contentWriter,
                           IRepositoryPath repositoryPath) {
        super(log);
        this.daoContent = daoContent;
        this.daoText = daoText;
        this.daoRepository = daoRepository;
        this.repositoryManager = repositoryManager;
        this.dateTimer = dateTimer;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
        this.repositoryPath = repositoryPath;
    }

    @Override
    public String createContent(int textId, String data) {
        return create(textId, data, null);
    }

    @Override
    public String createContent(int textId, byte[] data) {
        return create(textId, null, data);
    }

    @Override
    public String importContent(int contentId) {
        try {
            IContent content = daoContent.importContent(contentId);
            return content.toString(); // todo

        } catch (DaoFailure ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public String importContentsData(int contentId) {
        try {
            IContent content = daoContent.importContent(contentId);
            String path = content.getFilepath();
            return contentReader.readContent(path); // todo
        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public byte[] importContentsBytesData(int contentId) {
        try {
            IContent content = daoContent.importContent(contentId);
            String path = content.getFilepath();
            return contentReader.readContentAsBytes(path); // todo
        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure.getBytes();
        }
    }

    @Override
    public boolean changeContentsData(int textId, int contentId, String data) {
        return changeData(textId, contentId, data, null);
    }

    @Override
    public boolean changeContentsData(int textId, int contentId, byte[] data) {
        return changeData(textId, contentId, null, data);
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



    private String create(int textId, String textData, byte[] bytesData) {
        try {
            final String creationDate = dateTimer.getCurrentDateTime();
            IText text = daoText.importText(textId);
            text.setModificationDate(creationDate);  // important to set current time;
            IContent content = daoContent.createContent(text);
            final String path = repositoryPath.filepath(text);
            if( updateParentObjects(text) && repositoryManager.createFile(content) ) {
                if(bytesData == null) {
                    saveContentData(path, textData);
                } else {
                    saveContentData(path, bytesData);
                }
                return content.toString();
            }
            String message = String.format("%s Problem occurred while creating text data (id:%s)",
                    serviceFailure, text.getId());
            report(message);
            return message;

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    private boolean changeData(int textId, int contentId, String textData, byte[] bytesData) {
        try {
            IContent content = daoContent.importContent(contentId);
            String modificationDate = dateTimer.getCurrentDateTime();
            IText text = daoText.importText(textId);
            text.setModificationDate(modificationDate);
            String path = content.getFilepath();
            if(bytesData == null) {
                contentWriter.writeContent(path, textData);
            } else {
                contentWriter.writeContent(path, bytesData);
            }
            return updateParentObjects(text);
        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return false;
        }
    }

    private void saveContentData(String path, String data) throws IOException {
        contentWriter.writeContent(path, data);
    }

    private void saveContentData(String path, byte[] data) throws IOException {
        contentWriter.writeContent(path, data);
    }

    private boolean updateParentObjects(IText text) throws DaoFailure {
        // set new modification date in text & repository while doing something with content
        IRepository repository = daoRepository.importRepository(text.getRepositoryId());
        repository.setLastModificationDate(text.getModificationDate());
        boolean isTextUpdated = daoText.updateText(text);
        boolean isRepoUpdated = daoRepository.updateRepository(repository);
        return isTextUpdated && isRepoUpdated;
    }
}
