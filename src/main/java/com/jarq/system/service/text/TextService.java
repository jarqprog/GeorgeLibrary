package com.jarq.system.service.text;

import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IContentReader;
import com.jarq.system.managers.filesManagers.IContentWriter;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.content.IDaoContent;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.IText;
import com.jarq.system.service.Service;

public class TextService extends Service implements ITextService {

    private final IRepositoryManager repositoryManager;
    private final IContentReader<String> contentReader;
    private final IContentWriter<String> contentWriter;
    private final IDaoText daoText;
    private final IDaoContent daoContent;


    public static ITextService getInstance( ILog log,
                                            IRepositoryManager repositoryManager,
                                            IContentReader<String> contentReader,
                                            IContentWriter<String> contentWriter,
                                            IDaoText daoText,
                                            IDaoContent daoContent) {
        return new TextService(log, repositoryManager, contentReader,
                contentWriter, daoText, daoContent);
    }

    private TextService(ILog log,
                        IRepositoryManager repositoryManager,
                        IContentReader<String> contentReader,
                        IContentWriter<String> contentWriter,
                        IDaoText daoText,
                        IDaoContent daoContent) {
        super(log);
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
