package computeclient.test;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.junit.Test;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import fuzz.computeclient.Pi;
import fuzz.computeinterface.Compute;

public class ClientTests {
    @Test
    public void computePiNative() {
        try {
            Registry registry = LocateRegistry.getRegistry(20000);
            Compute comp = (Compute) registry.lookup(Compute.SERVICE_NAME);
            calcPi(comp);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

    @Test
    public void computePiSpring() {
        try {
            RmiProxyFactoryBean p = new RmiProxyFactoryBean();
            p.setServiceInterface(Compute.class);
            p.setServiceUrl("rmi://localhost:20000/" + Compute.SERVICE_NAME);
            p.afterPropertiesSet();
            Compute comp = (Compute) p.getObject();
            calcPi(comp);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }

    private void calcPi(Compute comp) throws RemoteException {
        Pi task = new Pi(3);
        BigDecimal pi = comp.executeTask(task);
        System.out.println(pi);
    }
}
