package fuzz.computeclient;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import fuzz.computeclient.ProxyFactory.ProxyType;
import fuzz.computeinterface.Compute;

public class ClientApp {
    private Compute engine;
    
    public static void main(final String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("spring|native");
            System.exit(0);
        }

        ProxyFactory.ProxyType pt = ProxyFactory.ProxyType.valueOf(args[0].trim().toUpperCase());
        new ClientApp(pt).calcPi();
    }

    public ClientApp(final ProxyType pt) throws RemoteException, Exception {
        engine = ProxyFactory.getBuilder(pt, Compute.SERVICE_NAME, Compute.REGISTRY_HOST, Compute.REGISTRY_PORT).build(Compute.class);
    }
    
    public  void calcPi() throws RemoteException {
        Pi task = new Pi(3);
        BigDecimal pi = engine.executeTask(task);
        System.out.println(pi);
    }

}
