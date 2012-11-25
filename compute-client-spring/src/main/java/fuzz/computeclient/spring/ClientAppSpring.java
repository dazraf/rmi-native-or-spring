package fuzz.computeclient.spring;


import java.rmi.RemoteException;

import fuzz.computeclient.ClientAppBase;
import fuzz.computeclient.ProxyFactory.ProxyType;

public class ClientAppSpring extends ClientAppBase {    
    /**
     * @param args
     * @throws Exception 
     * @throws RemoteException 
     */
    public static void main(String[] args) throws RemoteException, Exception {
        new ClientAppSpring().calcPi();
    }
    
    public ClientAppSpring() throws RemoteException, Exception {
        super(ProxyType.SPRING);
    }
}
