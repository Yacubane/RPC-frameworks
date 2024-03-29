package sr.thrift.smarthome.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.SingleSensorDevice;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class SingleSensorDeviceHandler extends DeviceHandler implements SingleSensorDevice.Iface {
    AtomicReference<Double> value = new AtomicReference<>(0.0);

    public SingleSensorDeviceHandler(String id, String name, double value) {
        super(id, name);
        this.value.set(value);
    }

    @Override
    public double getValue() throws TException {
        System.out.println("SingleSensorDeviceHandler::getValue");
        return value.get();
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new SingleSensorDevice.Processor<>(this);
    }

}
