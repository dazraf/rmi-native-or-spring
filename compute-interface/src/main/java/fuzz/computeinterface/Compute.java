package fuzz.computeinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
    public static String SERVICE_NAME = "compute";
    <T> T executeTask(Task<T> t) throws RemoteException;
}