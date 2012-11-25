package fuzz.computeclient.nativermi;

import java.rmi.RemoteException;

import fuzz.computeclient.ClientAppBase;
import fuzz.computeclient.ProxyFactory.ProxyType;

public class ClientAppNative extends ClientAppBase {
    
    /**
     * @param args
     * @throws Exception 
     * @throws RemoteException 
     */
    public static void main(String[] args) throws RemoteException, Exception {
        new ClientAppNative().calcPi();
    }
    
    public ClientAppNative() throws RemoteException, Exception {
        super(ProxyType.NATIVE);
    }
}
