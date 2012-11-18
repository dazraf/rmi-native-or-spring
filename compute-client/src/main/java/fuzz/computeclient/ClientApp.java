package fuzz.computeclient;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import fuzz.computeinterface.Compute;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("spring|native");
            System.exit(0);
        }

        Compute engine = null;
        if ("spring".equalsIgnoreCase(args[0])) {
            engine = getNativeRMIProxy();
        }

        else if ("native".equalsIgnoreCase(args[0])) {
            engine = getSpringRMIProxy();
        }
        
        else {
            System.out.println("spring|native");
            System.exit(0);            
        }
        calcPi(engine);
    }

    public static Compute getSpringRMIProxy() throws Exception {
        RmiProxyFactoryBean p = new RmiProxyFactoryBean();
        p.setServiceInterface(Compute.class);
        p.setServiceUrl("rmi://" + Compute.REGISTRY_HOST + ":" + Compute.REGISTRY_PORT + "/" + Compute.SERVICE_NAME);
        p.afterPropertiesSet();
        return (Compute) p.getObject();
    }

    public static Compute getNativeRMIProxy() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry (Compute.REGISTRY_HOST, Compute.REGISTRY_PORT);
        return (Compute) registry.lookup(Compute.SERVICE_NAME);        
    }
    
    public static void calcPi(Compute engine) throws RemoteException {
        Pi task = new Pi(3);
        BigDecimal pi = engine.executeTask(task);
        System.out.println(pi);
    }

}
