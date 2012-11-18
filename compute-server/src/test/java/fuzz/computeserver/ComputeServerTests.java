package fuzz.computeserver;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Test;

import fuzz.computeclient.ClientApp;
import fuzz.computeinterface.Compute;

public class ComputeServerTests {

    static {
        try {
            App.createRegistry();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws AccessException, RemoteException, NotBoundException {
        App.unbindService();
    }

    @Test
    public void runNativeRMIServer() throws Exception {
        Compute engine = new ComputeEngine();
        App.startNativeRMIService(engine);
        ClientApp.calcPi(ClientApp.getNativeRMIProxy());
        ClientApp.calcPi(ClientApp.getSpringRMIProxy());
    }

    @Test
    public void runSpringRMIServer() throws Exception {
        Compute engine = new ComputeEngine();
        App.startSpringRMIService(engine);
        ClientApp.calcPi(ClientApp.getNativeRMIProxy());
        ClientApp.calcPi(ClientApp.getSpringRMIProxy());
    }

}
