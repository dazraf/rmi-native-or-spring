package fuzz.computeclient;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidParameterException;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public final class ProxyFactory<T> {
    public enum ProxyType {
        NATIVE, SPRING
    }

    private ProxyType proxyType = ProxyType.NATIVE;
    private String serviceName;
    private String registryHostName = "localhost";
    private int registryPort = 1099;
    private Class<T> serviceClass;

    public ProxyFactory(String serviceName, Class<T> serviceClass) {
        this.serviceName = serviceName;
        this.serviceClass = serviceClass;
    }

    public ProxyFactory(ProxyType proxyType, String serviceName, Class<T> serviceClass, String registryHostName,
            int registryPort) {
        this.proxyType = proxyType;
        this.serviceName = serviceName;
        this.registryHostName = registryHostName;
        this.registryPort = registryPort;
        this.serviceClass = serviceClass;
    }

    public ProxyFactory<T> proxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
        return this;
    }

    public ProxyFactory<T> registryHostName(String registryHostName) {
        this.registryHostName = registryHostName;
        return this;
    }

    public ProxyFactory<T> registryPort(int registryPort) {
        this.registryPort = registryPort;
        return this;
    }

    @SuppressWarnings("unchecked")
    public T build() throws Exception {
        final Registry registry = LocateRegistry.getRegistry(registryHostName, registryPort);
        switch (proxyType) {
        case NATIVE:
            return (T) registry.lookup(serviceName);
        case SPRING:
            RmiProxyFactoryBean p = new RmiProxyFactoryBean();
            p.setServiceInterface(serviceClass);
            p.setServiceUrl("rmi://" + registryHostName + ":" + registryPort + "/" + serviceName);
            p.afterPropertiesSet();
            return (T) p.getObject();
        default:
            throw new InvalidParameterException("unknown proxy type");
        }

    }
}
