package fuzz.computeclient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Test;

public class ClientTests {
    @Test
    public void computePiNative() throws RemoteException, NotBoundException {
        ClientApp.calcPi(ClientApp.getNativeRMIProxy());
    }

    @Test
    public void computePiSpring() throws RemoteException, Exception {
        ClientApp.calcPi(ClientApp.getSpringRMIProxy());
    }
}
