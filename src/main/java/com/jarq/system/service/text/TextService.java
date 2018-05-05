package com.jarq.system.service.text;

import com.jarq.system.exceptions.DaoFailure;
import com.jarq.system.helpers.datetimer.IDateTimer;
import com.jarq.system.log.ILog;
import com.jarq.system.managers.filesManagers.IRepositoryManager;
import com.jarq.system.models.repository.IDaoRepository;
import com.jarq.system.models.repository.IRepository;
import com.jarq.system.models.text.IDaoText;
import com.jarq.system.models.text.IText;
import com.jarq.system.models.user.IDaoUser;
import com.jarq.system.models.user.IUser;
import com.jarq.system.service.Service;

import java.io.IOException;
import java.util.List;

public class TextService extends Service implements ITextService {

    private final IRepositoryManager repositoryManager;
    private final IDaoText daoText;
    private final IDaoRepository daoRepository;
    private final IDaoUser daoUser;
    private final IDateTimer dateTimer;
    private final String serviceFailure = "something goes wrong with text operation.";


    public static ITextService getInstance( ILog log,
                                            IRepositoryManager repositoryManager,
                                            IDaoText daoText,
                                            IDaoRepository daoRepository,
                                            IDaoUser daoUser,
                                            IDateTimer dateTimer) {
        return new TextService(log, repositoryManager,
                    daoText, daoRepository, daoUser, dateTimer);
    }

    private TextService(ILog log,
                        IRepositoryManager repositoryManager,
                        IDaoText daoText,
                        IDaoRepository daoRepository,
                        IDaoUser daoUser,
                        IDateTimer dateTimer) {
        super(log);
        this.repositoryManager = repositoryManager;
        this.daoText = daoText;
        this.daoRepository = daoRepository;
        this.daoUser = daoUser;
        this.dateTimer = dateTimer;
    }

    @Override
    public String createText(int repositoryId, String title) {

        try {
            IRepository repository = daoRepository.importRepository(repositoryId);
            IText text = daoText.createText(repository, title);
            repository.setLastModificationDate(dateTimer.getCurrentDateTime());  // update repository's modification date;
            daoRepository.updateRepository(repository);
            repositoryManager.createDir(text);
            return text.toString(); // todo

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public String importText(int textId) {
        try {
            IText text = daoText.importText(textId);
            return text.toString(); // todo

        } catch (DaoFailure ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    @Override
    public String[] importTextsByRepository(int repositoryId) {
        try {
            List<IText> texts = daoText.importTextsByRepositoryId(repositoryId);
            return texts.stream()
                    .map(IText::toString)
                    .toArray(String[]::new);

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return new String[0];
        }
    }

    @Override
    public String[] importTextsByUser(int userId) {
        try {
            IUser user = daoUser.importUser(userId);
            List<IText> texts = daoText.importTextsByUser(user);
            return texts.stream()
                    .map(IText::toString)
                    .toArray(String[]::new);

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return new String[0];
        }
    }

    @Override
    public String changeTitle(int textId, String title) {
        try {
            IText text = daoText.importText(textId);
            text.setTitle(title);
            text.setModificationDate(dateTimer.getCurrentDateTime());
            return updateText(text); // todo

        } catch (DaoFailure daoFailure) {
            reportException(daoFailure);
            return serviceFailure;
        }
    }

    @Override
    public String removeText(int textId) {
        try {
            IText text = daoText.importText(textId);
            IRepository repository = daoRepository.importRepository(text.getRepositoryId());
            repository.setLastModificationDate(dateTimer.getCurrentDateTime());
            daoRepository.updateRepository(repository);

            boolean dbCleared = daoText.removeText(text);
            boolean repoCleared = repositoryManager.removeTextDirectory(text);

            if ( dbCleared && repoCleared ) {
                return text.toString(); // todo
            }

            String message = String.format("%s Problem occurred while removing text (id:%s).", serviceFailure, textId);
            report(message);
            return message;

        } catch (DaoFailure | IOException ex) {
            reportException(ex);
            return serviceFailure;
        }
    }

    private String updateText(IText text) throws DaoFailure {
        IRepository repository = daoRepository.importRepository(text.getRepositoryId());
        repository.setLastModificationDate(dateTimer.getCurrentDateTime());
        daoRepository.updateRepository(repository);
        if ( daoText.updateText(text) ) {
            return text.toString();
        }

        String message = String.format("%s Problem occurred while updating text (id:%s)",
                serviceFailure, text.getId());
        report(message);
        return message;
    }
}
