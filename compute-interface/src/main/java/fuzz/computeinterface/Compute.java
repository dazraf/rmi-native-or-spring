package fuzz.computeinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    public static String SERVICE_NAME = "compute";
    public static String REGISTRY_HOST = "localhost";
    public static int REGISTRY_PORT = 20000;
    
    <T> T executeTask(final Task<T> t) throws RemoteException;
}