package fuzz.computeclient;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidParameterException;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public final class ProxyFactory<T> {
    public enum ProxyType {
        NATIVE, SPRING
    }

    public static ProxyBuilder getBuilder(final ProxyType proxyType, final String service,
            final String registryHostName, final int registryPort) throws RemoteException {
        final Registry registry = LocateRegistry.getRegistry(registryHostName, registryPort);
        switch (proxyType) {
        case NATIVE:
            return new ProxyBuilder() {
                @SuppressWarnings("unchecked")
                @Override
                public <T> T build(final Class<T> serviceClass) throws AccessException, RemoteException, NotBoundException {
                    return (T) registry.lookup(service);
                }
            };
        case SPRING:
            return new ProxyBuilder() {
                @SuppressWarnings("unchecked")
                @Override
                public <T> T build(final Class<T> serviceClass) throws Exception {
                    RmiProxyFactoryBean p = new RmiProxyFactoryBean();
                    p.setServiceInterface(serviceClass);
                    p.setServiceUrl("rmi://" + registryHostName + ":" + registryPort + "/" + service);
                    p.afterPropertiesSet();
                    return (T) p.getObject();
                }
            };
        default:
            throw new InvalidParameterException("unknown proxy type");
        }
    }

    interface ProxyBuilder {
        <T> T build(Class<T> serviceClass) throws Exception;
    }
}
