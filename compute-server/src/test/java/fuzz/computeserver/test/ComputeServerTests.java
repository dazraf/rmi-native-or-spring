package fuzz.computeserver.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.remoting.rmi.RmiServiceExporter;

import fuzz.computeinterface.Compute;
import fuzz.computeserver.ComputeEngine;

public class ComputeServerTests {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void runNativeRMIServer() {
        try {
            Compute engine = new ComputeEngine();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(20000);
            registry.rebind(Compute.SERVICE_NAME, stub);
            System.out.println("ComputeEngine bound. Press <enter> to shutdown.");
            System.in.read();
            System.out.println("ComputeEngine shutting down.");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }

    @Test
    public void runSpringRMIServer() {
        try {
            RmiServiceExporter se = new RmiServiceExporter();
            se.setServiceName(Compute.SERVICE_NAME);
            se.setRegistry(LocateRegistry.createRegistry(20000));
            se.setServiceInterface(Compute.class);
            se.setService(new ComputeEngine());
            se.afterPropertiesSet();
            System.out.println("ComputeEngine bound. Press <enter> to shutdown.");
            System.in.read();
            System.out.println("ComputeEngine shutting down.");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}
