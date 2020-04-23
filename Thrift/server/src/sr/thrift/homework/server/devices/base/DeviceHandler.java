package sr.thrift.homework.server.devices.base;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.Device;

public abstract class DeviceHandler implements Device.Iface {
    private final String id;
    private final String name;

    public DeviceHandler(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() throws TException {
        return id;
    }

    @Override
    public String getName() throws TException {
        return name;
    }

    public abstract TBaseProcessor<?> generateProcessor();

}
