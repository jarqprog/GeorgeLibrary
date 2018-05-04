package com.jarq.system.service;

import com.jarq.system.dao.IDaoFactory;

public class ServiceFactory implements IServiceFactory {

    private final IDaoFactory daoFactory;

    public static IServiceFactory getInstance(IDaoFactory daoFactory) {
        return new ServiceFactory(daoFactory);
    }


    private ServiceFactory(IDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public <T extends IService> T createService(Class<T> serviceType) {
        return null;
    }
}
