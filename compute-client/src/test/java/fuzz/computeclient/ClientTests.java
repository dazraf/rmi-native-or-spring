package fuzz.computeclient;

import java.rmi.RemoteException;
import org.junit.Test;

import fuzz.computeclient.ProxyFactory.ProxyType;

public class ClientTests {
    @Test
    public void computePiNative() throws Exception {
        ClientApp ca = new ClientApp(ProxyType.NATIVE);
        ca.calcPi();
    }

    @Test
    public void computePiSpring() throws RemoteException, Exception {
        ClientApp ca = new ClientApp(ProxyType.SPRING);
        ca.calcPi();
    }
}
