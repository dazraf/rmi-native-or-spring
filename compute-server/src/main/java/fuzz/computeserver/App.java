package fuzz.computeserver;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.remoting.rmi.RmiServiceExporter;

import fuzz.computeinterface.Compute;

public class App {

    public static void main(String[] args) throws IOException, NotBoundException {
        if (args.length == 0) {
            System.out.println("spring|native");
            System.exit(0);
        }

        createRegistry();
        Compute engine = new ComputeEngine();
        if ("spring".equalsIgnoreCase(args[0])) {
            startNativeRMIService(engine);
        }

        else if ("native".equalsIgnoreCase(args[0])) {
            startSpringRMIService(engine);
        }
        
        System.out.println("Press <Enter> to shutdown");
        System.in.read();
        System.out.println("Shutting down");
        unbindService();
    }

    static void createRegistry() throws RemoteException {
        LocateRegistry.createRegistry(Compute.REGISTRY_PORT);
    }
    
    static void startSpringRMIService(Compute engine) throws IOException {
        RmiServiceExporter se = new RmiServiceExporter();
        se.setServiceName(Compute.SERVICE_NAME);
        se.setRegistry(LocateRegistry.getRegistry(Compute.REGISTRY_HOST, Compute.REGISTRY_PORT));
        se.setServiceInterface(Compute.class);
        se.setService(engine);
        se.afterPropertiesSet();
    }

    static void startNativeRMIService(Compute engine) throws RemoteException {
        Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.getRegistry(Compute.REGISTRY_HOST, Compute.REGISTRY_PORT);
        registry.rebind(Compute.SERVICE_NAME, stub);
    }
    
    static void unbindService() throws AccessException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(Compute.REGISTRY_HOST, Compute.REGISTRY_PORT); 
        registry.unbind(Compute.SERVICE_NAME);
    }
}
