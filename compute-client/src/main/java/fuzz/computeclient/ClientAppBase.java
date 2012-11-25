package fuzz.computeclient;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import fuzz.computeclient.ProxyFactory.ProxyType;
import fuzz.computeinterface.Compute;
import fuzz.computepi.Pi;

public abstract class ClientAppBase {
    private Compute engine;
    

    public ClientAppBase(final ProxyType pt) throws RemoteException, Exception {
        engine = new ProxyFactory<Compute>(Compute.SERVICE_NAME, Compute.class)
        .proxyType(pt)
        .registryHostName(Compute.REGISTRY_HOST)
        .registryPort(Compute.REGISTRY_PORT)
        .build();
    }
    
    public  void calcPi() throws RemoteException {
        Pi task = new Pi(3);
        BigDecimal pi = engine.executeTask(task);
        System.out.println(pi);
    }

}
