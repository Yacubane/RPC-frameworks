package sr.thrift.smarthome.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.MultipleSensorDevice;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MultipleSensorDeviceHandler extends DeviceHandler implements MultipleSensorDevice.Iface {
    private final ConcurrentHashMap<String, Double> values;

    public MultipleSensorDeviceHandler(String id, String name, List<String> keys) {
        super(id, name);
        values = new ConcurrentHashMap<>();
        keys.forEach(e -> values.put(e, 0.0));
    }

    @Override
    public double getValue(String field) throws InvalidArguments, TException {
        System.out.println("MultipleSensorDeviceHandler::getValue");
        if (!values.containsKey(field)) {
            throw new InvalidArguments(1, "This device doesn't have this key");
        }
        return values.get(field);
    }

    @Override
    public Set<String> getAvailableKeys() throws TException {
        System.out.println("MultipleSensorDeviceHandler::getAvailableKeys");
        return values.keySet();
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new MultipleSensorDevice.Processor<>(this);
    }

}
