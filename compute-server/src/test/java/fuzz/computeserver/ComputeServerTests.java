package fuzz.computeserver;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.Test;

import fuzz.computeclient.nativermi.ClientAppNative;
import fuzz.computeclient.spring.ClientAppSpring;
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
            ClientAppNative nativeClient = new ClientAppNative();
            nativeClient.calcPi();
            ClientAppSpring springClient = new ClientAppSpring();
            springClient.calcPi();
        } finally {
            sa.shutdown();
        }
    }
}
