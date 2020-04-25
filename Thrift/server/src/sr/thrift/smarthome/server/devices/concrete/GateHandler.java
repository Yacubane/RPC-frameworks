package sr.thrift.smarthome.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.Gate;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.OpenCloseState;
import sr.thrift.smarthome.server.devices.general.OpenCloseDeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class GateHandler extends OpenCloseDeviceHandler implements Gate.Iface {
    AtomicReference<Double> openPercent = new AtomicReference<>(0.0);

    public GateHandler(String id, String name, double openPercent) {
        super(id, name, OpenCloseState.OPEN);
        this.openPercent.set(openPercent);
    }

    @Override
    public double getOpenPercent() throws TException {
        return openPercent.get();
    }

    @Override
    public void setOpenPercent(double openPercent) throws InvalidArguments, TException {
        if (openPercent < 0.0 || openPercent > 1.0) {
            throw new InvalidArguments(1, "Open percent must be between 0 and 1");
        }
        this.openPercent.set(openPercent);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new Gate.Processor<>(this);
    }

}
