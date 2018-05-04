package com.jarq.system.service;

public interface IServiceFactory {

    <T extends IService> T createSQLiteService(Class<T> serviceType);
}
