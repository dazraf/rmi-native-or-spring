package fuzz.computeserver;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Test;

import fuzz.computeclient.ClientApp;
import fuzz.computeclient.ProxyFactory.ProxyType;
import fuzz.computeserver.RegistrationStrategyFactory.StrategyType;

public class ComputeServerTests {

    @Test
    public void runNativeRMIServer() throws Exception {
        runClientServerTests(new ServerApp(StrategyType.NATIVE));
    }

    @Test
    public void runSpringRMIServer() throws Exception {
        runClientServerTests(new ServerApp(StrategyType.SPRING));
    }

    private void runClientServerTests(ServerApp sa) throws RemoteException, Exception, AccessException,
            NotBoundException {
        sa.startup();
        try {
            ClientApp nativeClient = new ClientApp(ProxyType.NATIVE);
            nativeClient.calcPi();
            ClientApp springClient = new ClientApp(ProxyType.SPRING);
            springClient.calcPi();
        } finally {
            sa.shutdown();
        }
    }
}
