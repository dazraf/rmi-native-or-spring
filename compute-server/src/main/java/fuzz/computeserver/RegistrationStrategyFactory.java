package fuzz.computeserver;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;

import org.springframework.remoting.rmi.RmiServiceExporter;

import fuzz.computeinterface.Compute;

public class RegistrationStrategyFactory {
    public enum StrategyType {
        NATIVE,
        SPRING
    }
    public static RegistrationStrategy getStrategy(final StrategyType st, final int registryPort) throws RemoteException {
        final Registry registry = getRegistry();
        
        switch (st) {
        case NATIVE:
            return new RegistrationStrategy() {
                @Override
                public <T extends Remote, I> void register(final String serviceName, final T service, final Class<I> serviceInteface) throws RemoteException {
                    @SuppressWarnings("unchecked")
                    T stub = (T) UnicastRemoteObject.exportObject(service, 0);
                    registry.rebind(serviceName, stub);
                }

                @Override
                public void unregister(final String serviceName) throws AccessException, RemoteException, NotBoundException {
                    registry.unbind(serviceName);                                        
                }
            };
        case SPRING:
            return new RegistrationStrategy() {
                @Override
                public <T extends Remote, I> void register(final String serviceName, final T service, final Class<I> serviceInterface) throws RemoteException {
                    RmiServiceExporter se = new RmiServiceExporter();
                    se.setServiceName(serviceName);
                    se.setRegistry(registry);
                    se.setServiceInterface(serviceInterface);
                    se.setService(service);
                    se.afterPropertiesSet();
                }
                @Override
                public void unregister(final String serviceName) throws AccessException, RemoteException, NotBoundException {
                    registry.unbind(serviceName);                    
                }
            };
        default:
            throw new InvalidParameterException("unknown strategy type");
        }
    }
    private static Registry getRegistry() throws RemoteException {
        Registry r = null;
        try {
            r = LocateRegistry.createRegistry(Compute.REGISTRY_PORT);
        } catch (Exception e) {
            r = LocateRegistry.getRegistry(Compute.REGISTRY_PORT);            
        }
        return r;
    }
    public interface RegistrationStrategy {
        <T extends Remote, I>void register(final String serviceName, final T service, final Class<I> serviceInterface) throws RemoteException;
        void unregister (final String serviceName) throws AccessException, RemoteException, NotBoundException;
    }
}
