package fuzz.computeserver;

import fuzz.computeinterface.Compute;
import fuzz.computeinterface.Task;

public class ComputeEngine implements Compute {

    public ComputeEngine() {
        super();
    }

    public <T> T executeTask(final Task<T> t) {
        return t.execute();
    }
}