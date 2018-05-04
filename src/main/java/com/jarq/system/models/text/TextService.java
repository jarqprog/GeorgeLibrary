package com.jarq.system.models.text;

import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.content.IDaoContent;

public class TextService implements ITextService {

    private final IRepositoryManager repositoryManager;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;
    private final IDaoText daoText;
    private final IDaoContent daoContent;


    public static ITextService getInstance(IRepositoryManager repositoryManager,
                                           IContentReader<String> contentReader,
                                           IContentWriter<String> contentWriter,
                                           IDaoText daoText,
                                           IDaoContent daoContent) {
        return new TextService( repositoryManager, contentReader,
                contentWriter, daoText, daoContent);
    }

    private TextService(IRepositoryManager repositoryManager,
                       IContentReader<String> contentReader,
                       IContentWriter<String> contentWriter,
                       IDaoText daoText,
                       IDaoContent daoContent) {
        this.repositoryManager = repositoryManager;
        this.contentReader = contentReader;
        this.contentWriter = contentWriter;
        this.daoText = daoText;
        this.daoContent = daoContent;
    }

    @Override
    public IText createText() {
        return null;
    }
}
