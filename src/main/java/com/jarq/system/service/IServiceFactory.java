package com.jarq.system.service;

public interface IServiceFactory {

    <T extends IService> T createService(Class<T> serviceType);
}
